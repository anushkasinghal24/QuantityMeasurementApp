package com.app.quantitymeasurement.app;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        String repoType = ApplicationConfig.getProperty("repository.type");
        System.out.println("Repository Type: " + repoType);
        IQuantityMeasurementRepository repository;

        if ("database".equalsIgnoreCase(repoType)) {
            repository = new QuantityMeasurementDatabaseRepository();
            System.out.println("Using Database Repository");
        } else {
            repository = QuantityMeasurementCacheRepository.getInstance();
            System.out.println("Using Cache Repository");
        }

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

//        QuantityDTO q1 = new QuantityDTO(10, "FEET");
//        QuantityDTO q2 = new QuantityDTO(10, "FEET");
//
//        boolean result = controller.performComparison(q1, q2);
//
//        System.out.println("Comparison Result: " + result);
//        QuantityDTO q1 = new QuantityDTO(10,"FEET");
//        QuantityDTO q2 = new QuantityDTO(20,"FEET");
//
//        QuantityDTO result = controller.performAddition(q1,q2);
//
//        System.out.println("Addition Result: " + result.getValue());

        QuantityDTO q1 = new QuantityDTO(5,"FEET");
        QuantityDTO q2 = new QuantityDTO(15,"FEET");

        QuantityDTO result = controller.performAddition(q1,q2);

        System.out.println("Addition Result: " + result.getValue());

    }

    public void save(QuantityMeasurementEntity entity) {

        System.out.println("Saving data to database...");

        String sql = "INSERT INTO quantity_measurement_entity "
                + "(operation, measurement_type, value1, value2, result) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, entity.getOperation());
            stmt.setString(2, entity.getMeasurementType());
            stmt.setDouble(3, entity.getValue1());
            stmt.setDouble(4, entity.getValue2());
            stmt.setBoolean(5, entity.isResult());

            stmt.executeUpdate();

            ConnectionPool.releaseConnection(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}