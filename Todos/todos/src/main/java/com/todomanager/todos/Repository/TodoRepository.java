package com.todomanager.todos.Repository;

import com.todomanager.todos.Entity.TodoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM TodoEntity t WHERE t.id=:id")
    public void getTodoAndDeleteById(@Param("id") Long id);


    public TodoEntity getTodoById(Long id);
}
