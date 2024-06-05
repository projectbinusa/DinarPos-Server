package com.template.eazypos.repository;


import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceBarang, Long> {
    @Query(value = "SELECT * FROM service  WHERE tgl_masuk BETWEEN :tanggalAwal AND :tanggalAkhir  AND status_end = :status" , nativeQuery = true)
    List<ServiceBarang> findByTanggalAndStatus(Date tanggalAwal, Date tanggalAkhir, String status);

    @Query(value = "SELECT * FROM service WHERE id_tt = :id" , nativeQuery = true)
    Optional<ServiceBarang> findByIdTT (Long id);

    @Query(value = "SELECT * FROM service WHERE id_teknisi = :id" , nativeQuery = true)
    Optional<ServiceBarang> findByIdTeknisi (Long id);
    @Query(value = "SELECT * FROM service WHERE taken = 'Y'" , nativeQuery = true)
    List<ServiceBarang> findByTaken ();

    @Query("SELECT t.nama, t.id AS id_teknisi, " +
            "(SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE s.teknisi.id = t.id AND FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m')) AS ttl, " +
            "(SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE s.teknisi.id = t.id AND FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%READY%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total != 0))) AS success, " +
            "(SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE s.teknisi.id = t.id AND FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%PROSES%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total = 0))) AS nots " +
            "FROM Teknisi t ORDER BY success DESC")
    List<Object[]> findDataService(@Param("months") Date months);

    @Query("SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND s.teknisi.bagian = 'Elektro'")
    int countTotalServiceElektro(@Param("months") Date months);

    @Query("SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND s.teknisi.bagian = 'PC'")
    int countTotalServiceCPU(@Param("months") Date months);

    @Query("SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%READY%' OR s.statusEnd LIKE '%CANCEL%') AND s.teknisi.bagian = 'Elektro'")
    int countTotalServiceSuccessElektro(@Param("months") Date months);

    @Query("SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%PROSES%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total = 0)) AND s.teknisi.bagian = 'Elektro'")
    int countTotalServiceNotElektro(@Param("months") Date months);

    @Query("SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%PROSES%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total = 0)) AND s.teknisi.bagian = 'PC'")
    int countTotalServiceNotCPU(@Param("months") Date months);

    @Query("SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%READY%' OR s.statusEnd LIKE '%CANCEL%') AND s.teknisi.bagian = 'PC'")
    int countTotalServiceSuccessCPU(@Param("months") Date months);
}
