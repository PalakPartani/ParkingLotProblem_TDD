package com.parkinglot;

import com.parkinglot.dao.Vehicle;
import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.observer.AirportSecurity;
import com.parkinglot.observer.ParkingLotOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglot.predicate.PredicateFilter.getPredicate;
import static org.junit.Assert.assertEquals;

public class ParkingLotTest {

    ParkingLots parkingLot;
    Vehicle vehicle;
    ParkingLotSystem parkingLotSystem;
    ParkingLots parkingLot1;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLots(3);
        vehicle = new Vehicle("White");
        parkingLotSystem = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLot(parkingLot);
    }

    //UC1
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle already parked !", e.getMessage());
        }
    }

    @Test
    public void givenVehicles_WhenParkingLotFull_ShouldThrowParkingLotFullException() {
        try {
            parkingLotSystem.park(vehicle, DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(), DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(), DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.SIZE_FULL, e.type);
        }
    }


    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {

        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLotSystem.unPark(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnFalse() {
        boolean isParked = parkingLot.unParkVehicle(new Vehicle());
        Assert.assertFalse(isParked);
    }

    //UC3
    @Test
    public void givenWhenLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);

        } catch (ParkingLotException e) {
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }


    @Test
    public void givenVehicleParked_WhenLotFull_ShouldThrowException() {
        try {
            Vehicle vehicle2 = new Vehicle();
            Vehicle vehicle3 = new Vehicle();
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Lot full !", e.getMessage());
        }
    }

    //uc4
    @Test
    public void givenWhenLotIsFull_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.register(airportSecurity);
        try {
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenWhenLotIsFull_ShouldInformSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.register(airportSecurity);
        try {
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);

        } catch (ParkingLotException e) {
            Assert.assertEquals("Lot full !", e.getMessage());
        }
    }

    //uc5
    @Test
    public void givenWhenLotIsEmpty_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        parkingLot.unParkVehicle(vehicle);
        boolean capacityFull = parkingLotOwner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenWhenLotIsEmpty_ShouldInformSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.register(airportSecurity);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        parkingLot.unParkVehicle(vehicle);
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    //uc6

    @Test
    public void givenParkingLotCapacity_WhenInitialize_ShouldReturnParkingCapacity() {
        int parkingLotCapacity = parkingLot.initializeParkingLot();
        Assert.assertEquals(3, parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_WhenAvailable_ShouldReturnTrue() {
        List expectedSlotList = new ArrayList();
        expectedSlotList.add(0);
        expectedSlotList.add(1);
        expectedSlotList.add(2);
        ArrayList emptySlots = parkingLot.getEmptySlots();
        Assert.assertEquals(expectedSlotList, emptySlots);
    }

    @Test
    public void givenVehiclesParkedAndUnParked_ShouldReturnAvailableEmptySlots() {

        List expectedSlotList = new ArrayList();
        Vehicle v = new Vehicle();
        expectedSlotList.add(0);
        expectedSlotList.add(1);
        expectedSlotList.add(2);
        parkingLotSystem.park(v, DriverType.NORMAL);

        parkingLotSystem.unPark(v);
        ArrayList<Integer> emptySlotList = parkingLot.getEmptySlots();
        Assert.assertEquals(expectedSlotList, emptySlotList);
    }

    @Test
    public void givenVehicleParkedOnEmptySlot_ShouldReturnTrue() {
        ArrayList<Integer> emptyList = parkingLot.getEmptySlots();
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    //uc7
    @Test
    public void givenVehicle_WhenParkedShouldFindTheLocation() {
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        int vehicleLocation = parkingLot.getVehicleLocationOnSlot(vehicle);
        Assert.assertEquals(vehicleLocation, 2);
    }


    @Test
    public void givenVehicle_WhenNotFound_ShouldThrowException() {
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
        try {
            parkingLot.parkVehicle(new Vehicle(), DriverType.NORMAL);
            parkingLot.getVehicleLocationOnSlot(new Vehicle());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Sorry! vehicle not found", e.getMessage());
        }
    }

    //uc8
    @Test
    public void givenVehicleParked_WhenTimeIsSet_ShouldReturnParkingTime() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.register(owner);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        int parkMinute = parkingLot.getTime();
        Assert.assertEquals(owner.getParkingTime(), parkMinute);
    }

    @Test
    public void givenVehicleParked_WhenTimeIsSet_ShouldReturnParkingTimeToSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.register(airportSecurity);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        int parkMinute = parkingLot.getTime();
        Assert.assertEquals(airportSecurity.getParkingTime(), parkMinute);
    }

    //uc9
    @Test
    public void givenMultipleLots_WhenAdded_ShouldReturnTrue() {
        parkingLotSystem = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLot(parkingLot);
        parkingLotSystem.addLot(parkingLot1);
        boolean lotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        boolean lotAdded1 = parkingLotSystem.isLotAdded(parkingLot);
        Assert.assertTrue(lotAdded && lotAdded1);
    }

    @Test
    public void givenVehicleToPark_WhenEvenlyParked_ShouldReturnTrue() {
        parkingLotSystem = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLot(parkingLot);
        parkingLotSystem.addLot(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.NORMAL);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        parkingLotSystem.park(vehicle4, DriverType.NORMAL);

        boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean vehicleParked1 = parkingLotSystem.isVehicleParked(vehicle2);
        boolean vehicleParked2 = parkingLotSystem.isVehicleParked(vehicle3);
        boolean vehicleParked3 = parkingLotSystem.isVehicleParked(vehicle4);
        Assert.assertTrue(vehicleParked && vehicleParked1 && vehicleParked2 && vehicleParked3);
    }

    @Test
    public void givenVehicleToPark_WhenEvenlyParked_ShouldReturnEqualEmptySlots() {
        parkingLotSystem = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLot(parkingLot);
        parkingLotSystem.addLot(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.NORMAL);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        parkingLotSystem.park(vehicle4, DriverType.NORMAL);

        Assert.assertEquals(parkingLot.getEmptySlots().size(), 1);
    }

    @Test
    public void givenVehicle_WhenEvenlyParked_ShouldReturnEmptySlotsOfAnotherList() {
        parkingLotSystem = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLot(parkingLot);
        parkingLotSystem.addLot(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.NORMAL);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        parkingLotSystem.park(vehicle4, DriverType.NORMAL);

        Assert.assertEquals(parkingLot1.getEmptySlots().size(), 1);
    }

    //uc10
    @Test
    public void givenVehicle_WhenDriverIsHandicap_ShouldParkVehicleAtNearestSpot() {

        Vehicle vehicle2 = new Vehicle();
        parkingLotSystem.park(vehicle2, DriverType.NORMAL);
        parkingLotSystem.unPark(vehicle2);

        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
        parkingLotSystem.park(new Vehicle("Blue"), DriverType.HANDICAP);

        int vehicleParkedLocation = parkingLotSystem.getVehicleByLocation(vehicle2);
        Assert.assertEquals(0, vehicleParkedLocation);

    }

    //uc11
    @Test
    public void givenVehiclesParked_WhenVehicleLarge_ShouldReturnExpectedSlotNumbers() {
        int expectedSlots = 0;
        parkingLotSystem = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLot(parkingLot);
        parkingLotSystem.addLot(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingLotSystem.parkAccToVehicle(vehicle, DriverType.NORMAL, ParkingLotSystem.VehicleType.LARGE);
        parkingLotSystem.parkAccToVehicle(vehicle2, DriverType.NORMAL, ParkingLotSystem.VehicleType.SMALL);
        parkingLotSystem.parkAccToVehicle(vehicle3, DriverType.NORMAL, ParkingLotSystem.VehicleType.LARGE);
        parkingLotSystem.parkAccToVehicle(vehicle4, DriverType.NORMAL, ParkingLotSystem.VehicleType.SMALL);

        int emptyParkingSlots = parkingLot.getEmptySlots().get(0);
        Assert.assertEquals(expectedSlots, emptyParkingSlots);
    }

    //uc12
    @Test
    public void givenVehicleColor_WhenFindVehicleAccordinglyColour_ShouldReturnVehicleDetails() {
        Vehicle vehicle2 = new Vehicle("White", "MH-18", "BMW");
        Vehicle vehicle3 = new Vehicle("White", "MH-16", "toyota");
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("1 MH-16 toyota White");
        expectedVehicles.add("2 MH-18 BMW White");
        parkingLotSystem.park(vehicle2, DriverType.NORMAL);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingLotSystem.filterByPredicate(getPredicate("color", "White"));
        Assert.assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenVehicles_WhenFindVehiclesAccordingToColorNotFound_ShouldThrowException() {
        try {

            Vehicle vehicle2 = new Vehicle("blk", "MH-12", "toyota");
            parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
            parkingLotSystem.filterByPredicate(getPredicate("color", "blue"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }

    //uc13
    @Test
    public void givenVehicleCarNameAndColor_WhenFindVehicle_ShouldReturnFilteredVehicleInformation() {
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-16 toyota blue");
        Vehicle vehicle1 = new Vehicle("white", "MH-15", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-16", "toyota");
        parkingLotSystem.park(vehicle1, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.NORMAL);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);

        List<String> vehicleDetailsListBasedOnFilters = parkingLotSystem.filterByPredicate(getPredicate("carName_color", "toyota", "blue"));
        Assert.assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenVehicles_WhenFindVehiclesAccordingToCarNameColorNotFound_ShouldThrowException() {
        try {

            Vehicle vehicle2 = new Vehicle("blk", "MH-12", "toyota");
            parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
            parkingLotSystem.filterByPredicate(getPredicate("carName_color", "toyota", "blue"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }

    //uc14
    @Test
    public void givenVehicle_WhenFindVehicleColor_ShouldReturnVehicleDetails() {

        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-12 BMW blue");
        Vehicle vehicle1 = new Vehicle("white", "MH-16", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-15", "toyota");
        parkingLotSystem.park(vehicle1, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingLotSystem.filterByPredicate(getPredicate("carName", "BMW"));
        Assert.assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenVehicles_WhenFindVehiclesAccordingToCarNameNotFound_ShouldThrowException() {
        try {

            Vehicle vehicle2 = new Vehicle("blk", "MH-12", "toyota");
            parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
            parkingLotSystem.filterByPredicate(getPredicate("carName", "BMW"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }


    //uc15
    @Test
    public void givenVehicles_WhenFindVehiclesParkedIn30Minutes_ShouldReturnVehicleDetails() {
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-12 BMW blue");
        expectedVehicles.add("1 MH-18 toyota blue");
        expectedVehicles.add("2 MH-16 toyota white");
        Vehicle vehicle1 = new Vehicle("white", "MH-16", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-18", "toyota");
        parkingLotSystem.park(vehicle1, DriverType.NORMAL);
        parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingLotSystem.filterByPredicate(getPredicate("time", "30"));
        Assert.assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenParkingLots_WhenFindVehiclesAccordingToTimeNotFound_ShouldThrowException() {
        try {
            parkingLotSystem.filterByPredicate(getPredicate("time", "30"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }

    //uc16
    @Test
    public void givenVehicleParked_WhenDriverIsHandicapedAndSmallCar_ShouldReturnVehicleDetails() {
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-18 toyota white");
        expectedVehicles.add("1 MH-12 BMW blue");
        Vehicle vehicle1 = new Vehicle("white", "MH-18", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");

        parkingLotSystem.park(vehicle1, DriverType.HANDICAP);
        parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
        List<String> vehicleDetailsListBasedOnFilters = parkingLotSystem.filterByPredicate(getPredicate("vehicle&driverType", "SMALL", "HANDICAP"));
        Assert.assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenParkingLots_WhenFindVehiclesAccordingToVehicleTypeAndDriverTypeNotFound_ShouldThrowException() {
        try {

            Vehicle vehicle2 = new Vehicle("blk", "MH-12", "toyota");
            parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
            parkingLotSystem.filterByPredicate(getPredicate("vehicle&driverType", "SMALL", "HANDICAP"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }


    //uc17
    @Test
    public void givenVehiclesParked_WhenFindParkedVehicles_ShouldReturnAllVehiclesDetails() {

        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-16 toyota white");
        expectedVehicles.add("1 MH-12 BMW blue");
        expectedVehicles.add("2 MH-18 toyota blk");
        Vehicle vehicle1 = new Vehicle("white", "MH-16", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blk", "MH-18", "toyota");
        parkingLotSystem.park(vehicle1, DriverType.HANDICAP);
        parkingLotSystem.park(vehicle2, DriverType.HANDICAP);
        parkingLotSystem.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingLotSystem.filterByPredicate((getPredicate("default")));
        Assert.assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenVehicle_WhenFindVehiclesNotFound_ShouldThrowException() {
        try {
            parkingLotSystem.filterByPredicate(getPredicate("default"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }
}
