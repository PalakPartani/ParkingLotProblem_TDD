package com.parkinglot;

import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLot);
        parkingAttendant.addMultipleLots(parkingLot1);
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

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnFalse() {
        try {
            Vehicle vehicle2 = new Vehicle("Black");
            parkingAttendant.park(vehicle2, DriverType.NORMAL);
            boolean isUnParked = parkingAttendant.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle is not present !", e.getMessage());
        }
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
        expectedSlotList.add(0);
        expectedSlotList.add(1);
        expectedSlotList.add(2);
        parkingAttendant.park(vehicle, DriverType.NORMAL);
        parkingAttendant.park(new Vehicle(), DriverType.NORMAL);
        parkingAttendant.unPark(vehicle);
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

    //uc9
    @Test
    public void givenMultipleLots_WhenAdded_ShouldReturnTrue() {

        boolean lotAdded = parkingAttendant.isLotAdded(parkingLot1);
        boolean lotAdded1 = parkingAttendant.isLotAdded(parkingLot);
        Assert.assertTrue(lotAdded && lotAdded1);
    }

    @Test
    public void givenVehicleToPark_WhenEvenlyParked_ShouldReturnTrue() {
        ParkingLotSystem parkingAttendant = new ParkingLotSystem();
        ParkingLots parkingLot1 = new ParkingLots(3);

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
        parkingAttendant.park(vehicle, DriverType.NORMAL);

        parkingAttendant.park(new Vehicle("Blue"), DriverType.HANDICAP);
        parkingAttendant.unPark(vehicle);

        parkingAttendant.park(vehicle, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.HANDICAP);
        int vehicleParkedLocation = parkingAttendant.getVehicleByLocation(vehicle2);
        Assert.assertEquals(0, vehicleParkedLocation);

    }

    //uc11
    @Test
    public void givenVehicles_WhenVehicleShouldParkedAccordinglyType_ShouldReturnExpectedSlotNumbers() {
        int expectedSlots = 0;

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

    @Test
    public void givenVehicles_WhenVehicleNotParkedAccordinglyType_ShouldReturnException() {

        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        try {
            parkingAttendant.parkAccToVehicle(vehicle, DriverType.NORMAL, ParkingLotSystem.VehicleType.LARGE);
            parkingAttendant.parkAccToVehicle(vehicle2, DriverType.NORMAL, ParkingLotSystem.VehicleType.SMALL);
            parkingAttendant.parkAccToVehicle(vehicle3, DriverType.NORMAL, ParkingLotSystem.VehicleType.LARGE);
            parkingAttendant.parkAccToVehicle(vehicle4, DriverType.NORMAL, ParkingLotSystem.VehicleType.SMALL);

            parkingLot.getEmptySlots().size();
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle not found !", e.getMessage());

        }
    }

    //uc12
    @Test
    public void givenVehicleColor_WhenFindAccordinglyColor_ShouldReturnCorrectVehicleSlotNumber() {

        Vehicle vehicle2 = new Vehicle("White");
        Vehicle vehicle3 = new Vehicle("White");

        ArrayList<Integer> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(1);
        expectedVehicles.add(2);

        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        parkingAttendant.park(vehicle, DriverType.NORMAL);

        ArrayList<Integer> filteredVehicleDetailsList = parkingAttendant.getVehicleByColor("White");
        Assert.assertEquals(expectedVehicles, filteredVehicleDetailsList);
    }

    @Test
    public void givenAnotherVehicleColor_WhenFindAccordinglyColor_ShouldReturnException() {

        Vehicle vehicle2 = new Vehicle("White");
        Vehicle vehicle3 = new Vehicle("White");

        ArrayList<Integer> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(1);
        expectedVehicles.add(2);
        try {
            parkingAttendant.park(vehicle2, DriverType.NORMAL);
            parkingAttendant.park(vehicle3, DriverType.NORMAL);
            parkingAttendant.park(vehicle, DriverType.NORMAL);

            parkingAttendant.getVehicleByColor("Green");
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle not found !", e.getMessage());
        }
    }


    //uc13
    @Test
    public void givenVehicleDetails_WhenFindingVehicleAccordinglyModelNumberAndColor_ShouldReturnCorrectVehicle() {

        ArrayList<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(1 + " " + "MH-1509");
        Vehicle vehicle1 = new Vehicle("black", "MH-1618", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-1218", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-1509", "toyota");
        Vehicle vehicle4 = new Vehicle("white", "MH-0510", "BMW");

        parkingAttendant.park(vehicle1, DriverType.NORMAL);
        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        parkingAttendant.park(vehicle4, DriverType.NORMAL);
        parkingAttendant.park(vehicle, DriverType.NORMAL);

        ArrayList<String> filteredVehicleDetailsList = parkingAttendant.getVehicleByMultipleValue("blue", "toyota");
        assertEquals(expectedVehicles, filteredVehicleDetailsList);
    }

    @Test
    public void givenAnotherVehicleDetails_WhenFindingVehicleAccordinglyModelNumberAndColor_ShouldReturnException() {

        ArrayList<String> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(1 + " " + "MH-1509");
        try {
            Vehicle vehicle1 = new Vehicle("black", "MH-1618", "toyota");
            Vehicle vehicle2 = new Vehicle("blue", "MH-1218", "BMW");
            Vehicle vehicle3 = new Vehicle("blue", "MH-1509", "toyota");
            Vehicle vehicle4 = new Vehicle("white", "MH-0510", "BMW");

            parkingAttendant.park(vehicle1, DriverType.NORMAL);
            parkingAttendant.park(vehicle2, DriverType.NORMAL);
            parkingAttendant.park(vehicle3, DriverType.NORMAL);
            parkingAttendant.park(vehicle4, DriverType.NORMAL);
            parkingAttendant.park(vehicle, DriverType.NORMAL);

            parkingAttendant.getVehicleByMultipleValue("blue", "toyota");
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle not found !", e.getMessage());
        }
    }

    //uc14
    @Test
    public void givenCarName_WhenFindVehicleOfCarName_ShouldReturnVehicleSlotNumber() {
        ArrayList<Integer> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(2);

        Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
        Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
        Vehicle vehicle3 = new Vehicle("blue", "MH-12-V123", "toyota");

        parkingAttendant.park(vehicle2, DriverType.NORMAL);
        parkingAttendant.park(vehicle1, DriverType.NORMAL);
        parkingAttendant.park(vehicle3, DriverType.NORMAL);
        ArrayList<Integer> vehicleDetailsListBasedOnFilters = parkingAttendant.findVehicleByCarName("BMW");
        assertEquals(expectedVehicles, vehicleDetailsListBasedOnFilters);
    }

    @Test
    public void givenAnotherCarName_WhenFindVehicleOfCarName_ShouldReturnException() {
        ArrayList<Integer> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(2);
        try {
            Vehicle vehicle1 = new Vehicle("white", "MH-19", "toyota");
            Vehicle vehicle2 = new Vehicle("blue", "MH-12", "BMW");
            Vehicle vehicle3 = new Vehicle("blue", "MH-12-V123", "toyota");

            parkingAttendant.park(vehicle2, DriverType.NORMAL);
            parkingAttendant.park(vehicle1, DriverType.NORMAL);
            parkingAttendant.park(vehicle3, DriverType.NORMAL);
            parkingAttendant.findVehicleByCarName("Benz");
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle not found !", e.getMessage());
        }
    }
}
