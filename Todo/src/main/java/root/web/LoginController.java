package root.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/login-error")
    public String showLoginErrorPage (Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
