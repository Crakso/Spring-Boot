package com.todomanager.todos.Service.ServiceInterface;

import com.todomanager.todos.DTO.ProfileDTO;
import com.todomanager.todos.DTO.ProfileResponseDTO;
import com.todomanager.todos.Entity.ProfileEntity;

import java.util.List;

public interface ProfileService {
    public List<ProfileDTO> getAllProfile();
    public ProfileResponseDTO updateCurrentProfile(ProfileDTO profileDTO,Long ProfileId);
    public Boolean deleteProfile(Long profileId);
    public ProfileResponseDTO getCurrentProfile(Long profileId);
    public ProfileDTO toDto(ProfileEntity profile);
}
