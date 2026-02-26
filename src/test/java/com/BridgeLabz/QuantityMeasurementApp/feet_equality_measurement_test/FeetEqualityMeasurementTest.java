package com.BridgeLabz.QuantityMeasurementApp.feet_equality_measurement_test;
import com.BridgeLabz.QuantityMeasurementApp.feet_equality_measurement.FeetEqualityMeasurement;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeetEqualityMeasurementTest {

    @Test
    void testEquality_SameValue() {
        FeetEqualityMeasurement.Feet feet1 =
                new FeetEqualityMeasurement.Feet(1.0);
        FeetEqualityMeasurement.Feet feet2 =
                new FeetEqualityMeasurement.Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void testEquality_DifferentValue() {
        FeetEqualityMeasurement.Feet feet1 =
                new FeetEqualityMeasurement.Feet(1.0);
        FeetEqualityMeasurement.Feet feet2 =
                new FeetEqualityMeasurement.Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    void testEquality_NullComparison() {
        FeetEqualityMeasurement.Feet feet =
                new FeetEqualityMeasurement.Feet(1.0);

        assertFalse(feet.equals(null));
    }

    @Test
    void testEquality_SameReference() {
        FeetEqualityMeasurement.Feet feet =
                new FeetEqualityMeasurement.Feet(1.0);

        assertTrue(feet.equals(feet));
    }

    @Test
    void testEquality_SymmetricProperty() {
        FeetEqualityMeasurement.Feet feet1 =
                new FeetEqualityMeasurement.Feet(1.0);
        FeetEqualityMeasurement.Feet feet2 =
                new FeetEqualityMeasurement.Feet(1.0);

        assertTrue(feet1.equals(feet2));
        assertTrue(feet2.equals(feet1));
    }

    @Test
    void testValidateInput_ValidNumber() {
        double result = FeetEqualityMeasurement.validateInput("5.5");
        assertEquals(5.5, result);
    }

    @Test
    void testValidateInput_InvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            FeetEqualityMeasurement.validateInput("abc");
        });
    }
}
