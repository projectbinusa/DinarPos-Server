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

    // Finds the first record after the specified date for the given barcode
    @Query("SELECT p FROM PersediaanBarang p WHERE p.barang.barcodeBarang = :barcode AND p.tanggal > :date ORDER BY p.tanggal ASC")
    List<PersediaanBarang> findFirstAfterDate(@Param("date") Date date, @Param("barcode") String barcode);

    // Finds the last record before the specified date for the given barcode
    @Query("SELECT p FROM PersediaanBarang p WHERE p.barang.barcodeBarang = :barcode AND p.tanggal <= :date ORDER BY p.tanggal DESC")
    List<PersediaanBarang> findLastBeforeDate(@Param("date") Date date, @Param("barcode") String barcode);


    @Query("SELECT pb FROM PersediaanBarang pb WHERE pb.barang.barcodeBarang = :barcode AND pb.tanggal <= :date ORDER BY pb.tanggal DESC")
    List<PersediaanBarang> findLatestBeforeDate(@Param("barcode") String barcode, @Param("date") Date date);

    @Query("SELECT pb FROM PersediaanBarang pb WHERE pb.barang.barcodeBarang = :barcode AND pb.tanggal > :date ORDER BY pb.tanggal DESC")
    List<PersediaanBarang> findFirstAfterDate(@Param("barcode") String barcode, @Param("date") Date date);
    @Query(value = "SELECT * FROM tabel_persediaan_barang WHERE id_barcode_barang = :id", nativeQuery = true)
    List<PersediaanBarang> findByIdTransaksi(Long id);
}
