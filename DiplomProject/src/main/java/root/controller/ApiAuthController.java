package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.dtoRequests.LoginRequest;
import root.dtoResponses.LoginResponse;
import root.services.LoginService;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final LoginService loginService;

    @Autowired
    public ApiAuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check() {
        return loginService.check();
    }
}

