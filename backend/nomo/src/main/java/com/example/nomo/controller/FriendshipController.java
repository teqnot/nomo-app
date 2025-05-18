package com.example.nomo.controller;

import com.example.nomo.dto.FriendshipDto;
import com.example.nomo.model.User;
import com.example.nomo.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) { this.friendshipService = friendshipService; }

    @PostMapping("/send")
    public ResponseEntity<Void> sendRequest(@RequestBody Map<String, Long> body) {
        friendshipService.sendFriendRequest(body.get("fromId"), body.get("toId"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptRequest(@RequestBody Map<String, Long> body) {
        friendshipService.acceptFriendRequest(body.get("requestId"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> getFriends(@RequestBody Map<String, Long> body) {
        User user = new User();
        user.setId(body.get("userId"));
        return ResponseEntity.ok(friendshipService.getFriends(user));
    }
}
