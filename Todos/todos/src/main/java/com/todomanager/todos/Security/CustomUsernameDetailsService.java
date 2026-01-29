package com.todomanager.todos.Security;

import com.todomanager.todos.Repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUsernameDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public CustomUsernameDetailsService(ProfileRepository profileRepo){
        this.profileRepository=profileRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return profileRepository.findByEmail(username).orElseThrow();
    }
}
