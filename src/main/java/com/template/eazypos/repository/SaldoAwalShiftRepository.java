package com.template.eazypos.repository;

import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.SaldoAwalShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaldoAwalShiftRepository extends JpaRepository<SaldoAwalShift , Long> {
    @Query("SELECT s FROM SaldoAwalShift s WHERE s.date = :date AND s.shift = :shift")
    List<SaldoAwalShift> findByDateAndShift(@Param("date") Date date, @Param("shift") String shift);
}
