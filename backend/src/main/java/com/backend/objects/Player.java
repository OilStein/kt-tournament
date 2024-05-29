package com.backend.objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@Table(name = "PLAYERS")
public class Player {
    private @Id @GeneratedValue Long id;

    @Column(unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    public Player() {}

    public Player(String username) {
        this.username = username;
    }
}
