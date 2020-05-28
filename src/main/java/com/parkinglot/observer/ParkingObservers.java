package com.parkinglot.observer;

/******************************************
 * purpose:follow observer pattern
 * @author name:Palak
 * @version:1.0
 * @Date:22-05-2020
 *******************************************/
public interface ParkingObservers {
    void setCapacityFull();

    boolean isCapacityFull();

    void setParkingTime(int minute);
    int getParkingTime();
}
