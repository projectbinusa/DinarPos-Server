package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "planning")
public class Planning extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long idPlan;

//    @Column(name = "id_marketting", nullable = false)
//    private Long idMarketting;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_salesman", nullable = false, updatable = false)
    private Salesman salesman;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_customer", nullable = false, updatable = false)
    private Customer customer;
    @Column(name = "tgl", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/Jakarta")
    private Date tgl;

    @Column(name = "bertemu", nullable = false, length = 50)
    private String bertemu;

    @Column(name = "ket", columnDefinition = "TEXT", nullable = false)
    private String ket;

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp() ON UPDATE current_timestamp()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date timestamp;

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public String getBertemu() {
        return bertemu;
    }

    public void setBertemu(String bertemu) {
        this.bertemu = bertemu;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
