package com.hospitalmanagementsystem.hospitalmanagementsystem.Controller;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.LogInRequest;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.LogInResponse;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.SignUpResponse;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
private final AuthService authService;

@PostMapping("login")
public ResponseEntity<LogInResponse> Login(@RequestBody LogInRequest logInRequest){
    return authService.logInUser(logInRequest);

}

@PostMapping("signup")
public ResponseEntity<SignUpResponse> signup(@RequestBody LogInRequest signupRequest){
    return authService.signUp(signupRequest);
}

}
