package com.template.eazypos.repository;

import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.Teknisi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeknisiRepository extends JpaRepository<Teknisi ,Long> {
    @Query(value = "SELECT * FROM teknisi WHERE nama = :username ", nativeQuery = true)
    Optional<Teknisi> findByNama(String username);
}
