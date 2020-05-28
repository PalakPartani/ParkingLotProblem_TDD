package com.parkinglot;

import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    List<ParkingLots> parkingLotList;
    ParkingLots parkingLot;

   /* public ArrayList<String> findAllParkedVehicles() {
        for (ParkingLots lot : parkingLotList)
            return lot.findAllParkedVehicles();
        throw new ParkingLotException("Vehicle not found !", ParkingLotException.ExceptionType.NOT_FOUND);
    }
*/
    public enum VehicleType {
        LARGE, SMALL;
    }

    public ParkingLotSystem() {
        parkingLotList = new ArrayList<>();
        parkingLot = new ParkingLots(3);
    }

    public void addMultipleLots(ParkingLots parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLots parkingLot) {
        return this.parkingLotList.contains(parkingLot);
    }

    public boolean park(Vehicle vehicle, DriverType driverType) {
        ParkingLots lot = getLotWithMoreEmptySlots();
        return lot.parkVehicle(vehicle, driverType);
    }

    public ParkingLots getLotWithMoreEmptySlots() {
        ParkingLots parkingLots;
        try {
            parkingLots = parkingLotList.stream()
                    .sorted(Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()))
                    .filter(list -> list.getEmptySlots().size() != 0)
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("ParkingLot is Full !", ParkingLotException.ExceptionType.SIZE_FULL));
        } catch (ParkingLotException e) {
            parkingLots = parkingLotList.get(0);
        }
        return parkingLots;

    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingLotList.stream()
                .filter(lots -> lots.isVehicleParked(vehicle))
                .map(slot -> true)
                .collect(Collectors.toList()).get(0);
    }

    public int getVehicleByLocation(Vehicle vehicle) {
        for (ParkingLots parkingLots : this.parkingLotList)
            if (parkingLots.isVehicleParked(vehicle))
                return parkingLots.getVehicleLocationOnSlot(vehicle);
        throw new ParkingLotException("Vehicle is not present !", ParkingLotException.ExceptionType.NOT_FOUND);
    }

    public ArrayList<Integer> getVehicleByColor(String color) {
        for (ParkingLots lot : parkingLotList)
            return lot.getCarByColorName(color);
        throw new ParkingLotException("Vehicle is not present !", ParkingLotException.ExceptionType.NOT_FOUND);
    }

    public boolean unPark(Vehicle vehicle) {
        for (ParkingLots parkingLots : this.parkingLotList) {
            return parkingLots.unParkVehicle(vehicle);
        }

        throw new ParkingLotException("Vehicle is not present !", ParkingLotException.ExceptionType.NOT_FOUND);
    }

    public void parkAccToVehicle(Vehicle vehicle, DriverType driverType, VehicleType vehicleTypeTypes) {
        if (vehicleTypeTypes.equals(VehicleType.LARGE)) {
            List<ParkingLots> parkingLotList = this.parkingLotList;
            Collections.sort(parkingLotList, Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()));
            ParkingLots lot = parkingLotList.get(0);
            lot.parkVehicle(vehicle, driverType);
        } else {
            ParkingLots lot = parkingLotList.get(0);
            lot.parkVehicle(vehicle, driverType);
        }
    }

    public ArrayList<String> getVehicleByMultipleValue(String color, String carName) {
        for (ParkingLots lot : parkingLotList)
            return lot.getMultipleFields(color, carName);
        throw new ParkingLotException("Vehicle not found !", ParkingLotException.ExceptionType.NOT_FOUND);
    }

    public ArrayList<Integer> findVehicleByCarName(String carName) {
        for (ParkingLots lot : parkingLotList)
            return lot.getVehicleByCarName(carName);
        throw new ParkingLotException("Vehicle not found !", ParkingLotException.ExceptionType.NOT_FOUND);
    }

    public ArrayList<Integer> getVehicleParkedInLotsInLast30Minutes() {
        for (ParkingLots parkingLot : this.parkingLotList)
            return parkingLot.getVehicleParkedInLast30Mins();
        throw new ParkingLotException("Vehicle not found !", ParkingLotException.ExceptionType.NOT_FOUND);
    }

    public ArrayList<String> findVehicleByVehicleTypeAndDriverType(VehicleType vehicleType, DriverType driverType) {
        int i = 0;
        for (ParkingLots lot : parkingLotList) {
            i++;
            System.out.println("hello " + i);
            return lot.getByFieldByVehicleTypeAndDriverType(vehicleType, driverType);
        }
        throw new ParkingLotException("Vehicle not found !", ParkingLotException.ExceptionType.NOT_FOUND);
    }
}
