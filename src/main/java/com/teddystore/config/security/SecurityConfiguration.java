package com.teddystore.config.security;

import com.teddystore.repository.CostumerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(CostumerRepository userRepo) throws InternalAuthenticationServiceException {
        return username -> userRepo.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/h2-console/**", "/api/v1/costumers/register/**");
//    }

    @Bean
    @Primary
    public HttpSecurity filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .mvcMatchers("/h2-console/**", "/api/v1/costumers/register/**", "/login")
                .permitAll()

                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                );
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        var service = new InMemoryUserDetailsManager();
//
//        var u1 = User.withUsername("natalie")
//                .password("12345")
//                .authorities("SCOPE_costumer:read")
//                .build();
//
//        var u2 = User.withUsername("emma")
//                .password("12345")
//                .authorities("SCOPE_costumer:write")
//                .build();
//
//        service.createUser(u1);
//        service.createUser(u2);
//
//        return service;
//    }

}
