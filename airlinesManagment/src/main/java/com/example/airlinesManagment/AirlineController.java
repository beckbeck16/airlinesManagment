package com.example.airlinesManagment;


import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class AirlineController{

    private final AirlineRepository repository;
    private final AirlineModelAssembler assembler;
    private final DestinationRepository destinationRepository;
    // private final AircraftRepository aircraftRepository;
    // private final AircraftModelAssembler aircraftAssembler;

    public AirlineController(AirlineRepository repository, AirlineModelAssembler assembler,
        // AircraftRepository aircraftRepository, AircraftModelAssembler aircraftAssembler,
        DestinationRepository destinationRepository) {
      this.repository = repository;
      this.assembler = assembler;
      this.destinationRepository = destinationRepository;
      // this.aircraftRepository = aircraftRepository;
      // this.aircraftAssembler = aircraftAssembler;
    }

    @PostMapping("/airlines")
    ResponseEntity<?> newAirline(@RequestBody Airline newAirline) {
    
      EntityModel<Airline> entityModel = assembler.toModel(repository.save(newAirline));
    
      return ResponseEntity 
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
          .body(entityModel);
    }

    @GetMapping("/airlines")
    CollectionModel<EntityModel<Airline>> all() {
    
      List<EntityModel<Airline>> airlines = repository.findAll().stream() 
          .map(assembler::toModel) 
          .collect(Collectors.toList());
    
      return CollectionModel.of(airlines, linkTo(methodOn(AirlineController.class).all()).withSelfRel());
    }

    @GetMapping("/airlines/{id}")
    ResponseEntity<Airline> one(@PathVariable Long id) {
      
      Optional<Airline> airline = repository.findById(id);

      if (airline.isPresent()) {
          return new ResponseEntity<Airline>(airline.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/airlinesBudget")
    ResponseEntity<List<AirlinesBudget>> getAirlinesBudget() {
    
      List<AirlinesBudget> airlines = repository.findAll().stream() 
          .map(airline -> new AirlinesBudget(airline.getName(), airline.getInitialBudget())) 
          .collect(Collectors.toList());
          
      return new ResponseEntity<List<AirlinesBudget>>(airlines, HttpStatus.OK);
    }

    @GetMapping("/airlines/{id}/getDestinationsDistance")
    ResponseEntity<List<DestinationsDistance>> getDestinationsDistance(@PathVariable Long id) {
      List<DestinationsDistance> destinationsDistance = new ArrayList<DestinationsDistance>();

      Optional<Airline> airline = repository.findById(id);
          // .orElseThrow(() -> new AirlineNotFoundException(id));

      if (airline.isPresent()){
        Airline currAirline = airline.get();
        // Location airlineLocation = currAirline.getHomeBaseLocation().getLocation();

        destinationsDistance = currAirline.getDistanceFromDestinations(destinationRepository.findAll());
        // destinationsDistance = destinationRepository.findAll().stream() 
        //     .map(destination -> new DestinationsDistance(destination.getName(), destination.getDistanceTo(airlineLocation)))
        //     .collect(Collectors.toList());

            return new ResponseEntity<List<DestinationsDistance>>(destinationsDistance, HttpStatus.OK);
      }

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
    @GetMapping("/airlines/{id}/getAirlineAvailableDestinations")
    ResponseEntity<List<Destination>> getAirlineAvailableDestinations(@PathVariable Long id) {
      List<Destination> availableDestinations = new ArrayList<>();

      Optional<Airline> airline = repository.findById(id);

      if (airline.isPresent()){
        Airline currAirline = airline.get();

        availableDestinations = currAirline.getAvailableDestinations(destinationRepository.findAll());

        return new ResponseEntity<List<Destination>>(availableDestinations, HttpStatus.OK);
      }

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/airlines/{id}/addNewAircraft")
    Aircraft addNewAircraft(@PathVariable long id, @RequestBody Aircraft newAircraft) {
      
      Optional<Airline> airline = repository.findById(id);

      if (airline.isPresent()){
        Airline currAirline = airline.get();
        currAirline.addAircraft(newAircraft);

         repository.save(currAirline);
      }
    
      return newAircraft;
    }
}