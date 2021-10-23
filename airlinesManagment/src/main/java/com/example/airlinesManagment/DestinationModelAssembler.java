package com.example.airlinesManagment;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class DestinationModelAssembler implements RepresentationModelAssembler<Destination, EntityModel<Destination>> {

  @Override
  public EntityModel<Destination> toModel(Destination destination) {

    return EntityModel.of(destination);
    // return EntityModel.of(airline, //
    //     linkTo(methodOn(AirlineController.class).one(airline.getId())).withSelfRel(),
    //     linkTo(methodOn(AirlineController.class).all()).withRel("airlines"));
  }
}
