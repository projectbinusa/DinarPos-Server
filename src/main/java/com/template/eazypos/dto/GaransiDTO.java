package com.template.eazypos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class GaransiDTO {
    private Long id_tt;

    private String namaBrg;

    private String merek;

    private String masukKe;

    private String kerusakan;

    private Date tanggalMasuk;

    public String getNamaBrg() {
        return namaBrg;
    }

    public void setNamaBrg(String namaBrg) {
        this.namaBrg = namaBrg;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getMasukKe() {
        return masukKe;
    }

    public void setMasukKe(String masukKe) {
        this.masukKe = masukKe;
    }

    public String getKerusakan() {
        return kerusakan;
    }

    public void setKerusakan(String kerusakan) {
        this.kerusakan = kerusakan;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public Long getId_tt() {
        return id_tt;
    }

    public void setId_tt(Long id_tt) {
        this.id_tt = id_tt;
    }


}
