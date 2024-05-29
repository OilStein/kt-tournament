package com.backend.models;

import com.backend.controllers.GameController;
import com.backend.controllers.PlayerController;
import com.backend.objects.Game;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameModelAssembler implements RepresentationModelAssembler<Game, EntityModel<Game>> {

        @Override
        public EntityModel<Game> toModel(Game game) {
            return EntityModel.of(game,
                    linkTo(methodOn(GameController.class).one(game.getId())).withSelfRel(),
                    linkTo(methodOn(GameController.class).all()).withRel("games"),
                    linkTo(methodOn(PlayerController.class).one(game.getPlayer1Id())).withRel("player1"),
                    linkTo(methodOn(PlayerController.class).one(game.getPlayer2Id())).withRel("player2")
            );
        }
}
