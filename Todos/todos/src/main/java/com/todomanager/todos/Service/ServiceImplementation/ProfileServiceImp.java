package com.todomanager.todos.Service.ServiceImplementation;

import com.todomanager.todos.DTO.AuthDTO;
import com.todomanager.todos.DTO.ProfileDTO;
import com.todomanager.todos.DTO.ProfileResponseDTO;
import com.todomanager.todos.Entity.ProfileEntity;
import com.todomanager.todos.Repository.ProfileRepository;
import com.todomanager.todos.Service.ServiceInterface.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImp implements ProfileService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImp(ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProfileResponseDTO registerProfile(ProfileDTO profileDTO){
    ProfileEntity profile = modelMapper.map(profileDTO, ProfileEntity.class);
    profile.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
    ProfileEntity newprofile = profileRepository.save(profile);
    return modelMapper.map(newprofile, ProfileResponseDTO.class);
    }

    @Override
    public ProfileResponseDTO loginProfile(AuthDTO authDTO){
        ProfileEntity profile = profileRepository.findByEmailAndPassword(authDTO.getEmail(), authDTO.getPassword());

        return modelMapper.map(profile,ProfileResponseDTO.class);

    }







    @Override
    public ProfileResponseDTO getCurrentProfile(Long profileId){
        ProfileEntity profile= profileRepository.findById(profileId)
                .orElseThrow(()->new UsernameNotFoundException("User with this id not found. "+profileId));
        return modelMapper.map(profile,ProfileResponseDTO.class);
    }

    public ProfileResponseDTO updateCurrentProfile(ProfileDTO profileDTO,Long ProfileId){
        ProfileEntity profile = profileRepository.findById(ProfileId)
                .orElseThrow(()->new UsernameNotFoundException("Given profile id is invalid "+ProfileId));
        profile.setName(profileDTO.getName());
        ProfileEntity updateProfile = profileRepository.save(profile);
        return modelMapper.map(updateProfile,ProfileResponseDTO.class);
    }

    @Override
    public Boolean deleteProfile(Long profileId){
        try {
            profileRepository.deleteById(profileId);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<ProfileDTO> getAllProfile() {
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        List<ProfileEntity> profileList = profileRepository.findAll();
        for (ProfileEntity profile : profileList){
           profileDTOList.add(modelMapper.map(profile,ProfileDTO.class));
        }
        return profileDTOList;
    }

    @Override
    public ProfileDTO toDto(ProfileEntity profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setCreatedAt(profile.getCreatedAt());
        profileDTO.setUpdateAt(profile.getUpdateAt());
    return profileDTO;
    }


}
