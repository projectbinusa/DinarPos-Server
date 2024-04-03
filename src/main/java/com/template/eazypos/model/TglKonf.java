package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tgl_konf")
public class TglKonf extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_tt", nullable = false)
//    private Long idTT;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt", nullable = false, updatable = false)
    private ServiceBarang service;

    @Column(name = "tgl_konf", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tglKonf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getIdTT() {
//        return idTT;
//    }
//
//    public void setIdTT(Long idTT) {
//        this.idTT = idTT;
//    }


    public ServiceBarang getService() {
        return service;
    }

    public void setService(ServiceBarang service) {
        this.service = service;
    }

    public Date getTglKonf() {
        return tglKonf;
    }

    public void setTglKonf(Date tglKonf) {
        this.tglKonf = tglKonf;
    }
}