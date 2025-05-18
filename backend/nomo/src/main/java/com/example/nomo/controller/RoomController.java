package com.example.nomo.controller;

import com.example.nomo.dto.RoomDto;
import com.example.nomo.dto.RoomRequest;
import com.example.nomo.model.Room;
import com.example.nomo.repository.UserRepository;
import com.example.nomo.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) { this.roomService = roomService; }

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomRequest request) {
        Room room = roomService.createRoom(request.getName(), request.getCreatorId());
        RoomDto dto = new RoomDto(room.getId(), room.getCreator().getId(), room.getName(), room.getDescription());
        return ResponseEntity.ok(dto);
    }
}
