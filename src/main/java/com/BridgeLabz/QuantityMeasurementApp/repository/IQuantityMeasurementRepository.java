package com.BridgeLabz.QuantityMeasurementApp.repository;

import com.BridgeLabz.QuantityMeasurementApp.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();
}
