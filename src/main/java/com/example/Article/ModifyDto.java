package com.example.Article;

import com.example.Article.ArticleController;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Getter
public class ModifyDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String value;
    public ModifyDto(String value) {
        this.value = value;
    }

    public ModifyEntity toEntity() {
        return new ModifyEntity(null, value);
    }
}
