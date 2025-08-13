package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String roomId;

    public Room(Long id, String roomId) {
        this.id = id;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
