package com.example.airlinesManagment;

import java.time.LocalDateTime;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Aircraft {
    
    @Id 
    @GeneratedValue
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    @JsonIgnore
    private Airline airline;

    private double price;
    private double maxDistance;    
    private LocalDateTime joinedAt; 

    Aircraft() {}
    
    public Aircraft(double price, double maxDistance) {
        this.price = price;
        this.maxDistance = maxDistance;
        this.joinedAt = LocalDateTime.now();
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "Aircraft [airline=" +  airline + ", id=" + id + ", joinedAt=" + joinedAt + ", maxDistance=" + maxDistance
                + ", price=" + price + "]";
    }

    public double calcSellingPrice(){
        double numMonthesInUse = 0;
        
        Period diff = Period.between(LocalDateTime.now().toLocalDate(), this.joinedAt.toLocalDate());
        numMonthesInUse = diff.getMonths();

        double sellingPrice = this.price * (1 - numMonthesInUse * 0.02);

        return sellingPrice;
    }

    public void sellAircraft(Airline newAirline){
        double sellingPrice = calcSellingPrice();
        this.setPrice(sellingPrice);
        this.airline.deleteAircraft(this);
        this.setAirline(newAirline); 
    }

}
