package com.todomanager.todos.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @Email
    @Column(unique = true)
    private String email;

    private String password;
    private Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "profile",cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TodoEntity> todo = new ArrayList<>();

    @PrePersist
    public void preIsActive(){
        if(this.isActive==null) isActive=false;
    }

    //No Args Constructor
    public ProfileEntity(){

    }

    //    AllArgsConstructor
    public ProfileEntity(Long id, String name, String email, String password, Boolean isActive, LocalDateTime createdAt, LocalDateTime updateAt,List<TodoEntity> todo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.todo = todo;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", todo=" + todo +
                '}';
    }


    //Getter And Setter (@Data, @Getter,@Setter).
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TodoEntity> getTodo() {
        return todo;
    }

    public void setTodo(List<TodoEntity> todo) {
        this.todo = todo;
    }
}
