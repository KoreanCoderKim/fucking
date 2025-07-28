package com.example.Article;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Long> {
    List<Reply> findByCommentId(Long commentId);
}
