package com.example.Article;

import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
@Table(name="Us")
public interface RoomRepository extends CrudRepository<Room, Long> {
    boolean existsByRoomId(String roomId);
}
