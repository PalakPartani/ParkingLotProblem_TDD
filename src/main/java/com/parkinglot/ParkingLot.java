/******************************************
 * purpose:Parking & unparking vehicles in a parking lot
 * @author name:Palak
 * @version:1.0
 * @Date:22-05-2020
 *******************************************/
package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private int capacity;
    int numberOfSlots;
    List vehicles;
    List<ParkingObservers> observersList;


    public ParkingLot(int capacity) {
        observersList = new ArrayList<>();
        this.capacity = capacity;
        initializeParkingLot();
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
        if (vehicles.size() == capacity && !vehicles.contains(null)) {
            for (ParkingObservers observers : observersList)
                observers.setCapacityFull();
            throw new ParkingLotException("Lot full !", ParkingLotException.ExceptionType.SIZE_FULL);
        }
        park(numberOfSlots++, vehicle);
    }

    public int getTime() {
        observersList.get(0).setParkingTime(LocalDateTime.now().getMinute());
        return LocalDateTime.now().getMinute();
    }

    public void park(int slotNumber, Object vehicle) {
        this.vehicles.set(slotNumber, vehicle);
    }

    /**
     * @return null slots
     */
    public ArrayList getEmptySlots() {
        ArrayList emptySlots = new ArrayList();
        for (int i = 0; i < this.capacity; i++) {
            if (this.vehicles.get(i) == null)
                emptySlots.add(i);
        }
        System.out.println(emptySlots.size());
        return emptySlots;
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
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
            return true;
        }
        return false;
    }

    /**
     * @return the null list
     * @purpose:initialize the lot
     */
    public int initializeParkingLot() {
        this.vehicles = new ArrayList();
        IntStream.range(0, this.capacity).forEach(slots -> vehicles.add(null));
        return vehicles.size();
    }

    /**
     * @param vehicle
     * @return location of parked vehicle
     */
    public int findVehicleLocation(Object vehicle) {
        if (vehicles.contains(vehicle))
            return vehicles.indexOf(vehicle);
        throw new ParkingLotException("Sorry! vehicle not found", ParkingLotException.ExceptionType.NOT_FOUND);
    }
}