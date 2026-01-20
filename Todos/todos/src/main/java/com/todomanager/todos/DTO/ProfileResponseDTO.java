package com.todomanager.todos.DTO;

import com.todomanager.todos.Entity.TodoEntity;
import org.apache.catalina.LifecycleState;

import java.time.LocalDateTime;
import java.util.List;


public class ProfileResponseDTO {
        private Long id;
        private String name;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
        private List<TodoEntity> todo;

    public ProfileResponseDTO() {
    }

    public ProfileResponseDTO(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime updateAt,List<TodoEntity> todo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.todo = todo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public List<TodoEntity> getTodo(){
        return this.todo;
    }
    public void setTodo(List<TodoEntity> todo){
        this.todo = todo;
    }

}
