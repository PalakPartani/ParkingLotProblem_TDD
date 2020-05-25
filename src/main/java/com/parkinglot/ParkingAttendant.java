package com.parkinglot;

import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.VehicleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//parkinglotsystem
public class ParkingAttendant {
    List<ParkingLotSystem> parkingLotList;
    ParkingLotSystem parkingLot;

    public enum Vehicle {
        LARGE, SMALL;
    }

    public ParkingAttendant() {
        parkingLotList = new ArrayList<>();
        parkingLot = new ParkingLotSystem(3);
    }

    public void addMultipleLots(ParkingLotSystem parkingLot) {
        parkingLotList.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLotSystem parkingLot) {
        if (this.parkingLotList.contains(parkingLot))
            return true;
        return false;
    }

    public void parkEvenly(Object vehicle, DriverType driverType, Vehicle... vehicleTypes) {
        if(vehicleTypes.equals(Vehicle.LARGE) && driverType.equals(DriverType.HANDICAP)) {
            List<ParkingLotSystem> parkingLotList = this.parkingLotList;
            Collections.sort(parkingLotList, Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()));
            ParkingLotSystem lot = parkingLotList.get(0);
            lot.parkVehicle(vehicle, DriverType.HANDICAP);
        } else {
            List<ParkingLotSystem> parkingLotList = this.parkingLotList;
            Collections.sort(parkingLotList, Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()));
            ParkingLotSystem lot = parkingLotList.get(0);
            lot.parkVehicle(vehicle, DriverType.NORMAL);
        }

    }

    public boolean isVehicleParked(Object vehicle) {
        return parkingLotList.stream()
                .filter(lots -> lots.isVehicleParked(vehicle))
                .map(slot -> true)
                .collect(Collectors.toList()).get(0);
    }
}
