package com.backend.repositories;

import com.backend.objects.Game;
import com.backend.objects.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
