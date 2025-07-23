package com.example.Article;

import com.example.Article.ArticleController;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

public class ArticleDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String roomId;
    @Column
    private String title;
    @Column
    private String News;

    public ArticleDto(String userId, int password, String title, String News) {
        this.title = title;
        this.News = News;
    }

    public Article toEntity(String Data, String usId, int password) {
        return new Article(null, usId, password, Data, title, News);
    }
}
