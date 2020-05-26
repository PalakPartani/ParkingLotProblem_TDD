package com.parkinglot;

import java.util.Objects;

public class Vehicle {
    private String color;
    private String numberPlate;
    private String modelName;

    public Vehicle() {
    }

    public Vehicle(String color) {
        this.color = color;
    }

    public Vehicle(String color, String numberPlate, String modelName) {
        this.color = color;
        this.numberPlate = numberPlate;
        this.modelName = modelName;
    }

    public String getColor() {
        return color;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getModelName() {
        return modelName;
    }
}
