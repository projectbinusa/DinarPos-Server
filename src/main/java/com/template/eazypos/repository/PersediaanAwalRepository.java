package com.template.eazypos.repository;

import com.template.eazypos.model.PersediaanAwal;
import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersediaanAwalRepository extends JpaRepository<PersediaanAwal , Long> {
    List<PersediaanAwal> findByTanggal(Date date);
    @Query(value = "SELECT * FROM persediaan_awal WHERE id_transaksi_jual =:id", nativeQuery = true)
    Optional<PersediaanAwal> findByIdTransaksi(Long id);
}
