package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tabel_stok_akhir")
public class StokAkhir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stok_akhir")
    private Long idStokAkhir;

    @Column(name = "barcode_barang")
    private String barcodeBarang;

    @Column(name = "tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggal;

    public Long getIdStokAkhir() {
        return idStokAkhir;
    }

    public void setIdStokAkhir(Long idStokAkhir) {
        this.idStokAkhir = idStokAkhir;
    }

    public String getBarcodeBarang() {
        return barcodeBarang;
    }

    public void setBarcodeBarang(String barcodeBarang) {
        this.barcodeBarang = barcodeBarang;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}
