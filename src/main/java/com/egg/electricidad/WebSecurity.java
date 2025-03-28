package com.egg.electricidad;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/", "/redirect-home").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/inicio").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/product/list", "/factory/list").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/product/register-product", "/factory/register-factory",
                                         "/factory/update/**", "/product/update/**", "/product/delete/**", "/factory/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        )
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio", true)
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .permitAll())

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler()))

                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.sendRedirect("/redirect-home");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
