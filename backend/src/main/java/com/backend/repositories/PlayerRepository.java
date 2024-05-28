package com.backend.repositories;

import com.backend.objects.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByUsername(String username);
}
