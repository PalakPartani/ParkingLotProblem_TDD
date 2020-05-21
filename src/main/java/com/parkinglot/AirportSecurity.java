package com.parkinglot;

public class AirportSecurity {
    private boolean isFullcapacity;

    public void capacityFull() {
        isFullcapacity = true;
    }

    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }
}
