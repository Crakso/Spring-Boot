package com.todomanager.todos;

import com.todomanager.todos.DTO.AuthDTO;
import com.todomanager.todos.Entity.ProfileEntity;
import com.todomanager.todos.Repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootTest
public class PatientTest {
    @Autowired
    private ProfileRepository profileRepo;


    @Test
    public void findALlPatientWithTodos(){
//        profileRepo.findAll(); // N+1 query problem.
       List<ProfileEntity> profile = profileRepo.findAllProfileWithTodos();
        for (ProfileEntity p : profile){
            System.out.println(p);
        }
    }

    @Test
    public void findByProfileIdWithTodos(){
        ProfileEntity profile = profileRepo.findByProfileIdWithTodos(2L);
        System.out.println(profile);
    }

    @Test
    public void findAndDeletePatientById(){
        profileRepo.deleteById(2L);
    }

    @Test
    public void findProfileByNameAndPassword(){
        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail("neeraj@gmail.com");
        authDTO.setPassword("123456");
        profileRepo.findByEmailAndPassword(authDTO.getEmail(),authDTO.getPassword());
    }

}
