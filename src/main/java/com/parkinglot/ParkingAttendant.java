package com.parkinglot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingAttendant {
    List<ParkingLot> parkingLotList;
    ParkingLot parkingLot;

    public ParkingAttendant() {
        parkingLotList = new ArrayList<>();
        parkingLot = new ParkingLot(3);
    }

    public void addMultipleLots(ParkingLot parkingLot) {
        parkingLotList.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLot parkingLot) {
        if (this.parkingLotList.contains(parkingLot))
            return true;
        return false;
    }

    public void parkEvenly(Object vehicle) {
        List<ParkingLot> parkingLotList = this.parkingLotList;
        Collections.sort(parkingLotList, Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()));
        ParkingLot lot = parkingLotList.get(0);
        lot.parkVehicle(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        return parkingLotList.stream()
                .filter(lots -> lots.isVehicleParked(vehicle))
                .map(slot -> true)
                .collect(Collectors.toList()).get(0);
    }
}
