package com.parkinglot;

public class AirportSecurity implements ParkingObservers {
    private boolean isFullcapacity;

    @Override
    public void setCapacityFull() {
        isFullcapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }
}
