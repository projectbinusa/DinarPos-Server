package com.template.eazypos.repository;

import com.template.eazypos.model.Kunjungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface KunjunganRepository extends JpaRepository<Kunjungan , Long> {
    @Query(value = "SELECT * FROM kunjungan WHERE deal < 100" , nativeQuery = true)
    List<Kunjungan> findDealPo();

    @Query(value = "SELECT * FROM kunjungan WHERE deal < 100 AND id_customer = :id ", nativeQuery = true)
    List<Kunjungan> findDealPoAndCustomer(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100" , nativeQuery = true)
    List<Kunjungan> findDealFinish();

    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100 AND id_customer = :id ", nativeQuery = true)
    List<Kunjungan> findDealFinishAndCustomer(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan BETWEEN :tglAwal AND :tglAkhir" , nativeQuery = true)
    List<Kunjungan> findByDate(Date tglAwal , Date tglAkhir);
}
