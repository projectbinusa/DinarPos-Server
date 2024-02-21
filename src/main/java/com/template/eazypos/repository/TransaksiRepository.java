package com.template.eazypos.repository;

import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    @Query(value = "SELECT t.no_faktur FROM tabel_transaksi t WHERE MONTH(t.tanggal) = ?1 AND YEAR(t.tanggal) = ?2 ORDER BY t.id_transaksi DESC LIMIT 1", nativeQuery = true)
    String findLastNotaByMonthAndYear(int month, int year);
}
