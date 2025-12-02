package com.supplychain.supplychain.repository;

import com.supplychain.supplychain.entity.DigitalTwin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalTwinRepository extends JpaRepository<DigitalTwin, Long> {
    List<DigitalTwin> findByTwinType(String twinType);
    List<DigitalTwin> findByStatus(String status);
}