package com.example.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Article a where a.id = :id")
    Article findByIdForUpdate(Long id);
    List<Article> findByRoomId(String RoomId);
    List<Article> findByUsId(String usId);
}


