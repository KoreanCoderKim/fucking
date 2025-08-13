package com.example.Article;
import com.example.Article.ArticleDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String usId;
    @Column(unique = true, nullable = false)
    private String roomId;
    @Column
    private String password;
    @Column
    private String title;
    @Column
    private String News;
    @Column
    private boolean isMode = true;
    public Article(Long id, String usId, String password, String roomId, String title, String News) {
        this.id = id;
        this.roomId = roomId;
        this.usId = usId;
        this.password = password;
        this.News = News;
        this.title = title;
    }
    public boolean getIsMode() { return isMode; }
    public void setUserName(String usId) { this.usId = usId; }
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", usId='" + usId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", password='" + password + '\'' +
                ", title='" + title + '\'' +
                ", News='" + News + '\'' +
                '}';
    }
    public void NotAcceptedUser() { isMode = false; }
    public void update(String roomId, String title, String News) {
        this.roomId = roomId;
        this.title = title;
        this.News = News;
    }
}
