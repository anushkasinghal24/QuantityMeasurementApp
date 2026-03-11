package com.BridgeLabz.QuantityMeasurementApp.app;

import com.BridgeLabz.QuantityMeasurementApp.controller.QuantityMeasurementController;
import com.BridgeLabz.QuantityMeasurementApp.dto.QuantityDTO;
import com.BridgeLabz.QuantityMeasurementApp.repository.IQuantityMeasurementRepository;
import com.BridgeLabz.QuantityMeasurementApp.repository.QuantityMeasurementCacheRepository;
import com.BridgeLabz.QuantityMeasurementApp.service.IQuantityMeasurementService;
import com.BridgeLabz.QuantityMeasurementApp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        IQuantityMeasurementRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        QuantityDTO q1 = new QuantityDTO(10,"FEET");
        QuantityDTO q2 = new QuantityDTO(10,"FEET");

        boolean result = controller.performComparison(q1,q2);

        System.out.println("Comparison Result: " + result);
    }
}
