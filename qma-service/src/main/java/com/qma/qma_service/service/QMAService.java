package com.qma.qma_service.service;

import com.qma.qma_service.entity.ConversionHistory;
import com.qma.qma_service.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QMAService {

    @Autowired
    private HistoryRepository repo;

    public double convert(String type, double value, String from, String to) {

        double result = 0;

        if(type.equals("LENGTH")) {
            if(from.equals("METER") && to.equals("CM")) {
                result = value * 100;
            }
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.getName() != null) {

            ConversionHistory history = new ConversionHistory();
            history.setType(type);
            history.setValue(value);
            history.setFromUnit(from);
            history.setToUnit(to);
            history.setResult(result);
            history.setUserEmail(auth.getName());

            repo.save(history);
        }

        return result;
    }

    public List<ConversionHistory> getHistory() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findByUserEmail(email);
    }
}