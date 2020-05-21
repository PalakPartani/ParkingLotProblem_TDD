package com.parkinglot;

public class ParkingLot {
    Object vehicle;

    public boolean parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicle == vehicle) {
            this.vehicle = null;
            return true;
        }
        return false;
    }
}
