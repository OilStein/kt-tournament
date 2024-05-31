package com.backend.utilities;

import com.backend.objects.Game;
import com.backend.objects.Player;
import com.backend.objects.Score;
import com.backend.repositories.GameRepository;
import com.backend.repositories.PlayerRepository;

import com.backend.repositories.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(PlayerRepository plRepo, GameRepository gmRepo, ScoreRepository scRepo) {
        return args -> {
            // Players
            log.info("Preloading {}", plRepo.save(new Player("Bilbo Baggins")));
            log.info("Preloading {}", plRepo.save(new Player("Frodo Baggins")));
            log.info("Preloading {}", plRepo.save(new Player("Samwise Gamgee")));

            // Games
            log.info("Preloading {}", gmRepo.save(new Game(1L, 2L, scRepo.save(new Score()), scRepo.save(new Score()))));
            log.info("Preloading {}", gmRepo.save(new Game(3L, 1L, scRepo.save(new Score()), scRepo.save(new Score()))));
        };
    }
}
