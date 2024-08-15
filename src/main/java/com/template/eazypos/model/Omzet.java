package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "omzet")
public class Omzet extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_marketting", nullable = false)
//    private Long idMarketting;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_salesman", nullable = false, updatable = false)
    private Salesman salesman;

    @Column(name = "tgl", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tgl;

    @Column(name = "omzet", nullable = false)
    private Double omzet;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_customer", nullable = false, updatable = false)
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_transaksi", updatable = false)
    private Transaksi transaksi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getIdMarketting() {
//        return idMarketting;
//    }
//
//    public void setIdMarketting(Long idMarketting) {
//        this.idMarketting = idMarketting;
//    }


    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public Double getOmzet() {
        return omzet;
    }

    public void setOmzet(Double omzet) {
        this.omzet = omzet;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }
}
