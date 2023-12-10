package effectiveMobile.com.taskManagementSystem.config;

import effectiveMobile.com.taskManagementSystem.services.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.COMMENTS_PERMISSION_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.COMMENTS_WHITE_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.RESOURCES_WHITE_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.TASKS_PERMISSION_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.TASKS_WHITE_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.USERS_PERMISSION_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.SecurityConstants.USERS_WHITE_LIST;
import static effectiveMobile.com.taskManagementSystem.constants.UserRolesConstants.ADMIN;
import static effectiveMobile.com.taskManagementSystem.constants.UserRolesConstants.USER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(TASKS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(COMMENTS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(TASKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, USER)
                        .requestMatchers(COMMENTS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN)
                        .requestMatchers(USERS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(USER)
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                );

        return httpSecurity.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
