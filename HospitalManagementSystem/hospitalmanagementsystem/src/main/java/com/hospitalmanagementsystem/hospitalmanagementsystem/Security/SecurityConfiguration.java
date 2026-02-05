package com.hospitalmanagementsystem.hospitalmanagementsystem.Security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration{

    private final jwtSecurityAuthFilter jwtSecurityAuthFilter;
    private final AuthSuccessHandler authSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()

                        )
                .addFilterBefore(jwtSecurityAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(auth->
                        auth.
                            failureHandler(
                                    (request,response,exception)->
                                    {
                                        log.error("auth2 error: {}",exception.getMessage());
                                    }
                            )
                                .successHandler(authSuccessHandler)
                );
                        return httpSecurity.build();
    }
}
