package com.template.eazypos.repository;

import com.template.eazypos.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal,Long> {
    @Query(value = "SELECT d.* FROM deal d JOIN kunjungan k ON d.id_report = k.id JOIN customer c ON k.id_customer = :id", nativeQuery = true)
    List<Deal> findByCustomerId(Long id);
}
