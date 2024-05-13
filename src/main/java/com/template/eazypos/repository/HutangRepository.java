package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Hutang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HutangRepository extends JpaRepository<Hutang , Long> {
    @Query(value = "SELECT * FROM hutang WHERE  pelunasan != 0 ", nativeQuery = true)
    List<Hutang> findAllHutang();
}
