package com.template.eazypos.dto;

public class TakenServiceDTO {
    Long id_teknisi;

    String solusi;

    String status;

    String ket;

    String type;

    public Long getId_teknisi() {
        return id_teknisi;
    }

    public void setId_teknisi(Long id_teknisi) {
        this.id_teknisi = id_teknisi;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
