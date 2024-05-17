package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_stok_awal")
public class StokAwal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stok_awal")
    private Long idStokAwal;

    @Column(name = "barcode_barang")
    private String barcodeBarang;

    @Column(name = "qty")
    private String qty;

    @Column(name = "tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggal;

    public Long getIdStokAwal() {
        return idStokAwal;
    }

    public void setIdStokAwal(Long idStokAwal) {
        this.idStokAwal = idStokAwal;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
