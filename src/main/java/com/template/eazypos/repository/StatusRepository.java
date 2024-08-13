package com.template.eazypos.repository;

import com.template.eazypos.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status , Long> {
    @Query(value = "SELECT * FROM status WHERE id_tt = :id ", nativeQuery = true)
    List<Status> findByIdTT(Long id);

    @Query("SELECT st FROM Status st WHERE st.service = :idTt")
    List<Status> findStatusByIdTt(@Param("idTt") Long idTt);

    @Query(value = "SELECT s.*, t.nama AS teknisi_nama, srv.keluhan AS service_keluhan, srv.tgl_masuk AS service_tgl_masuk, srv.nama AS service_nama_customer " +
            "FROM status s " +
            "JOIN teknisi t ON s.id_teknisi = t.id_teknisi " +
            "JOIN service srv ON s.id_tt = srv.id_tt " +
            "WHERE DATE(srv.tgl_masuk) BETWEEN :awal AND :akhir " +
            "ORDER BY s.id_tt ASC",
            nativeQuery = true)
    List<Object[]> laporanStatusService(@Param("awal") Date awal, @Param("akhir") Date akhir);
}
