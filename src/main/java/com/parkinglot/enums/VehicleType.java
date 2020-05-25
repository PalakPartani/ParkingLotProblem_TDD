package com.parkinglot.enums;

import com.parkinglot.ParkingLotSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum VehicleType {

    /*LARGE {
        List<ParkingLotSystem> parkingLotList = new ArrayList<>();

        @Override
        public void getParkingLotsList() {
            List<ParkingLotSystem> parkingLotList = this.parkingLotList;
            Collections.sort(parkingLotList, Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.reverseOrder()));
            ParkingLotSystem lot = parkingLotList.get(0);

            *//*IntStream.range(0, list.size() - 3)
                    .filter(i -> (list.get(i + 2) - list.get(i) + 1) == 3).forEach(i -> modifiedEmptySlotsList.add(i + 1));
            return (modifiedEmptySlotsList.size() > 0) ? modifiedEmptySlotsList : SMALL.getParkingLotsList(list);*//*
        }
    },
    SMALL {
        List<ParkingLotSystem> parkingLotList = new ArrayList<>();

        @Override
        public void getParkingLotsList() {

            List<ParkingLotSystem> parkingLotList = this.parkingLotList;
            Collections.sort(parkingLotList, Comparator.comparing(list -> list.getEmptySlots().size(), Comparator.naturalOrder()));
            ParkingLotSystem lot = parkingLotList.get(0);
        }
    };

    public abstract void getParkingLotsList();*/
}

