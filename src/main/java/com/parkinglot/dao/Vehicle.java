package com.parkinglot.dao;

public class Vehicle {
    private String color;
    private String numberPlate;
    private String carName;

    public Vehicle() {
    }

    public Vehicle(String color) {
        this.color = color;
    }

    public Vehicle(String color, String numberPlate, String carName) {
        this.color = color;
        this.numberPlate = numberPlate;
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getCarName() {
        return carName;
    }
}
