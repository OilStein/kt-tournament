package com.backend.controllers;

import com.backend.models.ScoreModelAssembler;
import com.backend.objects.Score;
import com.backend.repositories.ScoreRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ScoreController {

    private final ScoreRepository repository;
    private final ScoreModelAssembler assembler;

    ScoreController(ScoreRepository repository, ScoreModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // tag::get-aggregate-root[]
    @GetMapping("/scores")
    public CollectionModel<EntityModel<Score>> all() {
        List<EntityModel<Score>> scores = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(scores, linkTo(methodOn(ScoreController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // tag::get-single-item[]
    @GetMapping("/scores/{id}")
    public EntityModel<Score> one(@PathVariable Long id) {
        Score score = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Score not found"));
        return assembler.toModel(score);
    }
    // end::get-single-item[]
}
