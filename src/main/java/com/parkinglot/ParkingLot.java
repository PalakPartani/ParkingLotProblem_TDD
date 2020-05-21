package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int capacity;
    List vehicles;
    AirportSecurity airportSecurity;
    ParkingLotOwner parkingLotOwner;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList();
        this.capacity = capacity;
    }

    public boolean parkVehicle(Object vehicle) {
        if (vehicles.size() == capacity)
            throw new ParkingLotException("Lot full !", ParkingLotException.ExceptionType.SIZE_FULL);
        if (vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle already parked !", ParkingLotException.ExceptionType.PARKED);
        vehicles.add(vehicle);
        return true;
    }

    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            vehicles.remove(vehicle);
            return true;
        }
        return false;
    }

    public void registerSecurity(AirportSecurity airportSecurity) {
        this.airportSecurity = airportSecurity;
    }

    public void registerOwner(ParkingLotOwner parkingLotOwner) {
        this.parkingLotOwner = parkingLotOwner;
    }
}