package com.template.eazypos.dto;

import java.util.Date;

public class UpdateDataBonBarangDTO {

    private Date tgl_ambil;

    private Long id_teknisi;

    private Long id_tt;
    private String barcode_brg;

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
}
