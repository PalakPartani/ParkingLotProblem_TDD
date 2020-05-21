package com.parkinglot.exception;

public class ParkingLotException extends RuntimeException {
    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public enum ExceptionType {
        SIZE_FULL,PARKED;
    }
    ExceptionType type;
}
