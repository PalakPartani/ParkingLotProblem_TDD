package com.parkinglot;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ParkingSlot {
    public Vehicle vehicle;
    public int parkedTime;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.parkedTime= LocalDateTime.now().getMinute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return vehicle.equals(that.vehicle);
    }
}

