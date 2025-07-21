package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class PosDto {
    @Id
    @GeneratedValue
    private String RoomId;
    @Column
    private Long item;

    public PosDto(String RoomId) {
        this.RoomId = RoomId;
    }

    public Pos toEntity() {
        return new Pos(RoomId, null);
    }
}