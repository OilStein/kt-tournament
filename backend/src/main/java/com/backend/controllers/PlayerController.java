package com.backend.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.backend.objects.Player;
import com.backend.repositories.PlayerRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    private final PlayerRepository repository;

    public PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    // tag::get-aggregate-root[]
    @GetMapping("/players")
    public CollectionModel<EntityModel<Player>> all() {
        List<EntityModel<Player>> players = repository.findAll().stream()
                .map(player -> EntityModel.of(player,
                        linkTo(methodOn(PlayerController.class).one(player.getId())).withSelfRel(),
                        linkTo(methodOn(PlayerController.class).all()).withRel("players")))
                .toList();
        return CollectionModel.of(players, linkTo(methodOn(PlayerController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // tag::get-single-item[]
    @GetMapping("/players/{id}")
    public EntityModel<Player> one(@PathVariable Long id) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return EntityModel.of(player,
                linkTo(methodOn(PlayerController.class).one(id)).withSelfRel(),
                linkTo(methodOn(PlayerController.class).all()).withRel("players"));
    }
    // end::get-single-item[]

    @PostMapping("/players")
    public Player newPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
