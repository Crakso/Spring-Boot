package com.hospitalmanagementsystem.hospitalmanagementsystem.Util;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.ProviderType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.securetKey}")
    private String secretKey;
    private final UserRepository userRepository;

    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
       return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getKey())
                .compact();
    }


    public String validateTokenAndGetUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public ProviderType getProviderTypeFromRegistrationId(String registrationId) {
        return switch (registrationId.toLowerCase()){
            case "google"-> ProviderType.GOOGLE;
            case "github"-> ProviderType.GITHUB;
            default -> throw new IllegalArgumentException("unsupported Oauth2 provider type"+ registrationId);
        };
    }

    public String getProviderIdFromOauth2User(OAuth2User user, String registrationId) {
       String providerId = switch(registrationId.toLowerCase()){
            case "google"-> user.getAttribute("sub");
            case "github"-> user.getAttribute("id").toString();
            default -> throw new IllegalArgumentException("unsupported Oauth2 provider id."+ registrationId);
        };
       if(providerId==null||providerId.isBlank()) {

       throw new IllegalArgumentException("unable to determine provider id."+registrationId);
       }
           return providerId;


    }

    public String findUsernameFromOauth2User(OAuth2User user, String registrationId, String providerId) {
        String email = user.getAttribute("email");
        if(email!=null||!email.isBlank()){
            return email;
        }
        return switch (registrationId.toLowerCase()){
            case "google" -> user.getAttribute("sub");
            case "github" -> user.getAttribute("id");
            default -> providerId;
        };
    }


    public User getCurrentUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElse(null);

    }
}
