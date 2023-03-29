package com.crudapp.car_rental_backend.decorator;

import java.math.BigDecimal;

public abstract class AbstractCarRentDecorator implements CarRent {
    private final CarRent carRent;

    protected AbstractCarRentDecorator(CarRent carRent) {
        this.carRent = carRent;
    }

    @Override
    public BigDecimal getCost() {
        return carRent.getCost();
    }

    @Override
    public String getDescription() {
        return carRent.getDescription();
    }
}
