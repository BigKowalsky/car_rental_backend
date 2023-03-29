package com.crudapp.car_rental_backend.decorator;

import com.crudapp.car_rental_backend.domain.Car;

import java.math.BigDecimal;

public class BasicCarRent implements CarRent {

    private final Car car;

    public BasicCarRent(Car car) {
        this.car = car;
    }

    @Override
    public BigDecimal getCost() {
        return new BigDecimal(String.valueOf(car.getBasicPrice()));
    }

    @Override
    public String getDescription() {
        return "Basic Price";
    }
}
