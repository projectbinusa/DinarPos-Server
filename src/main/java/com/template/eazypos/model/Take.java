package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
@Entity
@Table(name = "take")
public class Take extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_tt", nullable = false, length = 10)
//    private String idTT;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt", nullable = false, updatable = false)
    private Service service;

    @Column(name = "id_teknisi", nullable = false, length = 10)
    private String idTeknisi;

    @Column(name = "id_take", nullable = false, length = 10)
    private String idTake;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getIdTT() {
//        return idTT;
//    }
//
//    public void setIdTT(String idTT) {
//        this.idTT = idTT;
//    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getIdTeknisi() {
        return idTeknisi;
    }

    public void setIdTeknisi(String idTeknisi) {
        this.idTeknisi = idTeknisi;
    }

    public String getIdTake() {
        return idTake;
    }

    public void setIdTake(String idTake) {
        this.idTake = idTake;
    }
}
