package com.app.quantitymeasurement.entity.model;
import com.app.quantitymeasurement.unit.generic_quantity.IMeasurable;
public class QuantityModel<U extends IMeasurable> {

    private double value;
    private U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }
}