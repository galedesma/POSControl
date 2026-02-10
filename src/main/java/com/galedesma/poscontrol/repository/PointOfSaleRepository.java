package com.galedesma.poscontrol.repository;

import com.galedesma.poscontrol.entity.PointOfSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointOfSaleRepository extends JpaRepository<PointOfSale, Integer> {
}
