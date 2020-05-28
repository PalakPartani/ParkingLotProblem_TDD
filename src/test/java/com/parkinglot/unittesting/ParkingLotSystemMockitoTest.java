package com.parkinglot.unittesting;

import com.parkinglot.ParkingLotSystem;
import com.parkinglot.ParkingLots;
import com.parkinglot.dao.Vehicle;
import com.parkinglot.enums.DriverType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ParkingLotSystemMockitoTest {
    @Mock
    ParkingLots parkingLots;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    ParkingLotSystem parkingLotSystem;
    Vehicle vehicle;

    @Before
    public void setup() {
        vehicle = new Vehicle();
        parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.addMultipleLots(parkingLots);
    }

    @Test
    public void givenVehicleToPark_WhenSetParked_ShouldReturnTrue() {
        when(parkingLots.parkVehicle(vehicle, DriverType.NORMAL)).thenReturn(true);
        boolean check = parkingLotSystem.park(vehicle, DriverType.NORMAL);
        assertTrue(check);
    }

    @Test
    public void givenVehicleToUnPark_WhenSetUnParked_ShouldReturnTrue() {
        when(parkingLots.unParkVehicle(vehicle)).thenReturn(true);
        boolean check = parkingLotSystem.unPark(vehicle);
        assertTrue(check);
    }

    @Test
    public void givenvCheckIfVehicleParked_WhenSetParked_ShouldReturnTrue() {
        parkingLotSystem.park(vehicle, DriverType.NORMAL);
        when(parkingLots.isVehicleParked(vehicle)).thenReturn(true);
        boolean check = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(check);
    }
}
