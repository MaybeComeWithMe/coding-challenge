package com.ubitricity.codingchallenge.repository;

import com.ubitricity.codingchallenge.model.ChargePoint;
import com.ubitricity.codingchallenge.model.ChargePoint.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {
}
