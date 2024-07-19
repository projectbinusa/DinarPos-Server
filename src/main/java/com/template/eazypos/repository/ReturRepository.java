package com.template.eazypos.repository;

import com.template.eazypos.dto.ReturServiceDTO;
import com.template.eazypos.model.Retur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturRepository extends JpaRepository<Retur , Long> {
    @Query("SELECT new com.template.eazypos.dto.ReturServiceDTO(" +
            "r.id, r.tanggalRetur, " +
            "s1.idTT, s1.checker, s1.customer.nama_customer, s1.nama, s1.alamat, s1.cp, s1.poin, s1.ket, s1.produk, s1.merk, s1.type, s1.sn, s1.keluhan, s1.perlengkapan, s1.penerima, s1.tanggalMasuk, s1.tanggalJadi, s1.tanggalAmbil, s1.biayaSparepart, s1.biayaService, s1.total, s1.estimasi, s1.bmax, s1.catatan, s1.statusEnd, s1.taken, s1.fb, s1.fa, s1.timestamp, " +
            "s2.idTT, s2.checker, s2.customer.nama_customer, s2.nama, s2.alamat, s2.cp, s2.poin, s2.ket, s2.produk, s2.merk, s2.type, s2.sn, s2.keluhan, s2.perlengkapan, s2.penerima, s2.tanggalMasuk, s2.tanggalJadi, s2.tanggalAmbil, s2.biayaSparepart, s2.biayaService, s2.total, s2.estimasi, s2.bmax, s2.catatan, s2.statusEnd, s2.taken, s2.fb, s2.fa, s2.timestamp) " +
            "FROM Retur r " +
            "LEFT JOIN r.TTLama s1 " +
            "LEFT JOIN r.TTBaru s2")
    List<ReturServiceDTO> findAllReturService();
}
