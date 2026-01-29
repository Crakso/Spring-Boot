package com.todomanager.todos.Service.ServiceImplementation;

import com.todomanager.todos.DTO.*;
import com.todomanager.todos.Entity.ProfileEntity;
import com.todomanager.todos.Repository.ProfileRepository;
import com.todomanager.todos.Security.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public AuthService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, ProfileRepository profileRepository,AuthenticationManager authenticationManager,AuthUtil authUtil){
        this.modelMapper = modelMapper;
        this.passwordEncoder= passwordEncoder;
        this.profileRepository = profileRepository;
        this.authenticationManager = authenticationManager;
        this.authUtil=authUtil;
    }

    public SignupResponseDTO signupProfile(ProfileDTO profile){
        ProfileEntity profileEntity = profileRepository.findByEmail(profile.getEmail()).orElseThrow(null);
        if(profileEntity!=null) throw new IllegalArgumentException("Email already exists.");
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        ProfileEntity profileDetails = profileRepository.save(modelMapper.map(profile,ProfileEntity.class));
        return new SignupResponseDTO(profileDetails.getId(),profileDetails.getUsername());
    }

    public LoginResponseDTO loginProfile(AuthDTO authDTO){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
        ProfileEntity profile = (ProfileEntity) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(profile);

        return new LoginResponseDTO(token,profile.getId());

    }


}
