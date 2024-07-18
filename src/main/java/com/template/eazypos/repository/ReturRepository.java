package com.template.eazypos.repository;

import com.template.eazypos.dto.GetServiceReturDTO;
import com.template.eazypos.model.Retur;
import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturRepository extends JpaRepository<Retur , Long> {
    @Query("SELECT new com.template.eazypos.dto.GetServiceReturDTO(" +
            "s.idTT, s.checker, s.customer.nama_customer, s.nama, s.alamat, s.cp, s.poin, s.ket, s.produk, s.merk, s.type, s.sn, s.keluhan, s.perlengkapan, s.penerima, " +
            "s.tanggalMasuk, s.tanggalJadi, s.tanggalAmbil, s.biayaSparepart, s.biayaService, s.total, s.estimasi, s.bmax, s.catatan, s.statusEnd, s.taken, s.fb, s.fa, s.timestamp) " +
            "FROM Retur r " +
            "LEFT JOIN r.TTLama s " +
            "LEFT JOIN r.TTBaru s")
    List<GetServiceReturDTO> findAllReturWithoutTeknisi();
}
