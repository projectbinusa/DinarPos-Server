package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksiIndent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BarangTransaksiIndentRepository extends JpaRepository<BarangTransaksiIndent, Long> {
    @Query(value = "SELECT * FROM tabel_barang_transaksi_indent WHERE id_transaksi_indent = :idTransaksiIndent", nativeQuery = true)
    List<BarangTransaksiIndent> findByTransaksiIndentId(Long idTransaksiIndent);
}
