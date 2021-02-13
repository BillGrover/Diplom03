package root.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import root.dto.ErrorsDto;
import root.dto.UserDto;
import root.dtoRequests.UserRequest;
import root.dtoResponses.LoginResponse;
import root.dtoResponses.SimpleResponse;
import root.model.Role;
import root.model.Role2User;
import root.repositories.PostRepository;
import root.repositories.UserRepository;
import root.repositories.UsersRolesRepository;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CaptchaService captchaService;
    private final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);
    private final UsersRolesRepository usersRolesRepo;
    private final JavaMailSender mailSender = new JavaMailSenderImpl();


    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepo,
            PostRepository postRepo,
            CaptchaService captchaService,
            UsersRolesRepository usersRolesRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.captchaService = captchaService;
        this.usersRolesRepo = usersRolesRepo;
    }

    public ResponseEntity<LoginResponse> login(UserRequest userRequest) {
        UsernamePasswordAuthenticationToken userFromRequestBody =
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());
        try {
            Authentication auth = authenticationManager.authenticate(userFromRequestBody);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (AuthenticationException e) {
            System.out.println("Authentication error.\n" + e.getMessage());
            return new ResponseEntity<>(
                    new LoginResponse(false),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(buildResponse(userRequest.getEmail()), HttpStatus.OK);
    }

    /**
     * Метод возвращает информацию о текущем авторизованном пользователе, если он авторизован.
     * Значение moderationCount содержит количество постов необходимых для проверки модераторами.
     * Считаются посты имеющие статус NEW и не проверерны модератором.
     * Если пользователь не модератор возращать 0 в moderationCount.
     *
     * @return ResponseEntity.
     */
    public ResponseEntity<LoginResponse> check() {
        User user;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            System.out.println("Не удалось получить юзера:\n" + e.getMessage());
            return new ResponseEntity<>(new LoginResponse(false), HttpStatus.OK);
        }
        return new ResponseEntity<>(buildResponse(user.getUsername()), HttpStatus.OK);
    }

    /**
     * Метод регистрации нового юзера
     *
     * @param registerRequest - объект UserRequest из запроса
     * @return SimpleResponse.
     */
    public SimpleResponse register(UserRequest registerRequest) {

        ErrorsDto errorsDto = validateRequest(registerRequest);
        if (errorsDto != null)
            return new SimpleResponse(false, errorsDto);

        root.model.User user = new root.model.User(
                registerRequest.getName(),
                registerRequest.getEmail(),
                ENCODER.encode(registerRequest.getPassword()));

        userRepo.save(user);
        usersRolesRepo.save(new Role2User(user, new Role(1, "USER")));

        return new SimpleResponse(true, new ErrorsDto());
    }

    /**
     * Метод проверяет реквест-объект при регистрации нового юзера на наличие ошибок.
     * @param registerRequest - реквест-объект.
     * @return ErrorsDto.
     */
    private ErrorsDto validateRequest(UserRequest registerRequest) {

        ErrorsDto errorsDto = null;
        if (!captchaService.validateCaptcha(registerRequest.getCaptcha(), registerRequest.getCaptchaSecret())){
            errorsDto = new ErrorsDto();
            errorsDto.setCaptcha("Код с картинки введён неверно.");
        }
        if (userRepo.existsByEmail(registerRequest.getEmail())){
            if (errorsDto == null)
                errorsDto = new ErrorsDto();
            errorsDto.setEmail("Этот e-mail уже зарегистрирован.");
        }
        if (registerRequest.getPassword().length() < 6){
            if (errorsDto == null)
                errorsDto = new ErrorsDto();
            errorsDto.setPassword("Пароль короче 6-ти символов.");
        }
        if (registerRequest.getName().matches("\\W") || registerRequest.getName().length() < 3){
            if (errorsDto == null)
                errorsDto = new ErrorsDto();
            errorsDto.setName("Имя должно состоять только из букв и быть длинной не менее 3 символов");
        }
        return errorsDto;
    }

    public SimpleResponse restore(UserRequest restoreRequest) {
        if (!userRepo.existsByEmail(restoreRequest.getEmail()))
            return new SimpleResponse(false, new ErrorsDto());


        return new SimpleResponse(true, new ErrorsDto());
    }

    /**
     * Метод заполняет поле moderationCount в классе UserDto.
     * В зависимости от того, является ли пользователь модератором.
     * Затем возвращает готовый LoginResponse.
     *
     * @param email - емэйл текущего юзера.
     * @return new LoginResponse
     */
    private LoginResponse buildResponse(String email) {

        root.model.User userFromDB = userRepo.findByEmail(email);

        UserDto userDto = new UserDto(userFromDB);
        int moderationCount = userFromDB.getIsModerator() == 0 ? 0 : postRepo.countByModerationStatus("NEW");
        userDto.setModerationCount(moderationCount);

        return new LoginResponse(true, userDto);
    }
}
/*
        Заметки для себя:

        AuthenticationManager получается в SecurityConfig.
       AuthenticationManager в методе authenticate возвращает одно из трёх:
       1. объект Authentication, если он может проверить юзера
       2. AuthenticationException, если юзер недопустим.
       3. null, если не может определить.

       Из входящих данных создаётся объект для последующей его сверки
        UsernamePasswordAuthenticationToken userFromRequestBody =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        метод authenticate проверяет юзера и возвращает объект Authenticate
        Authentication auth = authenticationManager.authenticate(userFromRequestBody);

        объект Authenticate помещается в SecurityContextHolder - хранилище аутентифицированных юзеров
        SecurityContextHolder.getContext().setAuthentication(auth);

        получить юзера (UserDetails) обратно:
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        На основе объекта Authenticate создаётся объект User(org.springframework.security.core.userdetails.User), имплементирующийся от UserDetails
        User userUD = (User) auth.getPrincipal();

        У этого юзера берётся username (здесь это e-mail, который был передан в RequestBody)
        return ResponseEntity.ok(getLoginResponse(userUD.getUsername()));
  */
