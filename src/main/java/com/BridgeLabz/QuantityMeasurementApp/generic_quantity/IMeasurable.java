package com.BridgeLabz.QuantityMeasurementApp.generic_quantity;

//public interface IMeasurable {
//
//
//    double getConversionFactor();
//    default double convertToBaseUnit(double value) {
//
//        return value * getConversionFactor();
//    }
//    default double convertFromBaseUnit(double baseValue) {
//        return baseValue / getConversionFactor();
//    }
//    String getUnitName();
//    default boolean supportsArithmetic() {return true;}
//}

//package com.BridgeLabz.QuantityMeasurementApp.generic_quantity;

//public interface IMeasurable {
//
//    double getConversionFactor();
//
//    default double convertToBaseUnit(double value) {
//        return value * getConversionFactor();
//    }
//
//    default double convertFromBaseUnit(double baseValue) {
//        return baseValue / getConversionFactor();
//    }
//
//    String getUnitName();
//
//    // 🔹 UC15 new method
//    String getMeasurementType();
//
//    default boolean supportsArithmetic() {
//        return true;
//    }
//
//    // 🔹 UC15 helper method
//    static IMeasurable getUnitByName(String unitName) {
//
//        for (LengthUnit unit : LengthUnit.values())
//            if (unit.getUnitName().equalsIgnoreCase(unitName))
//                return unit;
//
//        for (WeightUnit unit : WeightUnit.values())
//            if (unit.getUnitName().equalsIgnoreCase(unitName))
//                return unit;
//
//        for (VolumeUnit unit : VolumeUnit.values())
//            if (unit.getUnitName().equalsIgnoreCase(unitName))
//                return unit;
//
//        for (TemperatureUnit unit : TemperatureUnit.values())
//            if (unit.getUnitName().equalsIgnoreCase(unitName))
//                return unit;
//
//        throw new IllegalArgumentException("Invalid unit: " + unitName);
//    }
//}


public interface IMeasurable {

    double getConversionFactor();

    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }

    String getUnitName();

    // UC15
    String getMeasurementType();

    default boolean supportsArithmetic() {
        return true;
    }

    // UC15 helper
    static IMeasurable getUnitByName(String unitName) {

        if (unitName == null)
            throw new IllegalArgumentException("Unit cannot be null");

        String normalized = unitName.toUpperCase();

        // handle common alias
        if (normalized.equals("INCH"))
            normalized = "INCHES";

        for (LengthUnit unit : LengthUnit.values())
            if (unit.getUnitName().equalsIgnoreCase(normalized))
                return unit;

        for (WeightUnit unit : WeightUnit.values())
            if (unit.getUnitName().equalsIgnoreCase(normalized))
                return unit;

        for (VolumeUnit unit : VolumeUnit.values())
            if (unit.getUnitName().equalsIgnoreCase(normalized))
                return unit;

        for (TemperatureUnit unit : TemperatureUnit.values())
            if (unit.getUnitName().equalsIgnoreCase(normalized))
                return unit;

        throw new IllegalArgumentException("Invalid unit: " + unitName);
    }
}

