package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;

@Setter
public class CommentDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long articleId;
    @Column
    private String usName;
    @Column
    private String commentValue;

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public CommentDto(String usName, String commentValue) {
        this.usName = usName;
        this.commentValue = commentValue;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", usName='" + usName + '\'' +
                ", commentValue='" + commentValue + '\'' +
                '}';
    }

    public Comment toEntity() { return new Comment(id, articleId, usName, commentValue); }
}
