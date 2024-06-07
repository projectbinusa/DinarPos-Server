package com.template.eazypos.dto;

public class ServiceDataDTO {
    private String nama;
    private Long idTeknisi;
    private Long ttl;
    private Long success;
    private Long nots;

    // Constructors, Getters, and Setters
    public ServiceDataDTO(String nama, Long idTeknisi, Long ttl, Long success, Long nots) {
        this.nama = nama;
        this.idTeknisi = idTeknisi;
        this.ttl = ttl;
        this.success = success;
        this.nots = nots;
    }

    // Getters and Setters
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getIdTeknisi() {
        return idTeknisi;
    }

    public void setIdTeknisi(Long idTeknisi) {
        this.idTeknisi = idTeknisi;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getNots() {
        return nots;
    }

    public void setNots(Long nots) {
        this.nots = nots;
    }
}
