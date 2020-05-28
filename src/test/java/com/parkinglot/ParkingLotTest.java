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
    ParkingLotSystem parkingAttendant;
    ParkingLots parkingLot1;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLots(3);
        vehicle = new Vehicle("White");
        parkingAttendant = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
    }

    //UC1
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        parkingAttendant.park(vehicle, DriverType.NORMAL);
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

    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {

        parkingAttendant.park(vehicle, DriverType.NORMAL);
        boolean isParked = parkingAttendant.unPark(vehicle);
        Assert.assertTrue(isParked);
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

    //uc5
    @Test
    public void givenWhenLotIsEmpty_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
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
        parkingAttendant.park(v, DriverType.NORMAL);

        parkingAttendant.unPark(v);
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
        parkingAttendant = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
        parkingAttendant.addMultipleLots(parkingLot1);
        boolean lotAdded = parkingAttendant.isLotAdded(parkingLot1);
        boolean lotAdded1 = parkingAttendant.isLotAdded(parkingLot);
        Assert.assertTrue(lotAdded && lotAdded1);
    }

    @Test
    public void givenVehicleToPark_WhenEvenlyParked_ShouldReturnTrue() {
        parkingAttendant = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
        parkingAttendant.addMultipleLots(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingAttendant.park(vehicle, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        parkingAttendant.park(vehicle4, DriverType.NORMAL);

        boolean vehicleParked = parkingAttendant.isVehicleParked(vehicle);
        boolean vehicleParked1 = parkingAttendant.isVehicleParked(vehicle2);
        boolean vehicleParked2 = parkingAttendant.isVehicleParked(vehicle3);
        boolean vehicleParked3 = parkingAttendant.isVehicleParked(vehicle4);
        Assert.assertTrue(vehicleParked && vehicleParked1 && vehicleParked2 && vehicleParked3);
    }

    @Test
    public void givenVehicleToPark_WhenEvenlyParked_ShouldReturnEqualEmptySlots() {
        parkingAttendant = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
        parkingAttendant.addMultipleLots(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingAttendant.park(vehicle, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        parkingAttendant.park(vehicle4, DriverType.NORMAL);

        Assert.assertEquals(parkingLot.getEmptySlots().size(), 1);
    }

    @Test
    public void givenVehicleToPark_WhenEvenlyParked_ShouldReturnEqualEmptySlotsOfAnotherList() {
        parkingAttendant = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
        parkingAttendant.addMultipleLots(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingAttendant.park(vehicle, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        parkingAttendant.park(vehicle4, DriverType.NORMAL);

        Assert.assertEquals(parkingLot1.getEmptySlots().size(), 1);
    }

    //uc10
    @Test
    public void givenVehicleToPark_WhenDriverIsHandicap_ShouldParkVehicleAtNearestSpot() {

        Vehicle vehicle2 = new Vehicle();
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.unPark(vehicle2);

        parkingAttendant.park(vehicle, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.HANDICAP);

        parkingAttendant.park(new Vehicle("Blue"), DriverType.HANDICAP);

        int vehicleParkedLocation = parkingAttendant.getVehicleByLocation(vehicle2);
        Assert.assertEquals(0, vehicleParkedLocation);

    }

    //uc11
    @Test
    public void givenVehicles_WhenVehicleShouldParkedAccordinglyType_ShouldReturnExpectedSlotNumbers() {
        int expectedSlots = 0;
        parkingAttendant = new ParkingLotSystem();
        parkingLot1 = new ParkingLots(3);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
        parkingAttendant.addMultipleLots(parkingLot1);
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        parkingAttendant.parkAccToVehicle(vehicle, DriverType.NORMAL, ParkingLotSystem.VehicleType.LARGE);
        parkingAttendant.parkAccToVehicle(vehicle2, DriverType.NORMAL, ParkingLotSystem.VehicleType.SMALL);
        parkingAttendant.parkAccToVehicle(vehicle3, DriverType.NORMAL, ParkingLotSystem.VehicleType.LARGE);
        parkingAttendant.parkAccToVehicle(vehicle4, DriverType.NORMAL, ParkingLotSystem.VehicleType.SMALL);

        int emptyParkingSlots = parkingLot.getEmptySlots().get(0);
        Assert.assertSame(expectedSlots, emptyParkingSlots);
    }

    //uc12
    @Test
    public void givenVehicleColour_WhenFindVehicleAccordinglyColour_ShouldReturnVehicleSlotNumber() {
        Vehicle vehicle2 = new Vehicle("White", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("White", "MH-12-V123", "toyota");
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 null null White");
        expectedVehicles.add("1 MH-12-V123 toyota White");
        expectedVehicles.add("2 MH-12 BMW White");
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        parkingAttendant.park(vehicle, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingAttendant.filterByPredicate(getPredicate("color", "White"));
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    //uc13
    @Test
    public void givenVehicleModelNumberAndColor_WhenFindVehicleAccordinglyModelNumberAndColor_ShouldReturnFilteredVehicleInformation
    () {
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-12-V123 toyota blue");
        Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-12-V123", "toyota");
        // Vehicle vehicle4 = new Vehicle("white", "xy123", "BMW");
        parkingAttendant.park(vehicle1, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);

        List<String> vehicleDetailsListBasedOnFilters = parkingAttendant.filterByPredicate(getPredicate("carName_color", "toyota", "blue"));
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    //uc14
    @Test
    public void givenVehicle_WhenFindVehicleColor_ShouldReturnVehicleSlotNumber() {

        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-12 BMW blue");
        Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-12-V123", "toyota");
        parkingAttendant.park(vehicle1, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.HANDICAP);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingAttendant.filterByPredicate(getPredicate("carName", "BMW"));
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    //uc15
    @Test
    public void givenVehicles_WhenFindVehiclesParkedIn30Minutes_ShouldReturnVehicleSlotNumber() {
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-12 BMW blue");
        expectedVehicles.add("1 MH-12-V123 toyota blue");
        expectedVehicles.add("2 MH-19 toyota white");
        Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-12-V123", "toyota");
        parkingAttendant.park(vehicle1, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.HANDICAP);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingAttendant.filterByPredicate(getPredicate("time", "30"));
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    //uc16
    @Test
    public void givenVehicle_WhenFindVehiclesSmallVehicleAndHandicapDriverType_ShouldReturnVehicleDetails() {
        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-19 toyota white");
        expectedVehicles.add("1 MH-12 BMW blue");
        Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");

        parkingAttendant.park(vehicle1, DriverType.HANDICAP);
        parkingAttendant.park(vehicle2, DriverType.HANDICAP);
        List<String> vehicleDetailsListBasedOnFilters = parkingAttendant.filterByPredicate(getPredicate("vehicle&driverType", "SMALL", "HANDICAP"));
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    //uc17
    @Test
    public void givenParkingLots_WhenFindParkedVehicles_ShouldReturnVehiclesDetails() {

        List<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add("0 MH-19 toyota white");
        expectedVehicles.add("1 MH-12 BMW blue");
        expectedVehicles.add("2 MH-18 toyota blk");
        Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blk", "MH-18", "toyota");
        parkingAttendant.park(vehicle1, DriverType.HANDICAP);
        parkingAttendant.park(vehicle2, DriverType.HANDICAP);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        List<String> vehicleDetailsListBasedOnFilters = parkingAttendant.filterByPredicate((getPredicate("default")));
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }
}
