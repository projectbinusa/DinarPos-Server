package com.template.eazypos.dto;

import java.util.Date;

public class UpdateDataBonBarangDTO {

    private Date tgl_ambil;

    public Long id_teknisi;

    public Long id_tt;

    public String barcode_brg;

    public String status_service;

    public Date getTgl_ambil() {
        return tgl_ambil;
    }

    public void setTgl_ambil(Date tgl_ambil) {
        this.tgl_ambil = tgl_ambil;
    }

    public Long getId_teknisi() {
        return id_teknisi;
    }

    public void setId_teknisi(Long id_teknisi) {
        this.id_teknisi = id_teknisi;
    }

    public Long getId_tt() {
        return id_tt;
    }

    public void setId_tt(Long id_tt) {
        this.id_tt = id_tt;
    }

    public String getBarcode_brg() {
        return barcode_brg;
    }

    public void setBarcode_brg(String barcode_brg) {
        this.barcode_brg = barcode_brg;
    }

    public String getStatus_service() {
        return status_service;
    }

    public void setStatus_service(String status_service) {
        this.status_service = status_service;
    }
}
