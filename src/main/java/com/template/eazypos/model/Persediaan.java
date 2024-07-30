package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persediaan")
public class Persediaan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "persediaan_awal")
    private String persediaanAwal;
    @Column(name = "persediaan_akhir")
    private String persediaanAkhir;

    @Column(name = "pembelian")
    private String pembelian;

    @Column(name = "barang_siap_jual")
    private String barangSiapJual;

    @Column(name = "penjualan")
    private String penjualan;

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/Jakarta")
    private Date date;

    public Persediaan(long persediaanAkhirToAwal, Date date) {
    }

    public Persediaan() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersediaanAwal() {
        return persediaanAwal;
    }

    public void setPersediaanAwal(String persediaanAwal) {
        this.persediaanAwal = persediaanAwal;
    }

    public String getPersediaanAkhir() {
        return persediaanAkhir;
    }

    public void setPersediaanAkhir(String persediaanAkhir) {
        this.persediaanAkhir = persediaanAkhir;
    }

    public String getPembelian() {
        return pembelian;
    }

    public void setPembelian(String pembelian) {
        this.pembelian = pembelian;
    }

    public String getBarangSiapJual() {
        return barangSiapJual;
    }

    public void setBarangSiapJual(String barangSiapJual) {
        this.barangSiapJual = barangSiapJual;
    }

    public String getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(String penjualan) {
        this.penjualan = penjualan;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
