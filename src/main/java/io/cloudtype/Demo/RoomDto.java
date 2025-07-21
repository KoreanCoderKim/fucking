package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class RoomDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String roomId;

    public RoomDto(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "id=" + id +
                ", roomId='" + roomId + '\'' +
                '}';
    }

    public Room toEntity() {
        return new Room(null, roomId);
    }
}
