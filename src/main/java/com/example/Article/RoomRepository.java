package com.example.Article;

import jakarta.persistence.Table;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
@Table(name="Us")
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select r from Room r where r.id = :id")
    Room findByIdForUpdate(@Param("id") Long id);
    boolean existsByRoomId(String roomId);
}




