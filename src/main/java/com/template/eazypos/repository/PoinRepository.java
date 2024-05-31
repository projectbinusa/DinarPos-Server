package com.template.eazypos.repository;

import com.template.eazypos.model.Poin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PoinRepository extends JpaRepository<Poin , Long> {
    @Query(value = "SELECT * FROM poin WHERE id_teknisi = :id" , nativeQuery = true)
    Optional<Poin> findByIdTeknisi(Long id);
}
