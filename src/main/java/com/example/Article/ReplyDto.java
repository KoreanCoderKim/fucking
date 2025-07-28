package com.example.Article;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;

@Setter
public class ReplyDto {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long commentId;
    @Column
    private String usName;
    @Column
    private String replyValue;

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public ReplyDto(String usName, String replyValue) {
        this.usName = usName;
        this.replyValue = replyValue;
    }

    @Override
    public String toString() {
        return "ReplyDto{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", usName='" + usName + '\'' +
                ", replyValue='" + replyValue + '\'' +
                '}';
    }

    public Reply toEntity() { return new Reply(id, commentId, usName, replyValue); }
}
