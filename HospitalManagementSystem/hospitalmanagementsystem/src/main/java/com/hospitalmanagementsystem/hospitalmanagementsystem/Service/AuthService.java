package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.LogInRequest;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.LogInResponse;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.SignUpResponse;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<LogInResponse> logInUser(LogInRequest logInRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String token = authUtil.generateAccessToken(user);

            return new ResponseEntity<>(new LogInResponse(token, user.getId()), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new LogInResponse(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<SignUpResponse> signUp(LogInRequest signupRequest){
        try{
            User user = (User) userRepo.findByUsername(signupRequest.getUsername()).orElse(null);
            if(user==null){
                user=User.builder()
                        .username(signupRequest.getUsername())
                        .password(passwordEncoder.encode(signupRequest.getPassword()))
                        .build();
                userRepo.save(user);
                return new ResponseEntity<>(new SignUpResponse(user.getId(),user.getUsername()),HttpStatus.OK);
            }
            throw new IllegalArgumentException("Username is already exist.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new SignUpResponse(),HttpStatus.BAD_REQUEST);
    }























}
