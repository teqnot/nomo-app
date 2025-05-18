package com.example.nomo.service;

import com.example.nomo.dto.RoomDto;
import com.example.nomo.dto.RoomMemberDto;
import com.example.nomo.model.Room;
import com.example.nomo.model.RoomMember;
import com.example.nomo.model.User;
import com.example.nomo.repository.RoomMemberRepository;
import com.example.nomo.repository.RoomRepository;
import com.example.nomo.repository.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomMemberService {
    private final RoomMemberRepository roomMemberRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public RoomMemberService(RoomMemberRepository roomMemberRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.roomMemberRepository = roomMemberRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public void addUserToRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();

        if (roomMemberRepository.existsByUserAndRoom(user, room)) {
            throw new RuntimeException("User already in room.");
        }

        RoomMember roomMember = new RoomMember();
        roomMember.setUser(user);
        roomMember.setRoom(room);
        roomMember.setJoinedAt(LocalDateTime.now());

        roomMemberRepository.save(roomMember);
    }

    public List<Map<String, Object>> getRoomMembers(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found."));

        List<User> members = roomMemberRepository.findByRoom(room).stream()
                .map(RoomMember::getUser)
                .collect(Collectors.toList());

        List<Map<String, Object>> response = new ArrayList<>();

        for (User member : members) {
            RoomMemberDto roomMemberDto = new RoomMemberDto(member.getId(), member.getUsername());
            response.add(roomMemberDto.convertToRoomMemberDto());
        }

        return response;
    }

    public List<Map<String, Object>> getRoomsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Room> rooms = roomMemberRepository.findByUser(user).stream()
                .map(RoomMember::getRoom)
                .collect(Collectors.toList());

        List<Map<String, Object>> response = new ArrayList<>();

        for (Room room : rooms) {
            RoomDto roomDto = new RoomDto(room.getId(), room.getCreator().getId(), room.getName(), room.getDescription());
            User creator = userRepository.findById(room.getCreator().getId()).orElseThrow();
            response.add(roomDto.convertToRoomDto(creator));
        }

        return response;
    }

}
