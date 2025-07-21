package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Entity
public class Pos {
    @Id
    @GeneratedValue
    private String RoomId;
    @Column
    private Long item;

    public Pos(String RoomId, Long item) {
        this.RoomId = RoomId;
        this.item = item;
    }

    @Override
    public String toString() {
        return "Pos{" +
                ", RoomId='" + RoomId + '\'' +
                '}';
    }
}
