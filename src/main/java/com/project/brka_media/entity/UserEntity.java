package com.project.brka_media.entity;

import com.project.brka_media.DTO.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Date;

@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;

    public UserEntity(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

