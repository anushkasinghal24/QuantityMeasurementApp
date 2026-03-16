package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    @Override
    public void save(QuantityMeasurementEntity entity) {

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
            throw new RuntimeException("Database save failed", e);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        String sql = "SELECT * FROM quantity_measurement_entity";

        try {

            Connection conn = ConnectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

                entity.setOperation(rs.getString("operation"));
                entity.setMeasurementType(rs.getString("measurement_type"));
                entity.setValue1(rs.getDouble("value1"));
                entity.setValue2(rs.getDouble("value2"));
                entity.setResult(rs.getBoolean("result"));

                list.add(entity);
            }

            ConnectionPool.releaseConnection(conn);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from database", e);
        }

        return list;
    }
}