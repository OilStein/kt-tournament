package com.backend.models;

import com.backend.controllers.ScoreController;
import com.backend.objects.Score;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ScoreModelAssembler implements RepresentationModelAssembler<Score, EntityModel<Score>> {

        @Override
        public EntityModel<Score> toModel(Score score) {
            return EntityModel.of(score,
                    linkTo(methodOn(ScoreController.class).one(score.getId())).withSelfRel(),
                    linkTo(methodOn(ScoreController.class).all()).withRel("scores"));
        }
}
