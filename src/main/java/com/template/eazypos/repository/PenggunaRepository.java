package com.template.eazypos.repository;

import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PenggunaRepository extends JpaRepository<Pengguna , Long> {
    @Query(value = "SELECT * FROM tabel_pengguna WHERE username_pengguna = :username ", nativeQuery = true)
    Optional<Pengguna> findByUsername(String username);
    @Query(value = "SELECT * FROM tabel_pengguna WHERE username_pengguna = :username ", nativeQuery = true)
    Boolean existsByUsername(String username);
}
