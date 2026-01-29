package com.todomanager.todos.Controller;

import com.todomanager.todos.DTO.ProfileDTO;
import com.todomanager.todos.DTO.ProfileResponseDTO;
import com.todomanager.todos.Service.ServiceInterface.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProfileResponseDTO> getProfileById(@PathVariable Long id){
        ProfileResponseDTO profile = profileService.getCurrentProfile(id);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> getProfileAndDeleteById(@PathVariable Long id){
        Boolean data = profileService.deleteProfile(id);
        if (data) return ResponseEntity.ok("Profile Deleted Successfully.");
        return ResponseEntity.badRequest().body("Something went wrong try again.");
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProfileResponseDTO> updateProfile(@RequestBody ProfileDTO profileDTO, @PathVariable Long id){
        ProfileResponseDTO profile = profileService.updateCurrentProfile(profileDTO,id);
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

}
