package com.example.airlinesManagment;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DestinationController{

    private final DestinationRepository repository;
    private final DestinationModelAssembler assembler;

    public DestinationController(DestinationRepository repository, DestinationModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/Destinations")
    ResponseEntity<?> newDestination(@RequestBody Destination newDestination) {
    
      EntityModel<Destination> entityModel = assembler.toModel(repository.save(newDestination));
    
      return ResponseEntity 
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
          .body(entityModel);
    }

    @GetMapping("/Destinations")
    CollectionModel<EntityModel<Destination>> all() {
    
      List<EntityModel<Destination>> destination = repository.findAll().stream() 
          .map(assembler::toModel) 
          .collect(Collectors.toList());
    
      return CollectionModel.of(destination);//, linkTo(methodOn(DestinationController.class).all()).withSelfRel());
    }
}