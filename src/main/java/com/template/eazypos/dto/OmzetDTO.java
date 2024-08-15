package com.template.eazypos.dto;

import java.util.Date;

public class OmzetDTO {
    private Long id_salesman;

    private Long id_customer;
    private Double omzet;
    private Date tgl;

    public Long getId_salesman() {
        return id_salesman;
    }

    public void setId_salesman(Long id_salesman) {
        this.id_salesman = id_salesman;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public Double getOmzet() {
        return omzet;
    }

    public void setOmzet(Double omzet) {
        this.omzet = omzet;
    }

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }
}
