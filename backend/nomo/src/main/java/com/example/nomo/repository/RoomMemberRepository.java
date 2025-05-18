package com.example.nomo.repository;

import com.example.nomo.model.Room;
import com.example.nomo.model.RoomMember;
import com.example.nomo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByRoom(Room room);
    List<RoomMember> findByUser(User user);
    boolean existsByUserAndRoom(User user, Room room);
}
