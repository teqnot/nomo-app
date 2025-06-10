package com.example.nomo.service;

import com.example.nomo.dto.FriendshipDto;
import com.example.nomo.model.Friendship;
import com.example.nomo.model.User;
import com.example.nomo.repository.FriendshipRepository;
import com.example.nomo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public void sendFriendRequest(Long fromId, Long toId) {
        User fromUser = userRepository.findById(fromId).orElseThrow();
        User toUser = userRepository.findById(toId).orElseThrow();

        if (friendshipRepository.findByUserAndFriend(fromUser, toUser).isPresent() ||
        friendshipRepository.findByUserAndFriend(toUser, fromUser).isPresent()) {
            throw new RuntimeException("Friend request already exists");
        }

        Friendship friendship1 = new Friendship();
        friendship1.setUser(fromUser);
        friendship1.setFriend(toUser);
        friendship1.setCreatedAt(LocalDateTime.now());

        Friendship friendship2 = new Friendship();
        friendship2.setUser(toUser);
        friendship2.setFriend(fromUser);
        friendship2.setCreatedAt(LocalDateTime.now());

        friendshipRepository.saveAll(List.of(friendship1, friendship2));
    }

    public List<Map<String, Object>> getFriends(User user) {
        List<User> friends = friendshipRepository.findByUser(user).stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());
        List<Map<String, Object>> response = new ArrayList<>();

        for (User friend: friends) {
            FriendshipDto friendshipDto = new FriendshipDto(friend.getId(), friend.getUsername());
            response.add(friendshipDto.convertToFriendshipDto());
        }

        return response;
    }
}
