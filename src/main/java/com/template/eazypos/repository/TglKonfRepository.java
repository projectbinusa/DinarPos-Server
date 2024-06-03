package com.template.eazypos.repository;

import com.template.eazypos.model.TglKonf;
import com.template.eazypos.model.TransaksiBeli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TglKonfRepository extends JpaRepository<TglKonf , Long> {
    @Query(value = "SELECT * FROM tgl_konf WHERE id_tt = :id ", nativeQuery = true)
    List<TglKonf> findByIdTT(Long id);
}
