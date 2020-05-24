package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(3);
        vehicle = new Object();
    }

    //UC1
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        parkingLot.parkVehicle(vehicle);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle already parked !", e.getMessage());
        }
    }

    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        parkingLot.parkVehicle(vehicle);
        boolean isUnParked = parkingLot.unParkVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnFalse() {
        Object vehicle2 = new Object();
        parkingLot.parkVehicle(vehicle2);
        boolean isUnParked = parkingLot.unParkVehicle(vehicle);
        Assert.assertFalse(isUnParked);
    }

    //UC3
    @Test
    public void givenWhenLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new Object());
            parkingLot.parkVehicle(new Object());

        } catch (ParkingLotException e) {
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenVehicleParked_WhenLotFull_ShouldThrowException() {
        try {
            Object vehicle2 = new Object();
            Object vehicle3 = new Object();
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle2);
            parkingLot.parkVehicle(vehicle3);
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
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new Object());
            parkingLot.parkVehicle(new Object());
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
        parkingLot.parkVehicle(vehicle);
        parkingLot.parkVehicle(new Object());
        parkingLot.unParkVehicle(vehicle);
        boolean capacityFull = parkingLotOwner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    //uc6

    @Test
    public void givenParkingLotCapacity_WhenInitialize_ShouldReturnParkingCapacity() {
        // parkingLot.setCapacity(2);
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
        expectedSlotList.add(2);
        parkingLot.park(0, vehicle);
        parkingLot.park(1, new Object());
        parkingLot.unParkVehicle(vehicle);
        ArrayList emptySlotList = parkingLot.getEmptySlots();
        Assert.assertEquals(expectedSlotList, emptySlotList);
    }

    @Test
    public void givenVehicleParkedOnEmptySlot_ShouldReturnTrue() {
        ArrayList<Integer> emptyList = parkingLot.getEmptySlots();
        parkingLot.park(emptyList.get(0), vehicle);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }
}
