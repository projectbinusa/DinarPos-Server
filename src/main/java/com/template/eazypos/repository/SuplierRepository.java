package com.template.eazypos.repository;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Suplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuplierRepository extends JpaRepository<Suplier, Long> {
    @Query(value = "SELECT * FROM tabel_suplier WHERE del_flag = 1  ", nativeQuery = true)
    List<Suplier> findAllSuplier();

    @Query(value = "SELECT * FROM tabel_suplier WHERE kode_suplier = :code ", nativeQuery = true)
      Optional<Suplier> findByCode(String code);

    @Query(value = "SELECT * FROM tabel_suplier  WHERE LOWER(kode_suplier) LIKE LOWER(concat('%', :keyword, '%'))" , nativeQuery = true)
    Page<Suplier> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
