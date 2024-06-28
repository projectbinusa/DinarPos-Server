package com.template.eazypos.dto;

import java.util.Date;

public class UpdateBonBarangDTO {
    private Date tgl_kembali;

    private String status_service;

    public Date getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(Date tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getStatus_service() {
        return status_service;
    }

    public void setStatus_service(String status_service) {
        this.status_service = status_service;
    }
}
