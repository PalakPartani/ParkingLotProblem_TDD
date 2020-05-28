package com.parkinglot.predicate;

import com.parkinglot.ParkingLotSystem;
import com.parkinglot.dao.ParkingSlot;
import com.parkinglot.enums.DriverType;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;

public class PredicateFilter {
    public static List<ParkingSlot> vehiclesList;

    public static void initializePredicate(List<ParkingSlot> vehicles) {
        vehiclesList = vehicles;
    }

    public static IntPredicate getPredicate(String type, String... param) {
        switch (type) {
            case "color":
                return slot -> Objects.equals(vehiclesList.get(slot).getVehicle().getColor(), param[0]);
            case "carName_color":
                return slot -> Objects.equals(vehiclesList.get(slot).getVehicle().getCarName(), param[0])
                        && Objects.equals(vehiclesList.get(slot).getVehicle().getColor(), param[1]);
            case "carName":
                return slot -> Objects.equals(vehiclesList.get(slot).getVehicle().getCarName(), param[0]);
            case "time":
                long currentTimeInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
                return slot -> currentTimeInMinutes - vehiclesList.get(slot).getParkedTime() <= Integer.parseInt(param[0]);
            case "vehicle&driverType":
                return slot -> Objects.equals(vehiclesList.get(slot).getVehicleType(), ParkingLotSystem.VehicleType.valueOf(param[0])) &&
                        Objects.equals(vehiclesList.get(slot).getDriverType(), DriverType.valueOf(param[1]));
            default:
                return slot -> vehiclesList.get(slot) != null;
        }
    }
}
