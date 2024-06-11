package com.template.eazypos.repository;

import com.template.eazypos.model.Teknisi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeknisiRepository extends JpaRepository<Teknisi ,Long> {
    @Query(value = "SELECT * FROM teknisi WHERE nama = :username ", nativeQuery = true)
    Optional<Teknisi> findByNama(String username);

    @Query(value = "SELECT * FROM teknisi WHERE LOWER(nama) LIKE LOWER(concat('%', :keyword, '%'))", nativeQuery = true)
    Page<Teknisi> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
