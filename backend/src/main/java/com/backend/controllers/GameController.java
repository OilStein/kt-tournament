package com.backend.controllers;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.backend.exceptions.GameNotFoundException;
import com.backend.models.GameModelAssembler;
import com.backend.objects.Game;
import com.backend.repositories.GameRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameModelAssembler assembler;

    private final GameRepository repository;

    public GameController(GameRepository repository, GameModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/games")
    public CollectionModel<EntityModel<Game>> all() {
        List<EntityModel<Game>> games = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(games, linkTo(methodOn(GameController.class).all()).withSelfRel());
    }

    @GetMapping("/games/{id}")
    public EntityModel<Game> one(@PathVariable Long id) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        return assembler.toModel(game);
    }

    @PostMapping("/games")
    public ResponseEntity<EntityModel<Game>> newGame(@RequestBody Game newGame) {
        EntityModel<Game> entityModel = assembler.toModel(repository.save(newGame));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<EntityModel<Game>> updateGame(@RequestBody Game newGame, @PathVariable Long id) {
        Game updatedGame = repository.findById(id)
                .map(game -> {
                    game.setStatus(newGame.getStatus());
                    game.setPlayer1VictoryPoints(newGame.getPlayer1VictoryPoints());
                    game.setPlayer2VictoryPoints(newGame.getPlayer2VictoryPoints());
                    game.setPlayer1TacOpsPoints(newGame.getPlayer1TacOpsPoints());
                    game.setPlayer2TacOpsPoints(newGame.getPlayer2TacOpsPoints());
                    game.setPlayer1Painted(newGame.isPlayer1Painted());
                    game.setPlayer2Painted(newGame.isPlayer2Painted());
                    return repository.save(game);
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
        repository.deleteById(id);
    }
}
