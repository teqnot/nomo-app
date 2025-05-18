package com.example.nomo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String name;

    @Column(name = "room_description", nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User creator;

    @Column(name = "room_created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}