package com.example.Article;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Reply a where a.id = :id")
    Reply findByIdForUpdate(@Param("id") Long id);
    List<Reply> findByCommentId(Long commentId);
    List<Reply> findByUsName(String usName);
}





