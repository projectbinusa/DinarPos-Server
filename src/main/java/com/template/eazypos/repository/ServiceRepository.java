package com.template.eazypos.repository;


import com.template.eazypos.dto.ServiceDataDTO;
import com.template.eazypos.dto.ServiceReportDTO;
import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceBarang, Long> {
    @Query(value = "SELECT * FROM service  WHERE tgl_masuk BETWEEN :tanggalAwal AND :tanggalAkhir  AND status_end = :status AND taken = 'N'" , nativeQuery = true)
    List<ServiceBarang> findByTanggalAndStatus(Date tanggalAwal, Date tanggalAkhir, String status);

    @Query(value = "SELECT * FROM service WHERE id_tt = :id" , nativeQuery = true)
    Optional<ServiceBarang> findByIdTT (Long id);

    boolean existsByIdTT(Long idTT);

    @Query(value = "SELECT COUNT(*) FROM ServiceBarang")
    Long countTotalService();

    @Query(value = "SELECT * FROM service WHERE id_teknisi = :id" , nativeQuery = true)
    Optional<ServiceBarang> findByIdTeknisi (Long id);
    @Query(value = "SELECT * FROM service WHERE taken = 'Y'" , nativeQuery = true)
    List<ServiceBarang> findByTaken ();
    @Query(value = "SELECT * FROM service WHERE taken = 'N'" , nativeQuery = true)
    List<ServiceBarang> findByTakenN();

    @Query("SELECT new com.template.eazypos.dto.ServiceDataDTO(t.nama, t.id, " +
            "(SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE s.teknisi.id = t.id AND FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m')), " +
            "(SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE s.teknisi.id = t.id AND FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%READY%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total != 0))), " +
            "(SELECT COUNT(s.idTT) FROM ServiceBarang s WHERE s.teknisi.id = t.id AND FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = FUNCTION('DATE_FORMAT', :months, '%Y-%m') AND (s.statusEnd LIKE '%PROSES%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total = 0))) " +
            ") " +
            "FROM Teknisi t")
    List<ServiceDataDTO> findDataService(@Param("months") Date months);

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

    @Query("SELECT new com.template.eazypos.dto.ServiceReportDTO(" +
            "t.nama, t.id, " +
            "COUNT(s.idTT), " +
            "COUNT(CASE WHEN s.statusEnd LIKE '%READY%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total <> 0) THEN 1 END), " +
            "COUNT(CASE WHEN s.statusEnd LIKE '%PROSES%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total = 0) THEN 1 END)) " +
            "FROM ServiceBarang s " +
            "JOIN s.teknisi t " +
            "WHERE FUNCTION('YEAR', s.tanggalMasuk) = FUNCTION('YEAR', :month) " +
            "AND FUNCTION('MONTH', s.tanggalMasuk) = FUNCTION('MONTH', :month) " +
            "GROUP BY t.nama, t.id " +
            "ORDER BY COUNT(CASE WHEN s.statusEnd LIKE '%READY%' OR (s.statusEnd LIKE '%CANCEL%' AND s.total <> 0) THEN 1 END) DESC")
    List<ServiceReportDTO> findDataServicePimpinan(@Param("month") Date month);

    @Query("SELECT COUNT(s) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.teknisi.id IN " +
            "(SELECT t.id FROM Teknisi t WHERE t.bagian = 'Elektro')")
    int totalServiceElektro(@Param("months") String months);

    @Query("SELECT COUNT(s) FROM ServiceBarang s WHERE FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.teknisi.id IN " +
            "(SELECT t.id FROM Teknisi t WHERE t.bagian = 'PC')")
    int totalServiceCpu(@Param("months") String months);

    @Query("SELECT COUNT(s) FROM ServiceBarang s WHERE (FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%READY%' AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'Elektro')) OR " +
            "(FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%CANCEL%' AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'Elektro'))")
    int totalServiceSuccessElektro(@Param("months") String months);

    @Query("SELECT COUNT(s) FROM ServiceBarang s WHERE (FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%PROSES%' AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'Elektro')) OR " +
            "(FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%CANCEL%' AND s.total = 0 AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'Elektro'))")
    int totalServiceNotElektro(@Param("months") String months);

    @Query("SELECT COUNT(s) FROM ServiceBarang s WHERE (FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%PROSES%' AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'PC')) OR " +
            "(FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%CANCEL%' AND s.total = 0 AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'PC'))")
    int totalServiceNotCpu(@Param("months") String months);

    @Query("SELECT COUNT(s) FROM ServiceBarang s WHERE (FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%READY%' AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'PC')) OR " +
            "(FUNCTION('DATE_FORMAT', s.tanggalMasuk, '%Y-%m') = :months AND s.statusEnd LIKE '%CANCEL%' AND " +
            "s.teknisi.id IN (SELECT t.id FROM Teknisi t WHERE t.bagian = 'PC'))")
    int totalServiceSuccessCpu(@Param("months") String months);

    @Query(value = "SELECT s.*, " +
            "(SELECT st.status FROM status st WHERE st.id_tt = s.id_tt AND st.id = (SELECT MAX(st2.id) FROM status st2 WHERE st2.id_tt = s.id_tt)) AS sts " +
            "FROM service s " +
            "WHERE s.taken != 'Y' AND s.status_end NOT LIKE '%CANCEL%' " +
            "ORDER BY CASE " +
            "WHEN s.status_end = 'N_A' THEN 1 " +
            "WHEN s.status_end LIKE '%PROSES%' THEN 2 " +
            "WHEN s.status_end LIKE '%READY%' THEN 3 " +
            "ELSE 4 " +
            "END DESC", nativeQuery = true)
    List<ServiceBarang> getService();

    @Query("SELECT s FROM ServiceBarang s " +
            "WHERE (:status IS NULL OR s.statusEnd = :status OR " +
            "(s.statusEnd = 'READY_S' AND :status = 'READY') OR " +
            "(s.statusEnd = 'READY_T' AND :status = 'READY')) " +
            "AND s.tanggalMasuk BETWEEN :awal AND :akhir")
    List<ServiceBarang> filterByDateAndStatus(@Param("awal") Date awal, @Param("akhir") Date akhir, @Param("status") String status);

    @Query("SELECT s FROM ServiceBarang s " +
            "WHERE (:status = 'READY' AND (s.statusEnd = 'READY_S' OR s.statusEnd = 'READY_T')) " +
            "OR (s.statusEnd = :status AND :status != 'READY')")
    List<ServiceBarang> filterByStatus(@Param("status") String status);

    @Query("SELECT s FROM ServiceBarang s WHERE s.tanggalMasuk >= :awal AND s.tanggalMasuk <= :akhir")
    List<ServiceBarang> filterByDateRange(@Param("awal") Date awal, @Param("akhir") Date akhir);

    @Query(value = "SELECT * FROM service WHERE taken = 'Y'", nativeQuery = true)
    List<ServiceBarang> findByStatusEndContainingAndTakenAndTglMasukBetween(String statusEnd, String taken, String tglAwal, String tglAkhir);

    @Query(value = "SELECT * FROM service WHERE status_end = :status AND tgl_masuk BETWEEN :tglAwal AND :tglAkhir", nativeQuery = true)
    List<ServiceBarang> findByStatusEndAndTglMasukBetween(@Param("status") String status, @Param("tglAwal") String tglAwal, @Param("tglAkhir") String tglAkhir);

    @Query(value = "SELECT * FROM service WHERE status_end = :status AND tgl_masuk BETWEEN :tglAwal AND :tglAkhir", nativeQuery = true)
    List<ServiceBarang> findByStatusAndTanggalMasukBetween(@Param("status") String status, @Param("tglAwal") String tglAwal, @Param("tglAkhir") String tglAkhir);

    @Query(value = "SELECT * FROM service WHERE id_tt IN (SELECT id_tt_baru FROM retur WHERE id_tt_baru=service.id_tt) AND taken='N' AND DATE(tgl_masuk) >= :tglAwal AND DATE(tgl_masuk) <= :tglAkhir", nativeQuery = true)
    List<ServiceBarang> findServiceRetur(String tglAwal, String tglAkhir);

    @Query(value = "SELECT * FROM service WHERE id_tt IN (SELECT id_tt_baru FROM retur WHERE id_tt_baru=service.id_tt) AND taken='N' AND DATE(tgl_masuk) >= :tglAwal AND DATE(tgl_masuk) <= :tglAkhir", nativeQuery = true)
    List<ServiceBarang> findServiceCansel(@Param("tglAwal") String tglAwal, @Param("tglAkhir") String tglAkhir);

    @Query(value = "SELECT * FROM service WHERE taken = 'N' AND tgl_masuk < NOW() - INTERVAL 1 WEEK ORDER BY tgl_masuk ASC", nativeQuery = true)
    List<ServiceBarang> findServicesNotif();

    @Query(value = "SELECT * FROM service WHERE taken = 'Y' AND DATE(tgl_masuk) BETWEEN :tglAwal AND :tglAkhir ORDER BY tgl_ambil DESC LIMIT 100", nativeQuery = true)
    List<ServiceBarang> findTakenServices(@Param("tglAwal") String tglAwal, @Param("tglAkhir") String tglAkhir);

    @Query("SELECT s, (SELECT st.status FROM Status st WHERE st.service = s.idTT AND st.id = (SELECT MAX(st2.id) FROM Status st2 WHERE st2.service = s.idTT)) AS sts " +
            "FROM ServiceBarang s " +
            "WHERE s.taken = 'N' AND s.statusEnd LIKE %:statusEnd% " +
            "ORDER BY s.tanggalAmbil DESC")
    List<ServiceBarang> findServiceCancel(@Param("statusEnd") String statusEnd);

    @Query("SELECT s FROM ServiceBarang s " +
            "WHERE s.taken = 'N' AND s.statusEnd LIKE %:statusEnd% " +
            "AND s.tanggalMasuk >= :awal AND s.tanggalMasuk <= :akhir")
    List<ServiceBarang> findServiceCancelByDate(@Param("statusEnd") String statusEnd,
                                                @Param("awal") Date awal,
                                                @Param("akhir") Date akhir);

    @Query("SELECT s FROM ServiceBarang s WHERE s.teknisi.id = ?1 AND s.taken != 'Y' AND s.statusEnd NOT LIKE '%CANCEL%' ORDER BY CASE WHEN s.statusEnd = 'N_A' THEN 1 WHEN s.statusEnd LIKE '%PROSES%' THEN 2 WHEN s.statusEnd LIKE '%READY%' THEN 3 ELSE 4 END DESC")
    List<ServiceBarang> findMyServices(Long teknisiId);

    @Query("SELECT s FROM ServiceBarang s WHERE s.teknisi.id = ?1 AND s.taken = 'N' AND s.statusEnd LIKE '%CANCEL%'")
    List<ServiceBarang> findMyServicesRetur(Long teknisiId);

    @Query("SELECT s FROM ServiceBarang s WHERE s.taken = 'Y' ORDER BY s.tanggalAmbil DESC")
    List<ServiceBarang> findServiceTaken();

    @Query("SELECT COUNT(s) FROM ServiceBarang s")
    long countAllServices();


    @Query("SELECT s FROM ServiceBarang s WHERE (LOWER(s.taken) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND s.taken = 'N'")
    Page<ServiceBarang> findAllByKeywordAndTaken(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM ServiceBarang s WHERE s.taken = 'N'")
    Page<ServiceBarang> findAllByTaken(Pageable pageable);

    @Query("SELECT s FROM ServiceBarang s WHERE s.idTT = :idTT AND s.taken = 'N'")
    Optional<ServiceBarang> findByIdAndTaken(@Param("idTT") Long id);

    @Query("SELECT s, (SELECT st.status FROM Status st WHERE st.service = s.idTT AND st.id = (SELECT MAX(st2.id) FROM Status st2 WHERE st2.service = s.idTT)) AS sts " +
            "FROM ServiceBarang s " +
            "WHERE s.taken = 'Y' " +
            "ORDER BY s.tanggalAmbil DESC")
    List<ServiceBarang> findServiceTakenPimpinan();

    @Query("SELECT s FROM ServiceBarang s WHERE s.taken = 'Y' AND s.tanggalMasuk >= :awal AND s.tanggalMasuk <= :akhir")
    List<ServiceBarang> findServiceTakenByDateRange(@Param("awal") Date awal, @Param("akhir") Date akhir);


    @Query("SELECT sb, " +
            "(SELECT st.status FROM Status st WHERE st.service = sb AND st.id = " +
            "(SELECT MAX(st2.id) FROM Status st2 WHERE st2.service = sb)) AS sts " +
            "FROM ServiceBarang sb WHERE sb.taken = 'Y' ORDER BY sb.tanggalAmbil DESC")
    List<Object[]> findServiceBarangTaken();

    @Query("SELECT sb FROM ServiceBarang sb WHERE sb.taken = 'Y' AND sb.tanggalMasuk BETWEEN :awal AND :akhir")
    List<ServiceBarang> findServiceBarangTakenBetweenDates(@Param("awal") Date awal, @Param("akhir") Date akhir);

    @Query("SELECT sb FROM ServiceBarang sb WHERE " +
            "((sb.statusEnd = 'READY_S' OR sb.statusEnd = 'READY_T') OR sb.statusEnd = :status) " +
            "AND sb.tanggalMasuk BETWEEN :awal AND :akhir")
    List<ServiceBarang> findByStatusEndAndTanggalMasukBetween(@Param("status") String status, @Param("awal") Date awal, @Param("akhir") Date akhir);

    @Query("SELECT sb FROM ServiceBarang sb WHERE (sb.statusEnd = 'READY_S' OR sb.statusEnd = 'READY_T') OR sb.statusEnd = :status")
    List<ServiceBarang> findByStatusEnd(@Param("status") String status);

    List<ServiceBarang> findByTanggalMasukBetween(Date awal, Date akhir);

    @Query("SELECT sb, " +
            "(SELECT st.status FROM Status st WHERE st.service = sb AND st.id = " +
            "(SELECT MAX(st2.id) FROM Status st2 WHERE st2.service = sb)) AS sts " +
            "FROM ServiceBarang sb WHERE sb.taken != 'Y' " +
            "ORDER BY CASE " +
            "WHEN sb.statusEnd LIKE '%READY%' THEN 1 " +
            "WHEN sb.statusEnd LIKE '%PROSES%' THEN 2 " +
            "WHEN sb.statusEnd = 'N_A' THEN 3 " +
            "WHEN sb.statusEnd LIKE '%CANCEL%' THEN 4 " +
            "ELSE 5 END DESC")
    List<Object[]> findServiceBarang();

}

