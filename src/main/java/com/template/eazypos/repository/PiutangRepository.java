package com.template.eazypos.repository;

import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.Piutang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PiutangRepository extends JpaRepository<Piutang , Long> {
    @Query(value = "SELECT * FROM piutang WHERE  pelunasan != 0 ", nativeQuery = true)
    List<Piutang> findAllPiutang();
}
