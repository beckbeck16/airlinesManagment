package com.example.airlinesManagment;

public class AirlinesBudget {
    
    private String name;
    private int initialBudget;
    
    public AirlinesBudget(String name, int initialBudget) {
        this.name = name;
        this.initialBudget = initialBudget;
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
    
    @Override
    public String toString() {
        return "airlinesBudget [initialBudget=" + initialBudget + ", name=" + name + "]";
    }
}
