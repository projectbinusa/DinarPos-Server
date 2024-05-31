package com.template.eazypos.dto;

public class EditBiayaServiceDTO {

    public int biaya_service;

    public int biaya_sparepart;

    public int total;

    public int getBiaya_service() {
        return biaya_service;
    }

    public void setBiaya_service(int biaya_service) {
        this.biaya_service = biaya_service;
    }

    public int getBiaya_sparepart() {
        return biaya_sparepart;
    }

    public void setBiaya_sparepart(int biaya_sparepart) {
        this.biaya_sparepart = biaya_sparepart;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
