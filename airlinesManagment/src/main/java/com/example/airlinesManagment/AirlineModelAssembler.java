package com.example.airlinesManagment;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class AirlineModelAssembler implements RepresentationModelAssembler<Airline, EntityModel<Airline>> {

  @Override
  public EntityModel<Airline> toModel(Airline airline) {

    return EntityModel.of(airline);
    // return EntityModel.of(airline, //
    //     linkTo(methodOn(AirlineController.class).one(airline.getId())).withSelfRel(),
    //     linkTo(methodOn(AirlineController.class).all()).withRel("airlines"));
  }
}
