package se2.fit.tut08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private UserDetailsService jpaUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .userDetailsService(jpaUserDetailsService)
                .authorizeHttpRequests(req -> req.requestMatchers("/member/").authenticated()
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
}
