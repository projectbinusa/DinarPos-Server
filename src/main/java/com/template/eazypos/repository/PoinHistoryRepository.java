package com.template.eazypos.repository;

import com.template.eazypos.model.PoinHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PoinHistoryRepository extends JpaRepository<PoinHistory , Long> {
    @Query("SELECT ph.teknisi.id AS teknisiId, ph.teknisi.nama AS teknisiNama, SUM(ph.poin) AS totalPoin, SUM(ph.nominal) AS totalNominal " +
            "FROM PoinHistory ph " +
            "JOIN ph.teknisi t " +
            "WHERE FUNCTION('YEAR', ph.tanggal) = FUNCTION('YEAR', :month) " +
            "AND FUNCTION('MONTH', ph.tanggal) = FUNCTION('MONTH', :month) " +
            "GROUP BY ph.teknisi.id, ph.teknisi.nama " +
            "ORDER BY SUM(ph.poin) DESC")
    List<PoinHistory> findByMonth(@Param("month") LocalDate month);
}
