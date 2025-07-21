package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class FindDto {
    @Id
    @GeneratedValue
    @Column
    private String Room;

    public FindDto(String Room) {
        this.Room = Room;
    }

}
