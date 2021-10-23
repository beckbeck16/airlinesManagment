package com.example.airlinesManagment;

public class AirlineNotFoundException extends RuntimeException {
    AirlineNotFoundException(Long id) {
        super("Could not find airline " + id);
      }
}
