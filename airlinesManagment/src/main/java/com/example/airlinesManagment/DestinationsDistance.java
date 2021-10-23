package com.example.airlinesManagment;

public class DestinationsDistance {
    private String destinationName;
    private double distance;
    
    public DestinationsDistance(String destinationName, Double distance) {
        this.destinationName = destinationName;
        this.distance = distance;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "DestinationsDistance [destinationName=" + destinationName + ", distance=" + distance + "]";
    }
}   
