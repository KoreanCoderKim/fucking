package com.example.Article;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Comment a where a.id = :id")
    Comment findByIdForUpdate(@Param("id") Long id);
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByUsName(String usName);
}





