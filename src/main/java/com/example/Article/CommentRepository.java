package com.example.Article;

import org.springframework.data.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Comment a where a.id = :id")
    Comment findByIdForUpdate(@Param("id") Long id);
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByUsName(String usName);
}



