package com.template.eazypos.dto;

import javax.persistence.Lob;
import java.util.Date;

public class AddServiceDTO {
    private Long id_customer;

    @Lob
    private String ket;

    private String jenis_produk;

    private String merek;

    private String type;

    private String no_seri;

    @Lob
    private String perlengkapan;

    @Lob
    private String keluhan;

    private String penerima;

    private Date tanggal_masuk;

    private Integer biaya_maximal;

    private Integer estimasi_biaya;

    private String checker;

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public String getJenis_produk() {
        return jenis_produk;
    }

    public void setJenis_produk(String jenis_produk) {
        this.jenis_produk = jenis_produk;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo_seri() {
        return no_seri;
    }

    public void setNo_seri(String no_seri) {
        this.no_seri = no_seri;
    }

    public String getPerlengkapan() {
        return perlengkapan;
    }

    public void setPerlengkapan(String perlengkapan) {
        this.perlengkapan = perlengkapan;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public Date getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(Date tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public Integer getBiaya_maximal() {
        return biaya_maximal;
    }

    public void setBiaya_maximal(Integer biaya_maximal) {
        this.biaya_maximal = biaya_maximal;
    }

    public Integer getEstimasi_biaya() {
        return estimasi_biaya;
    }

    public void setEstimasi_biaya(Integer estimasi_biaya) {
        this.estimasi_biaya = estimasi_biaya;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }
}
