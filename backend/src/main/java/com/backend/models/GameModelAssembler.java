package com.backend.models;

import com.backend.controllers.GameController;
import com.backend.controllers.PlayerController;
import com.backend.controllers.ScoreController;
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
                    linkTo(methodOn(PlayerController.class).one(game.getPlayer1Id())).withRel("players"),
                    linkTo(methodOn(PlayerController.class).one(game.getPlayer2Id())).withRel("players"),
                    linkTo(methodOn(ScoreController.class).one(game.getPlayer1Score().getId())).withRel("scores"),
                    linkTo(methodOn(ScoreController.class).one(game.getPlayer2Score().getId())).withRel("scores")
            );
        }
}
