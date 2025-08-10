package com.example.Article;

import com.example.Article.ArticleController;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

public class ModifyDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String Value;
    public ModifyDto(String Value) {
        this.Value = Value;
    }

    public ModifyEntity toEntity() {
        return new ModifyEntity(null, Value);
    }
}
