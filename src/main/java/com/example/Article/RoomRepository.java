package com.example.Article;

import jakarta.persistence.Table;
import org.springframework.data.repository.JpaRepository;
@Table(name="Us")
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select r from Room r where r.id = :id")
    Room findByIdForUpdate(@Param("id") Long id);
    boolean existsByRoomId(String roomId);
}



