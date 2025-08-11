package com.example.Article;

import org.springframework.data.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from User a where a.id = :id")
    User findByIdForUpdate(@Param("id") Long id);
    boolean existsByUsId(String usId);

    List<User> findByUsId(String usId);
}



