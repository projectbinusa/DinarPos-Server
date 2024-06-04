package com.template.eazypos.repository;

import com.template.eazypos.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status , Long> {
    @Query(value = "SELECT * FROM status WHERE id_tt = :id ", nativeQuery = true)
    List<Status> findByIdTT(Long id);
}
