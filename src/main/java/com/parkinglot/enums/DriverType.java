package com.parkinglot.enums;

import java.util.Comparator;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;

public enum DriverType {
    NORMAL(reverseOrder()), HANDICAP(naturalOrder());
    public Comparator<Integer> order;

    DriverType(Comparator<Integer> order) {
        this.order = order;
    }
}
