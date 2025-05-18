package com.example.nomo.repository;

import com.example.nomo.model.Friendship;
import com.example.nomo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUserAndStatus(User user, String status);
    Optional<Friendship> findByUserAndFriend(User user, User friend);
}
