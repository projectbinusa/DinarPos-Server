package com.template.eazypos.repository;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BarangRepository extends JpaRepository<Barang,Long> {
    @Query(value = "SELECT * FROM tabel_barang WHERE barcode_barang = :barcode ", nativeQuery = true)
    Barang findByBarcode(String barcode);
}
