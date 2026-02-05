package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.LogInRequest;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.LogInResponse;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.ProviderType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.SignUpResponse;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    public User signupInternal(LogInRequest signupRequest, ProviderType providerType, String providerId){

        User user = User.builder()
                .username(signupRequest.getUsername())
                .providerType(providerType)
                .providerId(providerId)
                .build();
        if (providerType==ProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        }
        return userRepo.save(user);
    }


    public ResponseEntity<SignUpResponse> signUp(LogInRequest signupRequest){
        try{
            User user = (User) userRepo.findByUsername(signupRequest.getUsername()).orElse(null);
            if(user==null){
                user= signupInternal(signupRequest,ProviderType.EMAIL,null);
                return new ResponseEntity<>(new SignUpResponse(user.getId(),user.getUsername()),HttpStatus.OK);
            }
            throw new IllegalArgumentException("Username is already exist.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new SignUpResponse(),HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<LogInResponse> handleOauth2loginRequest(OAuth2User user, String registrationId) {

        //fetch providerTYpe and providerId
        ProviderType providerType= authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.getProviderIdFromOauth2User(user, registrationId);

        // check is user with this providerType and providerId already exist if exist then directly login.

        User userExist = userRepo.findByProviderTypeAndProviderId(providerType,providerId).orElse(null);

        String email = user.getAttribute("email");

        User getuserbyemail = userRepo.findByUsername(email).orElse(null);

        if(userExist==null || getuserbyemail==null){
            //signup first. user not exist into the database.

           String username = authUtil.findUsernameFromOauth2User(user,registrationId,providerId);
            userExist = signupInternal(new LogInRequest(username,null), providerType,providerId);
        }

        else if(userExist!=null){
            if(email!=null && !email.isBlank()){
                userExist.setUsername(email);
               userExist = userRepo.save(userExist);
            }
        }else {
            throw new BadCredentialsException("This email is already registered with provider"+ email);
        }

        LogInResponse logInResponse = new LogInResponse(authUtil.generateAccessToken(userExist),userExist.getId());
        return ResponseEntity.ok(logInResponse);

    }
}
