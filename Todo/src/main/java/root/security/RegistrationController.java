package root.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import root.data.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "regFormAttr")
    public RegistrationForm regForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(Model model, @ModelAttribute("regFormAttr") @Valid RegistrationForm form, BindingResult bindingResult) {

        if (userRepo.findByUsername(form.getUsername())!=null){     //если такой объект есть
            model.addAttribute("UsernameExistError", true);
            return "registration";

        }else
        if (bindingResult.hasErrors()) {
            return "registration";

        }else
        if (form.getPassword().equals(form.getConfirm())) {
            userRepo.save(form.saveUserToDB(passwordEncoder));
            return "redirect:/login";

        } else {
            model.addAttribute("registrationError", true);
            return "registration";
        }
    }
}
