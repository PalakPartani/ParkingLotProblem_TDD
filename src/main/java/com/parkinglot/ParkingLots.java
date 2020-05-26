/******************************************
 * purpose:Parking & unparking vehicles in a parking lot
 * @author name:Palak
 * @version:1.0
 * @Date:22-05-2020
 *******************************************/
package com.parkinglot;

import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLots {
    private int capacity;
    private ParkingSlot parkingSlot;
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
        parkingSlot = new ParkingSlot(vehicle);

        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked !", ParkingLotException.ExceptionType.PARKED);

        if (vehiclesList.size() == capacity && !vehiclesList.contains(null)) {
            for (ParkingObservers observers : observersList)
                observers.setCapacityFull();
            throw new ParkingLotException("Lot full !", ParkingLotException.ExceptionType.SIZE_FULL);
        }

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
        observersList.get(0).setParkingTime(LocalDateTime.now().getMinute());
        return LocalDateTime.now().getMinute();
    }

    /**
     * @param vehicle of type Object
     * @return boolean value
     * @purpose:check if vehicle is parked
     */
    public boolean isVehicleParked(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehiclesList.contains(parkingSlot);
    }

    /**
     * @param vehicle
     * @return boolean value
     * @purpose:unpark the given vehicle else return false
     */
    public boolean unParkVehicle(Vehicle vehicle) {

        if (isVehicleParked(vehicle)) {
            this.vehiclesList.set(this.vehiclesList.indexOf(parkingSlot), null);
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

    public ArrayList<Integer> getCarByColorName(String color) {
        ArrayList<Integer> checklist = new ArrayList<>();
        IntStream.range(0, vehiclesList.size())
                .filter(slot -> vehiclesList.get(slot) != null)
                .filter(slot -> Objects.equals(vehiclesList.get(slot).vehicle.getColor(), color))
                .forEach(checklist::add);
        return checklist;
    }

    public ArrayList<String> getMultipleFields(String color, String modelName) {
        ArrayList<String> filteredVehicleDetailsList = new ArrayList<>();
        IntStream.range(0, vehiclesList.size())
                .filter(slot -> vehiclesList.get(slot) != null)
                .filter(slot -> Objects.equals(vehiclesList.get(slot).vehicle.getColor(), color))
                .filter(slot -> Objects.equals(vehiclesList.get(slot).vehicle.getModelName(), modelName))
                .mapToObj(slot -> (slot + " " + vehiclesList.get(slot).vehicle.getNumberPlate()))
                .forEach(filteredVehicleDetailsList::add);
        return filteredVehicleDetailsList;
    }
}