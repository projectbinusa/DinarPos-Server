package com.template.eazypos.repository;

import com.template.eazypos.model.Kunjungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface KunjunganRepository extends JpaRepository<Kunjungan , Long> {
    @Query(value = "SELECT * FROM kunjungan WHERE deal < 100" , nativeQuery = true)
    List<Kunjungan> findDealPo();

    @Query(value = "SELECT * FROM kunjungan WHERE deal < 100 AND deal >= 80 AND id_salesman = :id ", nativeQuery = true)
    List<Kunjungan> findDealPoAndSalesman(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100" , nativeQuery = true)
    List<Kunjungan> findDealFinish();

    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100 AND id_salesman = :id ", nativeQuery = true)
    List<Kunjungan> findDealFinishAndCustomer(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan BETWEEN :tglAwal AND :tglAkhir" , nativeQuery = true)
    List<Kunjungan> findByDate(Date tglAwal , Date tglAkhir);
    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan BETWEEN :tglAwal AND :tglAkhir AND id_salesman = :id" , nativeQuery = true)
    List<Kunjungan> findByDateAndSalesman(Date tglAwal , Date tglAkhir , Long id);
    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan = :tgl" , nativeQuery = true)
    List<Kunjungan> findByTanggal(Date tgl);
    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan = :tgl AND id_salesman = :id" , nativeQuery = true)
    List<Kunjungan> findByTanggalSalesman(Date tgl , Long id);
    @Query(value = "SELECT * FROM kunjungan WHERE id_customer = :id" , nativeQuery = true)
    List<Kunjungan> findByIdCustomer(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE MONTH(tgl_kunjungan) = :bulan" , nativeQuery = true)
    List<Kunjungan> findByBulan(int bulan);

    @Query(value = "SELECT k FROM Kunjungan k JOIN k.salesman s " +
            "GROUP BY k.tanggalKunjungan, s.id " +
            "ORDER BY k.tanggalKunjungan DESC")
    List<Kunjungan> findKunjunganGroupedByTanggalAndSalesman();

    @Query(value = "SELECT k FROM Kunjungan k JOIN k.salesman s " +
            "WHERE k.tanggalKunjungan BETWEEN :tgl1 AND :tgl2 " +
            "GROUP BY k.tanggalKunjungan, s.id " +
            "ORDER BY k.tanggalKunjungan ASC")
    List<Kunjungan> findKunjunganByDateRangeGroupedBySalesman(
            @Param("tgl1") Date tgl1,
            @Param("tgl2") Date tgl2);

    @Query(value = "SELECT k FROM Kunjungan k JOIN k.salesman s " +
            "WHERE k.tanggalKunjungan BETWEEN :tgl1 AND :tgl2 " +
            "AND s.id = :idm " +
            "GROUP BY k.tanggalKunjungan " +
            "ORDER BY k.tanggalKunjungan ASC")
    List<Kunjungan> findKunjunganByDateRangeAndSalesman(
            @Param("tgl1") Date tgl1,
            @Param("tgl2") Date tgl2,
            @Param("idm") Long idm);
}


