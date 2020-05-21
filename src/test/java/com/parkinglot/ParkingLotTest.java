package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        vehicle = new Object();
    }

    //UC1
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        boolean isParked = parkingLot.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        parkingLot.parkVehicle(vehicle);
        boolean isUnParked = parkingLot.unParkVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }

    //UC3
    @Test
    public void givenVehicleParked_WhenLotFull_ShouldReturnTrue() {
        Object vehicle2 = new Object();
        parkingLot.parkVehicle(vehicle);
        boolean capacityFull = parkingLot.parkVehicle(vehicle2);
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenVehicleParked_WhenLotFull_ShouldThrowException() {
        try {
            Object vehicle2 = new Object();
            Object vehicle3 = new Object();
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle2);
            boolean capacityFull = parkingLot.parkVehicle(vehicle3);
            Assert.assertTrue(capacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Lot full !", e.getMessage());
        }
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(vehicle);
            boolean capacityFull = parkingLot.parkVehicle(vehicle);
            Assert.assertTrue(capacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle already parked !", e.getMessage());
        }
    }

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnTrue() {
        Object vehicle2 = new Object();
        parkingLot.parkVehicle(vehicle2);
        boolean isUnParked = parkingLot.unParkVehicle(vehicle);
        Assert.assertFalse(isUnParked);
    }

    @Test
    public void givenWhenLotIsFull_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerSecurity(airportSecurity);
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);

        }
    }

    @Test
    public void givenWhenLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerOwner(parkingLotOwner);
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }
}
