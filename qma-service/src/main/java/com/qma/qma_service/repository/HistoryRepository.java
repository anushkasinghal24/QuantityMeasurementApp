package com.qma.qma_service.repository;

import com.qma.qma_service.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<ConversionHistory, Long> {

    List<ConversionHistory> findByUserEmail(String userEmail);
}