package com.example.Article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Table(name="Us")
@NoArgsConstructor
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String usId;
    @Column
    private Object password;

    public User(Long id, String usId, Object password) {
        this.id = id;
        this.usId = usId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usId='" + usId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
