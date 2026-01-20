package com.todomanager.todos.Repository;

import com.todomanager.todos.DTO.AuthDTO;
import com.todomanager.todos.Entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository  extends JpaRepository<ProfileEntity, Long> {

    @Query("SELECT p FROM ProfileEntity p LEFT JOIN FETCH p.todo")
    public List<ProfileEntity> findAllProfileWithTodos();

    @Query("SELECT p FROM ProfileEntity p LEFT JOIN FETCH p.todo WHERE p.id=:id")
    public ProfileEntity findByProfileIdWithTodos(@Param("id") Long id);

    @Query("SELECT p FROM ProfileEntity p LEFT JOIN FETCH p.todo WHERE p.email=:email AND p.password=:password")
    public ProfileEntity findByEmailAndPassword(@Param("email") String email,@Param("password") String password);

}
