package com.todomanager.todos.DTO;



public class LoginResponseDTO {

private String jwt;
private Long id;

    public LoginResponseDTO(String jwt, Long id) {
        this.jwt = jwt;
        this.id = id;
    }

    public LoginResponseDTO() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
