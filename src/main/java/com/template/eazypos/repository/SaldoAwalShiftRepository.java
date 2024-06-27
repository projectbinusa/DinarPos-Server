package com.template.eazypos.repository;

import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.SaldoAwalShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaldoAwalShiftRepository extends JpaRepository<SaldoAwalShift , Long> {
    List<SaldoAwalShift> findByDateBetween(Date awal, Date akhir);
}
