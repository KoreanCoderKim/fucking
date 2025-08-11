package com.example.Article;

import org.springframework.data.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Reply a where a.id = :id")
    Reply findByIdForUpdate(Long id);
    List<Reply> findByCommentId(Long commentId);
    List<Reply> findByUsName(String usName);
}



