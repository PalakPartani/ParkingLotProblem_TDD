package com.parkinglot;

public class AirportSecurity implements ParkingObservers {
    private boolean isFullcapacity;
    private int minute;

    @Override
    public void setCapacityFull() {
        isFullcapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }

    @Override
    public void setParkingTime(int minute) {
        this.minute = minute;
    }

    @Override
    public int getParkingTime() {
        return minute;
    }
}
