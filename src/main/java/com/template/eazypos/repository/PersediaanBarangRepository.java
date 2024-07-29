package com.template.eazypos.repository;

import com.template.eazypos.model.PersediaanBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersediaanBarangRepository extends JpaRepository<PersediaanBarang , Long> {
    @Query("SELECT pb FROM PersediaanBarang pb WHERE pb.tanggal = :tanggal AND pb.barang.barcodeBarang = :barcode")
    List<PersediaanBarang> findByTanggalAndBarangBarcode(@Param("tanggal") Date tanggal, @Param("barcode") String barcode);

    @Query("SELECT pb FROM PersediaanBarang pb WHERE pb.tanggal > :tanggal AND pb.barang.barcodeBarang = :barcode ORDER BY pb.tanggal DESC")
    Optional<PersediaanBarang> findFirstByTanggalAfterAndBarangBarcodeOrderByTanggalDesc(@Param("tanggal") Date tanggal, @Param("barcode") String barcode);

    @Query("SELECT pb FROM PersediaanBarang pb WHERE pb.tanggal < :tanggal AND pb.barang.barcodeBarang = :barcode ORDER BY pb.tanggal DESC")
    Optional<PersediaanBarang> findFirstByTanggalBeforeAndBarangBarcodeOrderByTanggalDesc(@Param("tanggal") Date tanggal, @Param("barcode") String barcode);
}
