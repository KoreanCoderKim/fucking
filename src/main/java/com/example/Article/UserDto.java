package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="Us")
public class UserDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String userId;
    @Column
    private String password;

    public UserDto(String userId, String password) {
        this.userId = userId;
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
