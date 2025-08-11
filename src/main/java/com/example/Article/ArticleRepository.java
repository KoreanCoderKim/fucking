package com.example.Article;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Article a where a.id = :id")
    Article findByIdForUpdate(@Param("id") Long id);
    List<Article> findByRoomId(String RoomId);
    List<Article> findByUsId(String usId);
}




