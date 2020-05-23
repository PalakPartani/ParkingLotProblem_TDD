/******************************************
 * purpose:Parking & unparking vehicles in a parking lot
 * @author name:Palak
 * @version:1.0
 * @Date:22-05-2020
 *******************************************/
package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int capacity;
    List vehicles;
    List<ParkingObservers> observersList;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList<>();
        observersList = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * @param parkingObservers types of observer
     * @return adding the observers in list
     * @purpose:register the different observers
     */

    public void register(ParkingObservers parkingObservers) {
        observersList.add(parkingObservers);
    }

    /**
     * @param vehicle of Object type to be parked
     * @return converted value
     * @purpose:parking vehicle
     */

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

    /**
     * @param vehicle of type Object
     * @return boolean value
     * @purpose:check if vehicle is parked
     */
    public boolean isVehicleParked(Object vehicle) {
        return this.vehicles.contains(vehicle);
    }

    /**
     * @param vehicle
     * @return boolean value
     * @purpose:unpark the given vehicle else return false
     */
    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            vehicles.remove(vehicle);
            return true;
        }
        return false;
    }
}