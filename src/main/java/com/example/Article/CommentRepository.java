package com.example.Article;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByUsName(Long usName);
}
