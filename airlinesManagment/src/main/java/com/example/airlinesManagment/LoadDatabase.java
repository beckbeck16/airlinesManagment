package com.example.airlinesManagment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(AirlineRepository airlineRepository, DestinationRepository destinationRepository, AircraftRepository aircraftRepository) {

    return args -> {

      Destination desti = new Destination("Tel Aviv", new Location(34.8, 32.0));
      destinationRepository.save(desti);
      
      Airline airline1 = new Airline("ElAl", 100, desti);
      airlineRepository.save(airline1);

      Aircraft aircraft1 = new Aircraft(20, 1000);
      airline1.addAircraft(aircraft1);

      airlineRepository.save(airline1);

      Destination desti2 = new Destination("Seoul", new Location(127.0, 37.02));
      destinationRepository.save(desti2);
      airlineRepository.save(new Airline("KOREAN AIR", 1000, desti2));

      Destination desti3 = new Destination("Dubai", new Location(55.29, 25.27));
      destinationRepository.save(desti3);
      airlineRepository.save(new Airline("Emirates", 5000, desti3));

    airlineRepository.findAll().forEach(airline -> log.info("Preloaded " + airline));
      
    };
  }
}
