package com.app.quantitymeasurement.controller;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.dto.ConvertRequest;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityRequest;
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

    // Compare API
    @PostMapping("/compare")
    public boolean compare(@Valid @RequestBody QuantityRequest request) {
        return service.compare(request.getQ1(), request.getQ2());
    }

    // Add API
    @PostMapping("/add")
    public QuantityDTO add(@Valid @RequestBody QuantityRequest request) {
        return service.add(request.getQ1(), request.getQ2());
    }

    //  Subtract API
    @PostMapping("/subtract")
    public QuantityDTO subtract(@Valid @RequestBody QuantityRequest request) {
        return service.subtract(request.getQ1(), request.getQ2());
    }

    //  Divide API
    @PostMapping("/divide")
    public double divide(@Valid @RequestBody QuantityRequest request) {
        return service.divide(request.getQ1(), request.getQ2());
    }

    // Get All
    @GetMapping("/all")
    public List<QuantityMeasurementEntity> getAll() {
        return service.getAll();
    }

    // Get By Id
    @GetMapping("/{id}")
    public QuantityMeasurementEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PostMapping("/convert")
    public double convert(@Valid @RequestBody ConvertRequest request) {

        return service.convert(
                request.getValue(),
                request.getFromUnit(),
                request.getToUnit()
        );
    }
}