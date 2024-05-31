package com.backend.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.backend.models.PlayerModelAssembler;
import com.backend.objects.Player;
import com.backend.repositories.PlayerRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the player entity
 */
@RestController
public class PlayerController {

    private final PlayerModelAssembler assembler;

    private final PlayerRepository repository;

    public PlayerController(PlayerRepository repository, PlayerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Get all players
     * @return Restful Json array of all players
     */
    // tag::get-aggregate-root[]
    @GetMapping("/players")
    public CollectionModel<EntityModel<Player>> all() {
        List<EntityModel<Player>> players = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(players, linkTo(methodOn(PlayerController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    /**
     * Get a single player
     * @param id The id of the player
     * @return Restful Json object of the player
     */
    // tag::get-single-item[]
    @GetMapping("/players/{id}")
    public EntityModel<Player> one(@PathVariable Long id) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return assembler.toModel(player);
    }
    // end::get-single-item[]

    /**
     * Create a new player
     * @param newPlayer The player to be created
     * @return Restful Json object of the created player
     */
    @PostMapping("/players")
    public ResponseEntity<EntityModel<Player>> newPlayer(@RequestBody Player newPlayer) {
        EntityModel<Player> entityModel = assembler.toModel(repository.save(newPlayer));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
