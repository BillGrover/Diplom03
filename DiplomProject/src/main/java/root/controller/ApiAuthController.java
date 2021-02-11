package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.dto.ErrorsDto;
import root.dtoRequests.UserRequest;
import root.dtoResponses.CaptchaResponse;
import root.dtoResponses.LoginResponse;
import root.dtoResponses.SimpleResponse;
import root.services.CaptchaService;
import root.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Autowired
    public ApiAuthController(AuthService authService, CaptchaService captchaService) {
        this.authService = authService;
        this.captchaService = captchaService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRequest userRequest) {
        return authService.login(userRequest);
    }

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check() {
        return authService.check();
    }

    @GetMapping("/logout")
    public ResponseEntity<SimpleResponse> logout() {
        return new ResponseEntity<>(new SimpleResponse(true, new ErrorsDto()), HttpStatus.OK);
    }

    @GetMapping("/captcha")
    @ResponseBody
    public CaptchaResponse captcha() {
        return captchaService.createCaptcha();
    }

    @PostMapping("/register")
    @ResponseBody
    public SimpleResponse register(@RequestBody UserRequest registerRequest) {
        return authService.register(registerRequest);
    }
}

