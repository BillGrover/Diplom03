package root.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import root.dto.ErrorsDto;
import root.dtoRequests.PostRequest;
import root.dtoResponses.OnePostResponse;
import root.dtoResponses.PostsResponse;
import root.dtoResponses.SimpleResponse;
import root.enums.ModerationStatus;
import root.model.Post;
import root.model.Tag;
import root.model.Tag2Post;
import root.model.User;
import root.repositories.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    PostRepository postRepo;
    UserRepository userRepo;
    TagRepository tagRepo;
    Tag2PostRepository tag2PostRepo;
    GlobalSettingsRepository globalSettingsRepo;

    public PostService(PostRepository postRepo, UserRepository userRepo, TagRepository tagRepo,
                       Tag2PostRepository tag2PostRepo, GlobalSettingsRepository globalSettingsRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
        this.tag2PostRepo = tag2PostRepo;
        this.globalSettingsRepo = globalSettingsRepo;
    }

    /**
     * Метод получения постов со всей сопутствующей информацией для главной страницы и подразделов "Новые", "Самые обсуждаемые", "Лучшие" и "Старые".
     * Метод выводит посты, отсортированные в соответствии с параметром mode.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param mode      recent/popular/best/early
     * @return ResponseEntity
     */
    public ResponseEntity<PostsResponse> getPosts(String offsetStr, String limitStr, String mode) {
        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset or limit) в GET-запросе api/post\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pagination = PageRequest.of(offset, limit);
        Page<Post> posts;
        switch (mode) {
            case ("recent"):    //сначала новые
                posts = postRepo.findRecent(pagination);
                break;
            case ("popular"):   //по убыванию комментариев (comments.size)
                posts = postRepo.findPopular(pagination);
                break;
            case ("best"):      //по убыванию лайков (сумма значений листа votes)
                posts = postRepo.findBest(pagination);
                break;
            case ("early"):     //сначала старые
                posts = postRepo.findEarly(pagination);
                break;
            default:            //по id
                posts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeOrderById(
                        (byte) 1, ModerationStatus.ACCEPTED, new Date(), pagination);
        }
        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод возвращает посты, соответствующие поисковому запросу - строке query.
     * В случае, если запрос пустой, метод выводит все посты.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param query     Query for search
     * @return ResponseEntity
     */
    public ResponseEntity<PostsResponse> getByQuery(String offsetStr, String limitStr, String query) {
        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset or limit) в GET-запросе api/post/search\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<Post> posts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeOrderById(
                (byte) 1, ModerationStatus.ACCEPTED, new Date(), PageRequest.of(offset, limit));
        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }


    /**
     * Выводит посты за указанную дату, переданную в запросе в параметре date.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param dateStr   The day for search.
     * @return ResponseEntity
     */
    public ResponseEntity<PostsResponse> getByDate(String offsetStr, String limitStr, String dateStr) {
        Page<Post> posts;
        try {
            int offset = Integer.parseInt(offsetStr);
            int limit = Integer.parseInt(limitStr);
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dayStart = df1.parse(dateStr);
            df1 = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSS");
            Date dayEnd = df1.parse(dateStr + "-23-59-59-999");

            posts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeAndTimeBetweenOrderByTimeDesc(
                    (byte) 1, ModerationStatus.ACCEPTED, new Date(), dayStart, dayEnd, PageRequest.of(offset, limit));

        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset, date or limit) в GET-запросе api/post/byDate\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод выводит список постов, привязанных к тэгу, который был передан методу в качестве параметра tag.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param tag       Tag name for search.
     * @return ResponseEntity.
     */
    public ResponseEntity<PostsResponse> getByTag(String offsetStr, String limitStr, String tag) {
        Page<Post> posts;
        try {
            int offset = Integer.parseInt(offsetStr);
            int limit = Integer.parseInt(limitStr);
            posts = postRepo.findbyTag(tag, PageRequest.of(offset, limit));
        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset, tag or limit) в GET-запросе api/post/byTag\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод выводит все посты, которые требуют модерационных действий (которые нужно утвердить или отклонить)
     * или над которыми мною были совершены модерационные действия: которые я отклонил или утвердил.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param status    Moderation status.
     * @return ResponseEntity.
     */
    public ResponseEntity<PostsResponse> getModeration(String offsetStr, String limitStr, String status) {
        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
            ModerationStatusChecker.ok(status);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (status, offset or limit) в GET-запросе api/post/moderation\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<Post> posts;
        if (status.toUpperCase().equals("NEW"))
            posts = postRepo.findByStatusAndModeratorId(status.toUpperCase(), PageRequest.of(offset, limit));
        else
            posts = postRepo.findByStatusAndModeratorId(status.toUpperCase(), getUserFromContext().getId(), PageRequest.of(offset, limit));

        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод выводит только те посты, которые создал текущий юзер.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param status    Moderation status:
     *                  inactive - скрытые, ещё не опубликованы (is_active = 0);
     *                  pending - активные, ожидают утверждения модератором (is_active = 1, moderation_status = NEW);
     *                  declined - отклонённые по итогам модерации (is_active = 1, moderation_status = DECLINED);
     *                  published - опубликованные по итогам модерации (is_active = 1, moderation_status = ACCEPTED).
     * @return ResponseEntity.
     */
    public ResponseEntity<PostsResponse> getMy(String offsetStr, String limitStr, String status) {

        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
            PostStatusChecker.ok(status);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (status, offset or limit) в GET-запросе api/post/my\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int currentUserId = getUserFromContext().getId();

        Pageable pagination = PageRequest.of(offset, limit);
        Page<Post> posts;
        switch (status.toUpperCase()) {
            case ("INACTIVE"):
                posts = postRepo.findInactive(currentUserId, pagination);
                break;
            case ("PENDING"):
                posts = postRepo.findByStatusAndUserId(currentUserId, "NEW", pagination);
                break;
            case ("DECLINED"):
                posts = postRepo.findByStatusAndUserId(currentUserId, "DECLINED", pagination);
                break;
            case ("PUBLISHED"):
                posts = postRepo.findByStatusAndUserId(currentUserId, "ACCEPTED", pagination);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод выводит данные конкретного поста для отображения на странице поста,
     * в том числе, список комментариев и тэгов, привязанных к данному посту.
     * Если вызывающий этот метод юзер - не модератор и не автор поста, увеличивает количество просмотров.
     *
     * @param id - id поста.
     * @return ResponseEntity.
     */
    public ResponseEntity<OnePostResponse> getOnePost(int id) {
        Post post = postRepo.findById(id);
        if (post == null) {
            System.out.println("Пост не найден. GET-запрос: api/post/" + id + "\n");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (getUserFromContext().getIsModerator() != 1 && post.getUser().getId() != getUserFromContext().getId()) {
            post.setViewCount(post.getViewCount() + 1);
            postRepo.save(post);
        }
        return new ResponseEntity<>(new OnePostResponse(post), HttpStatus.OK);
    }

    /**
     * Метод сохраняет новый пост в БД. Возвращает ошибки, если они есть.
     *
     * @param postRequest - Данные поста, которые нужно записать в БД.
     * @return SimpleResponse.
     */
    public ResponseEntity<SimpleResponse> savePost(PostRequest postRequest) {

        if (postRequest.getText().length() < 50 || postRequest.getTitle().length() < 3)
            return tooShortTextOrTitle(postRequest);

        try {
            Post newPost = new Post();
            newPost.setIsActive(postRequest.getActive());
            newPost.setModerationStatus(dependsOfGlobalSettings());
            newPost.setUser(getUserFromContext());
            newPost.setTime(
                    postRequest.getTimestamp() * 1000 < System.currentTimeMillis() ? new Date() : new Date(postRequest.getTimestamp() * 1000));
            newPost.setTitle(postRequest.getTitle());
            newPost.setText(postRequest.getText());
            newPost.setViewCount(0);
            postRepo.save(newPost);
            newPost.setTag2Post(handleTags(postRequest, newPost));
        } catch (Exception e) {
            System.out.println("Ошибка при записи нового поста в базу данных\n" + e.getMessage());
            return new ResponseEntity<>(new SimpleResponse(false, new ErrorsDto()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new SimpleResponse(true, new ErrorsDto()), HttpStatus.OK);
    }

    /**
     * Метод апдейтит пост или возвращает ошибки, если апдейт не удался.
     *
     * @param id          - id поста
     * @param postRequest - новые данные поста.
     * @return SimpleResponse.
     */
    public ResponseEntity<SimpleResponse> updatePost(int id, PostRequest postRequest) {
        if (postRequest.getText().length() < 50 || postRequest.getTitle().length() < 3)
            return tooShortTextOrTitle(postRequest);
        try {
            Post post = postRepo.findById(id);
            post.setIsActive(postRequest.getActive());
            if (getUserFromContext().getIsModerator() == 0)
                post.setModerationStatus(dependsOfGlobalSettings());
            post.setTime(postRequest.getTimestamp() * 1000 < System.currentTimeMillis() ? new Date() : new Date(postRequest.getTimestamp() * 1000));
            post.setTitle(postRequest.getTitle());
            post.setText(postRequest.getText());
            post.setTag2Post(handleTags(postRequest, post));
        } catch (Exception e) {
            System.out.println("Ошибка при редактировании поста в базе данных\n" + e.getMessage());
            return new ResponseEntity<>(new SimpleResponse(false, new ErrorsDto()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new SimpleResponse(true, new ErrorsDto()), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает теги из запроса, записывает их в базу и создаёт связи с постом.
     *
     * @param postRequest - пост из запроса.
     * @return - Set(Tag2Post) готовый к записи в пост.
     */
    private Set<Tag2Post> handleTags(PostRequest postRequest, Post newPost) {
        addTagsIfNotExist(postRequest.getTags());
        List<Tag> tags =
                postRequest.getTags()
                        .stream()
                        .map(t -> tagRepo.findByTitle(t))
                        .collect(Collectors.toList());

        Set<Tag2Post> tag2Posts =
                tags.stream()
                        .map(tag -> {
                            Tag2Post t2p = new Tag2Post(newPost, tag);
                            tag2PostRepo.save(t2p);
                            return t2p;
                        }).collect(Collectors.toSet());
        return tag2Posts;
    }

    /**
     * Метод создаёт требуемый респонс, если текст или заголовок поста слишком короткие.
     *
     * @param postRequest - проверяемый пост.
     * @return ResponseEntity.
     */
    private ResponseEntity<SimpleResponse> tooShortTextOrTitle(PostRequest postRequest) {
        ErrorsDto errorsDto = new ErrorsDto();
        if (postRequest.getText().length() < 50)
            errorsDto.setText("Текст поста не может быть короче 50 символов.");
        if (postRequest.getTitle().length() < 3)
            errorsDto.setTitle("Заголовок поста не может быть короче 3 символов");
        return new ResponseEntity<>(new SimpleResponse(false, errorsDto), HttpStatus.OK);
    }

    /**
     * Метод проверяет глобальные настройки.
     * Если премодерация активна, устанавливает статус новых постов - NEW, в противном случае - ACCEPTED.
     *
     * @return ModerationStatus.NEW or ModerationStatus.ACCEPTED.
     */
    private ModerationStatus dependsOfGlobalSettings() {
        if (globalSettingsRepo.findByCode("POST_PREMODERATION").getValue().equals("YES"))
            return ModerationStatus.NEW;
        else
            return ModerationStatus.ACCEPTED;
    }

    /**
     * Метод проверяет список тегов на наличие их в базе. Если тега нет, добавляет его.
     *
     * @param tags - список имён тегов.
     */
    private void addTagsIfNotExist(List<String> tags) {
        if (tags.size() != 0) {
            tags.forEach(tag -> {
                if (tagRepo.countAllByTitle(tag.toLowerCase()) == 0)
                    tagRepo.save(new Tag(tag));
            });
        }
    }

    /**
     * Метод получает текущего юзера из контекста Spring Security.
     *
     * @return
     */
    private User getUserFromContext() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByName(userDetails.getUsername());
    }
}
