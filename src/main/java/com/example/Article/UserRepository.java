package com.example.Article;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from User a where a.id = :id")
    User findByIdForUpdate(@Param("id") Long id);
    boolean existsByUsId(String usId);

    List<User> findByUsId(String usId);
}





