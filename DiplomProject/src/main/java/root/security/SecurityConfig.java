package root.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/register").not().fullyAuthenticated()

                .antMatchers(HttpMethod.PUT, "/api/settings*").hasAuthority("MODERATOR")
                .antMatchers(HttpMethod.POST,"/api/moderation*").hasAuthority("MODERATOR")
                .antMatchers(HttpMethod.PUT,"/api/post/moderation*").hasAuthority("MODERATOR")
                .antMatchers(HttpMethod.GET,"/api/post/moderation*").hasAuthority("MODERATOR")

                .antMatchers(HttpMethod.GET, "/api/post/my*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/post/like*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/post/dislike*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/post*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.PUT, "/api/post/{id}*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/api/statistics/my*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/api/auth/logout*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/comment*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/profile/my*").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/image*").hasAnyAuthority("USER", "MODERATOR")

                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin().disable();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    //Получение бина AuthenticationManager из WebSecurityConfigurerAdapter
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
