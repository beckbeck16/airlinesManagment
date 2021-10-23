package com.example.airlinesManagment;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class AircraftModelAssembler implements RepresentationModelAssembler<Aircraft, EntityModel<Aircraft>> {

  @Override
  public EntityModel<Aircraft> toModel(Aircraft aircraft) {

    return EntityModel.of(aircraft);
  
  }
}
