package root.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;
import root.domain.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

    @NotNull(message="Name must be at least 3 characters long")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String username;

    @NotNull(message="Password must be at least 1 symbols long")
    @Size(min=1, message="Password must be at least 1 symbols long")
    private String password;

    private String confirm;

    @NotNull(message="Fullname must be at least 3 characters long")
    @Size(min=3, message="Fullname must be at least 3 characters long")
    private String fullname;

    public User saveUserToDB(PasswordEncoder passwordEncoder) {

        return new User(
                username,
                passwordEncoder.encode(password),
                fullname);
    }
}
