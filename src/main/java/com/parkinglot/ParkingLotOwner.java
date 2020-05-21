package com.parkinglot;

public class ParkingLotOwner {
    private boolean isFullcapacity;

    public void capacityFull() {
        isFullcapacity = true;
    }

    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }
}
