package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class ChanDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String roomId;
    @Column
    private String title;
    @Column
    private String News;

    public ChanDto(String roomId, String title, String News) {
        this.roomId = roomId;
        this.title = title;
        this.News = News;
    }

}
