package root.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
        .configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http                                                               //Более строгие правила должны идти первыми.
                .authorizeRequests()
                .antMatchers("/home")                           //Будет вызывать HttpSecurity только при указанных совпадениях
                .access("hasRole('ROLE_USER')")                    //Выполняется, если access = true. //Только ROLE_USER могут заходить на /home. Внутрь access можно записать любые правила на SpEL
                .antMatchers("/", "/**").access("permitAll") //Все могут заходить на / и /**

                .and()                                                      //Закончили настройку авторизации и собираюсь прописать некоторые дополнительные настройки
                .formLogin()
                .loginPage("/login")                                        //enable form login
                .failureUrl("/login-error")
                .defaultSuccessUrl("/home", true)     //страница, на которую перйдет юзер после аутентификации. True - принудительный переход (по умолчанию переход - туда, где юзер был до аутентификации).

                .and()
                .logout()
                .logoutSuccessUrl("/login")
        ;}

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

    }
}
