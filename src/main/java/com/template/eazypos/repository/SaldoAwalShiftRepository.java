package com.template.eazypos.repository;

import com.template.eazypos.model.SaldoAwalShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldoAwalShiftRepository extends JpaRepository<SaldoAwalShift , Long> {
}
