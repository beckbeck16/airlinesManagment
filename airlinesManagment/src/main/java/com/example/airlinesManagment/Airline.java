package com.example.airlinesManagment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Airline {
    
    @Id 
    @GeneratedValue
    private Long airline_id;

    private String name;
    private int initialBudget;

    
    @ManyToOne
    @JoinColumn(name="dest_id")
    private Destination homeBaseLocation;
    
    private LocalDateTime joinedAt; 
    
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Aircraft> aircrafts;

    Airline() {}

    Airline(String name, int initialBudget, Destination homeBaseLocation) {

        this.name = name;
        this.initialBudget = initialBudget;
        this.homeBaseLocation = homeBaseLocation;
        this.joinedAt = LocalDateTime.now();
        this.aircrafts = new HashSet<>();
    }

    public Set<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(Set<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    public Long getAirline_id() {
        return airline_id;
    }

    public void setAirline_id(Long airline_id) {
        this.airline_id = airline_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(int initialBudget) {
        this.initialBudget = initialBudget;
    }
    
    public Destination getHomeBaseLocation() {
        return homeBaseLocation;
    }

    public void setHomeBaseLocation(Destination homeBaseLocation) {
        this.homeBaseLocation = homeBaseLocation;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "Airline [aircrafts=" + "aircrafts" + ", homeBaseLocation=" + homeBaseLocation + ", id=" + airline_id
                + ", initialBudget=" + initialBudget + ", joinedAt=" + joinedAt + ", name=" + name + "]";
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircrafts.add(aircraft);
        aircraft.setAirline(this);
    }

    public void deleteAircraft(Aircraft aircraft) {
        this.aircrafts.remove(aircraft);
    }

    public List<Destination> getAvailableDestinations(List<Destination> destinations){
        List<Destination> availablDestinations = new ArrayList<>();
        Location airlineLocation = this.getHomeBaseLocation().getLocation();

        // Get the maximum distance the airline can fly to
        Optional<Aircraft> maxAircraftDistance= this.aircrafts.stream()
                                            .max(Comparator.comparing(Aircraft::getMaxDistance));

        if(maxAircraftDistance.isPresent()){
            double maxDistance = maxAircraftDistance.get().getMaxDistance();
            
            destinations.stream().forEach(destination -> {
                
                // Check that the destination isn't the homeBase
                if(!destination.equals(this.getHomeBaseLocation())){
                    double distanceToDest = destination.getDistanceTo(airlineLocation);

                    if(distanceToDest <= maxDistance){
                        availablDestinations.add(destination);
                    }
                }
            });
        }

        return availablDestinations;
    }

    public List<DestinationsDistance> getDistanceFromDestinations(List<Destination> destinations) {
        
        List<DestinationsDistance> destinationsDistance = new ArrayList<DestinationsDistance>();
        Location airlineLocation = this.getHomeBaseLocation().getLocation();

        destinationsDistance = destinations.stream() 
            .map(destination -> new DestinationsDistance(destination.getName(), destination.getDistanceTo(airlineLocation)))
            .collect(Collectors.toList());

        return destinationsDistance;
    }
}
