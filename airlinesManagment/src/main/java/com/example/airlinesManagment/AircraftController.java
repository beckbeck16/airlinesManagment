package com.example.airlinesManagment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class AircraftController{

    private final AircraftRepository repository;
    private final AircraftModelAssembler assembler;

    public AircraftController(AircraftRepository repository, AircraftModelAssembler aircraftAssembler) {
        this.repository = repository;
        this.assembler = aircraftAssembler;
    }

    @PostMapping("/aircrafts")
    ResponseEntity<?> newAircraft(@RequestBody Aircraft newAircraft) {
    
      EntityModel<Aircraft> entityModel = assembler.toModel(repository.save(newAircraft));
    
      return ResponseEntity 
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
          .body(entityModel);
    }

    @GetMapping("/aircrafts")
    CollectionModel<EntityModel<Aircraft>> all() {
    
      List<EntityModel<Aircraft>> aircrafts = repository.findAll().stream() 
          .map(assembler::toModel) 
          .collect(Collectors.toList());
    
      return CollectionModel.of(aircrafts, linkTo(methodOn(AircraftController.class).all()).withSelfRel());
    }

    @GetMapping("/aircraft/{id}")
    ResponseEntity<Aircraft> one(@PathVariable Long id) {
  
      Optional<Aircraft> aircraft = repository.findById(id);

      if (aircraft.isPresent()) {
          return new ResponseEntity<Aircraft>(aircraft.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/aircraft/{id}/sellAircraft")
    ResponseEntity<Aircraft> sellAircraft(@PathVariable Long id) {
  
      Optional<Aircraft> aircraft = repository.findById(id);

      if (aircraft.isPresent()) {
          Aircraft airc = aircraft.get();  
        //   airc.sellAircraft(airline); // I need to get a new airline which bought the aircraft

          return new ResponseEntity<Aircraft>(airc, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}