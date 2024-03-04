package com.template.eazypos.repository;

import com.template.eazypos.model.Suplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuplierRepository extends JpaRepository<Suplier , Long> {
    @Query(value = "SELECT * FROM tabel_suplier WHERE del_flag = 1  ", nativeQuery = true)
    List<Suplier> findAllSuplier();
}
