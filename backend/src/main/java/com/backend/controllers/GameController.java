package com.backend.controllers;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.backend.exceptions.GameNotFoundException;
import com.backend.models.GameModelAssembler;
import com.backend.objects.Game;
import com.backend.objects.Score;
import com.backend.objects.Status;
import com.backend.repositories.GameRepository;
import com.backend.repositories.ScoreRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameModelAssembler assembler;

    private final GameRepository gameRepository;
    private final ScoreRepository scoreRepository;

    public GameController(GameRepository gameRepository, ScoreRepository scoreRepository, GameModelAssembler assembler) {
        this.gameRepository = gameRepository;
        this.scoreRepository = scoreRepository;
        this.assembler = assembler;
    }

    // tag::get-aggregate-root[]
    @GetMapping("/games")
    public CollectionModel<EntityModel<Game>> all() {
        List<EntityModel<Game>> games = gameRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(games, linkTo(methodOn(GameController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // tag::get-single-item[]
    @GetMapping("/games/{id}")
    public EntityModel<Game> one(@PathVariable Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        return assembler.toModel(game);
    }
    // end::get-single-item[]

    @PostMapping("/games")
    public ResponseEntity<EntityModel<Game>> newGame(@RequestBody Game newGame) {
        Score player1Score = new Score();
        Score player2Score = new Score();
        scoreRepository.save(player1Score);
        scoreRepository.save(player2Score);

        newGame.setPlayer1Score(player1Score);
        newGame.setPlayer2Score(player2Score);

        EntityModel<Game> entityModel = assembler.toModel(gameRepository.save(newGame));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<EntityModel<Game>> updateGame(@RequestBody Game newGame, @PathVariable Long id) {
        Game updatedGame = gameRepository.findById(id)
                .map(game -> {
                    game.setStatus(newGame.getStatus());
                    return gameRepository.save(game);
                })
                .orElseThrow(
                        () -> new GameNotFoundException(id)
                );

        EntityModel<Game> entityModel = assembler.toModel(updatedGame);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
    }

    @PutMapping("/games/{id}/complete")
    public ResponseEntity<EntityModel<Game>> completeGame(@PathVariable Long id) {
        Game updatedGame = gameRepository.findById(id)
                .map(game -> {
                    game.setStatus(Status.COMPLETED);
                    return gameRepository.save(game);
                })
                .orElseThrow(
                        () -> new GameNotFoundException(id)
                );

        EntityModel<Game> entityModel = assembler.toModel(updatedGame);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
