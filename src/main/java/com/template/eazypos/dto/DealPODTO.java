package com.template.eazypos.dto;

import java.util.Date;

public class DealPODTO {
    private Long id_kunjungan;

    private String ket;

    private String administrasi;

    public Long getId_kunjungan() {
        return id_kunjungan;
    }

    public void setId_kunjungan(Long id_kunjungan) {
        this.id_kunjungan = id_kunjungan;
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
