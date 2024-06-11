package com.template.eazypos.dto;

import java.util.Date;

public class BonBarangDTO {
    public Long id_teknisi;

    public Long id_tt;

    public String barcode_brg;

    public Date tanggal_ambil;

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

    public Date getTanggal_ambil() {
        return tanggal_ambil;
    }

    public void setTanggal_ambil(Date tanggal_ambil) {
        this.tanggal_ambil = tanggal_ambil;
    }
}
