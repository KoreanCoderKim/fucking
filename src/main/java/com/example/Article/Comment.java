package com.example.Article;
import lombok.Getter;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long articleId;
    @Column
    private String usName;
    @Column
    private String commentValue;
    @Column
    private boolean isMode = true;
    public void updateCV(String commentValue) {
        this.commentValue = commentValue;
    }
    public Comment(Long id, Long articleId, String usName, String commentValue, boolean isMode) {
        this.articleId = articleId;
        this.id = id;
        this.commentValue = commentValue;
        this.usName = usName;
        this.isMode = isMode;
    }
    public boolean getisMode() {
        return isMode;
    }
    public void setUsName(String usName) { this.usName = usName; }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", usName='" + usName + '\'' +
                ", commentValue='" + commentValue + '\'' +
                ", isMode=" + isMode +
                '}';
    }

    public void NotAcceptedReply() {
        isMode = false;
    }
}
