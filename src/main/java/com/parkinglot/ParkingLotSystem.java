package com.parkinglot;

import com.parkinglot.dao.Vehicle;
import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    List<ParkingLots> parkingLotList;
    ParkingLots parkingLot;

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
        parkingLots = parkingLotList.stream()
                .sorted(Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()))
                .filter(list -> list.getEmptySlots().size() != 0)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("ParkingLot is Full !", ParkingLotException.ExceptionType.SIZE_FULL));
        return parkingLots;

    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingLotList.stream()
                .filter(lots -> lots.isVehicleParked(vehicle))
                .map(slot -> true)
                .collect(Collectors.toList()).get(0);
    }

    public int getVehicleByLocation(Vehicle vehicle) {
        return parkingLotList.stream().
                filter(lots -> lots.getVehicleLocationOnSlot(vehicle) != -1)
                .map(lots -> lots.getVehicleLocationOnSlot(vehicle))
                .collect(Collectors.toList()).get(0);

    }

    public boolean unPark(Vehicle vehicle) {
        return parkingLotList.stream().
                filter(lots -> lots.unParkVehicle(vehicle))
                .map(slot -> true)
                .collect(Collectors.toList())
                .get(0);

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

    //predicate
    public List<String> filterByPredicate(IntPredicate intPredicate) {
        return parkingLotList.stream()
                .map(lots -> lots.filterByPredicate(intPredicate))
                .collect(Collectors.toList())
                .get(0);

    }
}
