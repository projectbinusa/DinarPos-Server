package com.template.eazypos.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IjinDTO {
    private Long id_salesman;

    private String jenis;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tgl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date s_d_tgl;

    private String ket;

    public Long getId_salesman() {
        return id_salesman;
    }

    public void setId_salesman(Long id_salesman) {
        this.id_salesman = id_salesman;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public Date getS_d_tgl() {
        return s_d_tgl;
    }

    public void setS_d_tgl(Date s_d_tgl) {
        this.s_d_tgl = s_d_tgl;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }
}
