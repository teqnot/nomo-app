package com.example.nomo.controller;

import com.example.nomo.model.Room;
import com.example.nomo.model.User;
import com.example.nomo.service.RoomMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room-members")
public class RoomMemberController {
    private final RoomMemberService roomMemberService;

    public RoomMemberController(RoomMemberService roomMemberService) { this.roomMemberService = roomMemberService; }

    @PostMapping("/add")
    public ResponseEntity<Void> addUserToRoom(@RequestBody Map<String, Long> body) {
        roomMemberService.addUserToRoom(body.get("userId"), body.get("roomId"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/members")
    public ResponseEntity<List<Map<String, Object>>> getRoomMembers(@RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(roomMemberService.getRoomMembers(body.get("roomId")));
    }

    @PostMapping("/user-rooms")
    public ResponseEntity<List<Map<String, Object>>> getRoomsByUser(@RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(roomMemberService.getRoomsByUser(body.get("userId")));
    }
}
