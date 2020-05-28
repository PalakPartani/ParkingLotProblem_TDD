package com.parkinglot.unittesting;

import com.parkinglot.ParkingLots;
import com.parkinglot.dao.Vehicle;
import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.observer.AirportSecurity;
import com.parkinglot.observer.ParkingLotOwner;
import com.parkinglot.observer.ParkingObservers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;

public class MockitoTest {
    ParkingLots parkingLot;
    Vehicle vehicle;

    @Mock
    AirportSecurity airportSecurity;
    @Mock
    ParkingLotOwner parkingLotOwner;
    @Mock
    ParkingObservers observers;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLots(2);
    }

    @Test
    public void givenCapacityFull_ShouldThrowException() {

        parkingLot.parkVehicle(vehicle, DriverType.NORMAL);
        parkingLot.register(airportSecurity);
        when(airportSecurity.isCapacityFull()).thenThrow(new ParkingLotException("Lot full !", ParkingLotException.ExceptionType.SIZE_FULL));
    }

    @Test
    public void givenCapacityFullToAirportSecurity_ShouldReturnTrue() {
        parkingLot.register(airportSecurity);
        when(airportSecurity.isCapacityFull()).thenReturn(true);
        boolean check = airportSecurity.isCapacityFull();
        Assert.assertTrue(check);
    }

    @Test
    public void givenCapacityFullToOwner_ShouldReturnTrue() {
        parkingLot.register(parkingLotOwner);
        when(parkingLotOwner.isCapacityFull()).thenReturn(true);
        boolean check = parkingLotOwner.isCapacityFull();
        Assert.assertTrue(check);
    }

    @Test
    public void testInterface() {
        when(observers.isCapacityFull()).thenReturn(true);
        Assert.assertTrue(observers.isCapacityFull());
    }

}