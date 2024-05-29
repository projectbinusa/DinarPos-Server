package com.template.eazypos.dto;


import java.util.Date;

public class KonfirmDTO {
    public Date date;

    public Long id_service;

    public Long getId_service() {
        return id_service;
    }

    public void setId_service(Long id_service) {
        this.id_service = id_service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
