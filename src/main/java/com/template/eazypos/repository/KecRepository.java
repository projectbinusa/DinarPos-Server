package com.template.eazypos.repository;

import com.template.eazypos.model.Kec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KecRepository extends JpaRepository<Kec,Long> {
    @Query(value = "SELECT * FROM kec WHERE id_kabkot = :id" , nativeQuery = true)
    Page<Kec> findByIdKabkot(Long id , Pageable pageable);
}
