package com.parkinglot.unittesting;

import com.parkinglot.ParkingLotSystem;
import com.parkinglot.ParkingLots;
import com.parkinglot.dao.Vehicle;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ParkingLotSystemMockitoTest {
    ParkingLots parkingLot;
    Vehicle vehicle;
    @Mock
    ParkingLotSystem parkingLotSystem;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLots(2);
    }
}
