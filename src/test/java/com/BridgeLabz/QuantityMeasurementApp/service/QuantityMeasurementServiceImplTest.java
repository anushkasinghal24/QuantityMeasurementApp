package com.BridgeLabz.QuantityMeasurementApp.service;
import com.BridgeLabz.QuantityMeasurementApp.dto.QuantityDTO;
import com.BridgeLabz.QuantityMeasurementApp.repository.QuantityMeasurementCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceImplTest {

    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        service = new QuantityMeasurementServiceImpl(
                QuantityMeasurementCacheRepository.getInstance()
        );
    }

    @Test
    void givenTwoEqualLengths_WhenCompared_ShouldReturnTrue() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(120, "INCH");

        boolean result = service.compare(q1, q2);

        assertTrue(result);
    }

    @Test
    void givenTwoLengths_WhenAdded_ShouldReturnResult() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(24, "INCH");

        QuantityDTO result = service.add(q1, q2);

        assertNotNull(result);
    }
}