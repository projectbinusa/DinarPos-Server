package com.template.eazypos.repository;

import com.template.eazypos.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarangRepository extends JpaRepository<Barang,Long> {
}
