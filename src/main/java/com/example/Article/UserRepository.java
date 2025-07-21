package com.example.Article;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsId(String usId);

    List<User> findByUsId(String usId);
}
