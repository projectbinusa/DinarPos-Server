package com.template.eazypos.repository;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BarangRepository extends JpaRepository<Barang,Long> {
    @Query(value = "SELECT * FROM tabel_barang WHERE barcode_barang = :barcode ", nativeQuery = true)
    Barang findByBarcode(String barcode);
    @Query(value = "SELECT * FROM tabel_barang WHERE del_flag = 1  ", nativeQuery = true)
    List<Barang> findAllBarang();

    @Query("SELECT b FROM Barang b WHERE LOWER(b.barcodeBarang) LIKE LOWER(concat('%', :keyword, '%'))")
    Page<Barang> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
