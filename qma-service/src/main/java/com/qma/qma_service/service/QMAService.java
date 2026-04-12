package com.qma.qma_service.service;

import com.qma.qma_service.entity.ConversionHistory;
import com.qma.qma_service.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QMAService {

    @Autowired
    private HistoryRepository repo;

    public double convert(String type, double value, String from, String to, String email) {

        double result = 0;

        // ✅ Business logic
        if ("LENGTH".equalsIgnoreCase(type)) {
            if ("METER".equalsIgnoreCase(from) && "CM".equalsIgnoreCase(to)) {
                result = value * 100;
            } else {
                throw new IllegalArgumentException("Unsupported unit conversion");
            }
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }



        // 💾 Save history
        ConversionHistory history = new ConversionHistory();
        history.setType(type);
        history.setValue(value);
        history.setFromUnit(from);
        history.setToUnit(to);
        history.setResult(result);
        history.setUserEmail(email);

        repo.save(history);

        return result;
    }

    public String compare(String type,
                          double value1, String unit1,
                          double value2, String unit2,
                          String email) {

        double v1 = convert(type, value1, unit1, "CM", email);
        double v2 = convert(type, value2, unit2, "CM", email);

        if (v1 > v2) return "FIRST_GREATER";
        else if (v1 < v2) return "SECOND_GREATER";
        else return "EQUAL";
    }

    public List<ConversionHistory> getHistory(String email) {
        return repo.findByUserEmail(email);
    }
}