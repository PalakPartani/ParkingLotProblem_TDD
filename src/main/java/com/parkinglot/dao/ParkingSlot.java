package com.parkinglot.dao;

import com.parkinglot.ParkingLotSystem;
import com.parkinglot.enums.DriverType;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ParkingSlot {
    public Vehicle vehicle;
    public int parkedTime;
    private ParkingLotSystem.VehicleType vehicleType;
    private DriverType driverType;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.parkedTime = LocalDateTime.now().getMinute();
    }

    public ParkingSlot(Vehicle vehicle, ParkingLotSystem.VehicleType vehicleType, DriverType driverType) {
        this.vehicle = vehicle;
        this.parkedTime = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
        this.driverType = DriverType.HANDICAP;
        this.vehicleType = vehicleType;
        this.driverType=driverType;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getParkedTime() {
        return parkedTime;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public ParkingLotSystem.VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return vehicle.equals(that.vehicle);
    }

}

