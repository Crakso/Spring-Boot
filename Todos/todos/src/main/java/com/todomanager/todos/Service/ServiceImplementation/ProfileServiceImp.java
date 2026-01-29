package com.todomanager.todos.Service.ServiceImplementation;

import com.todomanager.todos.DTO.ProfileDTO;
import com.todomanager.todos.DTO.ProfileResponseDTO;
import com.todomanager.todos.Entity.ProfileEntity;
import com.todomanager.todos.Repository.ProfileRepository;
import com.todomanager.todos.Service.ServiceInterface.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImp implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public ProfileServiceImp(ProfileRepository profileRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.modelMapper=modelMapper;
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
        profileDTO.setName(profile.getName());
        profileDTO.setEmail(profile.getUsername());
    return profileDTO;
    }


}
