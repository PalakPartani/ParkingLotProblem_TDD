package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int capacity;
    List vehicles;
    List<ParkingObservers> observersList;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList();
        observersList = new ArrayList<>();
        this.capacity = capacity;
    }

    public void register(ParkingObservers parkingObservers) {
        observersList.add(parkingObservers);
    }

    public void parkVehicle(Object vehicle) {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked !", ParkingLotException.ExceptionType.PARKED);
        if (vehicles.size() == capacity) {
            for (ParkingObservers observers : observersList)
                observers.setCapacityFull();
            throw new ParkingLotException("Lot full !", ParkingLotException.ExceptionType.SIZE_FULL);
        }
        this.vehicles.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        return this.vehicles.contains(vehicle);
    }

    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            vehicles.remove(vehicle);
            return true;
        }
        return false;
    }
}