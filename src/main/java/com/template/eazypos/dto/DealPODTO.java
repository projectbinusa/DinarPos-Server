package com.template.eazypos.dto;

import java.util.Date;

public class DealPODTO {
    private Long id_kunjungan;

    private Date tgl_input;

    private String ket;

    private String administrasi;

    public Long getId_kunjungan() {
        return id_kunjungan;
    }

    public void setId_kunjungan(Long id_kunjungan) {
        this.id_kunjungan = id_kunjungan;
    }

    public Date getTgl_input() {
        return tgl_input;
    }

    public void setTgl_input(Date tgl_input) {
        this.tgl_input = tgl_input;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getAdministrasi() {
        return administrasi;
    }

    public void setAdministrasi(String administrasi) {
        this.administrasi = administrasi;
    }
}
