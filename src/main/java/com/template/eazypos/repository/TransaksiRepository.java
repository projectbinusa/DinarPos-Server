package com.template.eazypos.repository;

import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    Boolean existsByNoFaktur(String NoFaktur);
    @Query(value = "SELECT RIGHT(no_faktur, 4) AS kd_max FROM Transaksi WHERE no_faktur LIKE %:pattern% ORDER BY tanggal DESC LIMIT 1", nativeQuery = true)
    Optional<String> findMaxKode(String pattern);

    Optional<Transaksi> findTopByNoFakturLikeOrderByTanggalDesc(String pattern);
}
