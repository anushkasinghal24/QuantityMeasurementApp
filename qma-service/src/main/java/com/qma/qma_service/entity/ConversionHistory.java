package com.qma.qma_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class ConversionHistory {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double value;
    private String fromUnit;
    private String toUnit;
    private double result;

    private String userEmail;

    private LocalDateTime timestamp = LocalDateTime.now();
}