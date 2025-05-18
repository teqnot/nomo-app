package com.example.nomo.service;

import com.example.nomo.model.Room;
import com.example.nomo.model.User;
import com.example.nomo.repository.RoomRepository;
import com.example.nomo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomMemberService roomMemberService;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository, RoomMemberService roomMemberService) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.roomMemberService = roomMemberService;
    }

    public Room createRoom(String name, Long creatorId) {
        User creator = userRepository.findById(creatorId).orElseThrow(() -> new RuntimeException("User not found"));

        Room room = new Room();
        room.setName(name);
        room.setCreator(creator);
        room.setCreatedAt(LocalDateTime.now());
        roomRepository.save(room);

        roomMemberService.addUserToRoom(creator.getId(), room.getId());

        return room;
    }
}
