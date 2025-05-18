package com.example.nomo.repository;

import com.example.nomo.model.Room;
import com.example.nomo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByCreator(User creator);
}
