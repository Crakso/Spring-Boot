package com.hospitalmanagementsystem.hospitalmanagementsystem.Security;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("request:- "+request + "\n response:- "+response + "\n authentication:- "+authentication);
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();

        String RegistrationId = auth2AuthenticationToken.getAuthorizedClientRegistrationId();

        authService.handleOauth2loginRequest(user, RegistrationId);

    }
}
