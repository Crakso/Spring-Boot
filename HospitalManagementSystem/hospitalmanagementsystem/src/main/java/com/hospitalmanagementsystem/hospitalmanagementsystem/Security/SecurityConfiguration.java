package com.hospitalmanagementsystem.hospitalmanagementsystem.Security;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.PermissionType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.PermissionType.APPOINTMENT_DELETE;
import static com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.PermissionType.USER_MANAGE;
import static com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType.*;


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
                .authorizeHttpRequests(auth-> auth
                                .requestMatchers("/auth/**","/public/**").permitAll()
                                .requestMatchers("/admin/**").hasRole(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE,"/admin/**").hasAnyAuthority(APPOINTMENT_DELETE.name(),USER_MANAGE.name())
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
