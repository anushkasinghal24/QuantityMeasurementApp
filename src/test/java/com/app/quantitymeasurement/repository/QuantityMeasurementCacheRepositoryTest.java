////package com.app.quantitymeasurement.repository;
////import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
////import org.junit.jupiter.api.Test;
////
////import static org.junit.jupiter.api.Assertions.*;
////
////class QuantityMeasurementCacheRepositoryTest {
////
////    @Test
////    void givenEntity_WhenSaved_ShouldStoreInRepository() {
////
////        QuantityMeasurementCacheRepository repo =
////                QuantityMeasurementCacheRepository.getInstance();
////
////        QuantityMeasurementEntity entity =
////                new QuantityMeasurementEntity(10,20,"ADD",30);
////
////        repo.save(entity);
////
////        assertFalse(repo.findAll().isEmpty());
////    }
////}
//
//package com.app.quantitymeasurement.repository;
//
//import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class QuantityMeasurementCacheRepositoryTest {
//
//    @Test
//    void givenEntity_WhenSaved_ShouldStoreInRepository() {
//
//        QuantityMeasurementCacheRepository repo =
//                QuantityMeasurementCacheRepository.getInstance();
//
//        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
//
//        entity.setOperation("ADD");
//        entity.setMeasurementType("FEET");
//        entity.setValue1(10);
//        entity.setValue2(20);
//        entity.setResult(true);   // boolean
//
//        repo.save(entity);
//
//        assertFalse(repo.findAll().isEmpty());
//    }
//}