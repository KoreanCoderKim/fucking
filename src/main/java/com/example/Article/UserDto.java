package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Setter;
@Setter
@Table(name="Us")
public class UserDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column
    private String password;

    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User toEntity() { return new User(null, userId, password); }
}
