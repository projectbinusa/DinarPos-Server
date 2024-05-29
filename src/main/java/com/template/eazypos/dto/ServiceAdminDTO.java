package com.template.eazypos.dto;

import java.util.Date;

public class ServiceAdminDTO {
    public int biaya_sparepart;

    public int biaya_service;

    public int total;

    public String status;

    public int getBiaya_sparepart() {
        return biaya_sparepart;
    }

    public void setBiaya_sparepart(int biaya_sparepart) {
        this.biaya_sparepart = biaya_sparepart;
    }

    public int getBiaya_service() {
        return biaya_service;
    }

    public void setBiaya_service(int biaya_service) {
        this.biaya_service = biaya_service;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
