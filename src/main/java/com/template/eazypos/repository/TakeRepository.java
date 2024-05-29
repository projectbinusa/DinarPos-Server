package com.template.eazypos.repository;

import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.model.Take;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.file.LinkOption;
import java.util.List;
import java.util.Optional;

public interface TakeRepository extends JpaRepository<Take , Long> {
    @Query(value = "SELECT * FROM take WHERE id_tt = :id" , nativeQuery = true)
    List<Take> findByIdTT (Long id);
}
