package com.example.Article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Reply {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long commentId;
    @Column
    private String usName;
    @Column
    private String replyValue;
    @Column
    private boolean isMode = true;
    public Reply(Long id, Long commentId, String usName, String replyValue) {
        this.commentId = commentId;
        this.id = id;
        this.replyValue = replyValue;
        this.usName = usName;
    }
    public void setUsName(String usName) { this.usName=usName; }
    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", usName='" + usName + '\'' +
                ", ReplyValue='" + replyValue + '\'' +
                '}';
    }

    public void NotAcceptedReply() {
        isMode = false;
    }
}
