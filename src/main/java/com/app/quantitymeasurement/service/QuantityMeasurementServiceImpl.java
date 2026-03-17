//package com.app.quantitymeasurement.service;
//
//import com.app.quantitymeasurement.entity.dto.QuantityDTO;
//import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
//import com.app.quantitymeasurement.exception.QuantityMeasurementException;
//import com.app.quantitymeasurement.unit.generic_quantity.IMeasurable;
//import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
//
//public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
//
//    private IQuantityMeasurementRepository repository;
//
//    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
//        return null;
//    }
//
//
//    @Override
//    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
//
//        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
//        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());
//
//        if (!unit1.getMeasurementType().equals(unit2.getMeasurementType())) {
//            throw new QuantityMeasurementException("Units mismatch");
//        }
//
//        double base1 = unit1.convertToBaseUnit(q1.getValue());
//        double base2 = unit2.convertToBaseUnit(q2.getValue());
//
//        boolean result = Double.compare(base1, base2) == 0;
//
//        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
//        entity.setOperation("COMPARE");
//        entity.setMeasurementType(q1.getUnit());
//        entity.setValue1(q1.getValue());
//        entity.setValue2(q2.getValue());
//        entity.setResult(result);
//
//        repository.save(entity);
//
//        return result;
//    }
//
//    @Override
//    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
//
//        double result = q1.getValue() + q2.getValue();
//
//        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
//
//        entity.setOperation("ADD");
//        entity.setMeasurementType(q1.getUnit());
//        entity.setValue1(q1.getValue());
//        entity.setValue2(q2.getValue());
//        entity.setResult(true);
//
//        repository.save(entity);
//
//        return new QuantityDTO(result, q1.getUnit());
//    }
//
//    @Override
//    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
//        return null;
//    }
//
//    @Override
//    public double divide(QuantityDTO q1, QuantityDTO q2) {
//        return 0;
//    }
//
//}


package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.exception.ResourceNotFoundException;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.generic_quantity.IMeasurable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        return null; // implement later
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());

        if (!unit1.getMeasurementType().equals(unit2.getMeasurementType())) {
            throw new QuantityMeasurementException("Units mismatch");
        }

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());

        boolean result = Double.compare(base1, base2) == 0;

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("COMPARE");
        entity.setMeasurementType(q1.getUnit());
        entity.setValue1(q1.getValue());
        entity.setValue2(q2.getValue());
        entity.setResult(result);

        repository.save(entity);

        return result;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() + q2.getValue();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("ADD");
        entity.setMeasurementType(q1.getUnit());
        entity.setValue1(q1.getValue());
        entity.setValue2(q2.getValue());
        entity.setResult(true);

        repository.save(entity);

        return new QuantityDTO(result, q1.getUnit());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        double result = q1.getValue() - q2.getValue();

        return new QuantityDTO(result, q1.getUnit());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        if (q2.getValue() == 0) {
            throw new QuantityMeasurementException("Cannot divide by zero");
        }
        return q1.getValue() / q2.getValue();
    }

    @Override
    public List<QuantityMeasurementEntity> getAll() {
        return List.of();
    }

    @Override
    public QuantityMeasurementEntity getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data not found with id: " + id));
    }

    @Override
    public void delete(Long id) {

        QuantityMeasurementEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Data not found with id: " + id));

        repository.delete(entity);
    }
}