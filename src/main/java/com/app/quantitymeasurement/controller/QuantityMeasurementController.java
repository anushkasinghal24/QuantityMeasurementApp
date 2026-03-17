//package com.app.quantitymeasurement.controller;
//
//import com.app.quantitymeasurement.entity.dto.QuantityDTO;
//import com.app.quantitymeasurement.service.IQuantityMeasurementService;
//
//public class QuantityMeasurementController {
//
//    private IQuantityMeasurementService service;
//
//    public QuantityMeasurementController(IQuantityMeasurementService service) {
//        this.service = service;
//    }
//
//    public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {
//        return service.compare(q1,q2);
//    }
//
//    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2){
//        return service.add(q1,q2);
//    }
//}
//
//package com.app.quantitymeasurement.controller;
//
//import com.app.quantitymeasurement.entity.dto.QuantityDTO;
//import com.app.quantitymeasurement.service.IQuantityMeasurementService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/quantity")
//public class QuantityMeasurementController {
//
//    @Autowired
//    private IQuantityMeasurementService service;
//
//    // 🔹 Compare API
//    @PostMapping("/compare")
//    public boolean compare(@RequestBody QuantityDTO[] inputs) {
//        return service.compare(inputs[0], inputs[1]);
//    }
//
//    // 🔹 Add API
//    @PostMapping("/add")
//    public QuantityDTO add(@RequestBody QuantityDTO[] inputs) {
//        return service.add(inputs[0], inputs[1]);
//    }
//
//    // 🔹 Subtract API
//    @PostMapping("/subtract")
//    public QuantityDTO subtract(@RequestBody QuantityDTO[] inputs) {
//        return service.subtract(inputs[0], inputs[1]);
//    }
//
//    // 🔹 Divide API
//    @PostMapping("/divide")
//    public double divide(@RequestBody QuantityDTO[] inputs) {
//        return service.divide(inputs[0], inputs[1]);
//    }
//}



package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.dto.QuantityRequest;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quantity")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // 🔹 Compare API
    @PostMapping("/compare")
    public boolean compare(@RequestBody QuantityDTO[] inputs) {
        return service.compare(inputs[0], inputs[1]);
    }

    // 🔹 Add API
    @PostMapping("/add")
    public QuantityDTO add(@RequestBody QuantityDTO[] inputs) {
        return service.add(inputs[0], inputs[1]);
    }

    // 🔹 Subtract API
    @PostMapping("/subtract")
    public QuantityDTO subtract(@RequestBody QuantityDTO[] inputs) {
        return service.subtract(inputs[0], inputs[1]);
    }

    @PostMapping("/add")
    public QuantityDTO add(@Valid @RequestBody QuantityRequest request) {
        return service.add(request.getQ1(), request.getQ2());
    }

    // 🔹 Divide API
    @PostMapping("/divide")
    public double divide(@RequestBody QuantityDTO[] inputs) {
        return service.divide(inputs[0], inputs[1]);
    }

    @GetMapping
    public List<QuantityMeasurementEntity> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public QuantityMeasurementEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}