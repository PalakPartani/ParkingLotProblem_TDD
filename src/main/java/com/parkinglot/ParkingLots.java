/******************************************
 * purpose:Parking & unparking vehicles in a parking lot
 * @author name:Palak
 * @version:1.0
 * @Date:22-05-2020
 *******************************************/
package com.parkinglot;

import com.parkinglot.dao.ParkingSlot;
import com.parkinglot.dao.Vehicle;
import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.observer.ParkingObservers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parkinglot.predicate.PredicateFilter.initializePredicate;

public class ParkingLots {
    public int capacity;
    List<ParkingObservers> observersList;
    public List<ParkingSlot> vehiclesList;

    public ParkingLots(int capacity) {
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
     * @return the null list
     * @purpose:initialize the lot
     */
    public int initializeParkingLot() {
        this.vehiclesList = new ArrayList<>();
        IntStream.range(0, this.capacity)
                .forEach(slots -> vehiclesList.add(null));
        return vehiclesList.size();
    }

    /**
     * @param
     * @param vehicle of Object type to be parked
     * @return converted value
     * @purpose:parking vehicle
     */

    public boolean parkVehicle(Vehicle vehicle, DriverType driverType) {

        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked !", ParkingLotException.ExceptionType.PARKED);

        if (vehiclesList.size() == capacity && !vehiclesList.contains(null)) {
            for (ParkingObservers observers : observersList)
                observers.setCapacityFull();
            throw new ParkingLotException("Lot full !", ParkingLotException.ExceptionType.SIZE_FULL);
        }

        ParkingSlot parkingSlot = new ParkingSlot(vehicle, ParkingLotSystem.VehicleType.SMALL, driverType);

        int emptyParkingSlot = getEmptyParkingSlotBasedOnDriverType(driverType);
        this.vehiclesList.set(emptyParkingSlot, parkingSlot);
        return true;
    }

    public Integer getEmptyParkingSlotBasedOnDriverType(DriverType driverType) {
        return getEmptySlots().stream()
                .sorted(driverType.order)
                .collect(Collectors.toList())
                .get(0);
    }

    /**
     * @return null slots
     */
    public ArrayList<Integer> getEmptySlots() {
        ArrayList emptySlots = new ArrayList();
        IntStream.range(0, this.capacity)
                .filter(slot -> vehiclesList.get(slot) == null)
                .forEach(emptySlots::add);
        return emptySlots;
    }

    public int getTime() {
        observersList.get(0)
                .setParkingTime(LocalDateTime.now().getMinute());
        return LocalDateTime.now().getMinute();
    }

    /**
     * @param vehicle of type Object
     * @return boolean value
     * @purpose:check if vehicle is parked
     */
    public boolean isVehicleParked(Vehicle vehicle) {
        return this.vehiclesList.contains(new ParkingSlot(vehicle));
    }

    /**
     * @param vehicle
     * @return boolean value
     * @purpose:unpark the given vehicle else return false
     */
    public boolean unParkVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) {
            this.vehiclesList.set(this.vehiclesList.indexOf(new ParkingSlot(vehicle)), null);
            return true;
        }
        return false;
    }

    /**
     * @param vehicle
     * @return location of parked vehicle
     */
    public int getVehicleLocationOnSlot(Vehicle vehicle) {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        if (isVehicleParked(vehicle))
            return this.vehiclesList.indexOf(parkingSlot);
        throw new ParkingLotException("Sorry! vehicle not found", ParkingLotException.ExceptionType.NOT_FOUND);
    }

//Predicates

    /**
     * @param intPredicate filter
     * @return list
     */
    public List<String> filterByPredicate(IntPredicate intPredicate) {
        ArrayList<String> filteredVehicleDetailsList = new ArrayList<>();
        initializePredicate(vehiclesList);
        IntStream.range(0, vehiclesList.size())
                .filter(slot -> vehiclesList.get(slot) != null)
                .filter(intPredicate)
                .mapToObj(slot -> (slot + " " + vehiclesList.get(slot).getVehicle().getNumberPlate() + " " + vehiclesList.get(slot).getVehicle().getCarName() + " " + vehiclesList.get(slot).getVehicle().getColor()))
                .forEach(filteredVehicleDetailsList::add);
        return filteredVehicleDetailsList;
    }
}