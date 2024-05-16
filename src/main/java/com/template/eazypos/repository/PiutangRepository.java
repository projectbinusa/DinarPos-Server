package com.template.eazypos.repository;

import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.Piutang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PiutangRepository extends JpaRepository<Piutang , Long> {
    @Query(value = "SELECT h FROM Piutang h WHERE h.pelunasan <> '0'")
    List<Piutang> findAllPiutang();

    @Query("SELECT h FROM Piutang h WHERE h.date BETWEEN :tglAwal AND :tglAkhir")
    List<Piutang> findByTanggalBetween(@Param("tglAwal") Date tglAwal, @Param("tglAkhir") Date tglAkhir);
}
