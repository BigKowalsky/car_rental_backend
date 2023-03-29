package com.crudapp.car_rental_backend.decorator;

import java.math.BigDecimal;

public class DailyCarRentDecorator extends  AbstractCarRentDecorator {
    public DailyCarRentDecorator(CarRent carRent) {
        super(carRent);
    }

    @Override
    public BigDecimal getCost() {
        return super.getCost().add(new BigDecimal(5));
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " by rent per day.";
    }
}
