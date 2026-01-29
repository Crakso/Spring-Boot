package com.todomanager.todos.Controller;

import com.todomanager.todos.DTO.*;
import com.todomanager.todos.Service.ServiceImplementation.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponseDTO> loginProfile(@RequestBody AuthDTO authDTO){
        LoginResponseDTO loginResponseDTO= authService.loginProfile(authDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/signup")
    private ResponseEntity<SignupResponseDTO> registerProfile(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(authService.signupProfile(profileDTO));
    }

}
