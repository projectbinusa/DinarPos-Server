package com.template.eazypos.repository;

import com.template.eazypos.model.Omzet;
import com.template.eazypos.model.TransaksiIndent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OmzetRepository extends JpaRepository<Omzet , Long> {
    @Query(value = "SELECT * FROM omzet WHERE id_transaksi = :id ", nativeQuery = true)
    Optional<Omzet> findByIdTransaksi(Long id);
}
