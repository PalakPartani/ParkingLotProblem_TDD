package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot();
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        boolean isParked = parkingLot.parkVehicle(new Object());
        Assert.assertTrue(isParked);
    }
}
