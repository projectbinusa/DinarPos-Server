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
    private ServiceBarang service;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_teknisi", nullable = false, updatable = false)
    private Teknisi id_tekinisi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_take", nullable = false, updatable = false)
    private Teknisi id_take;

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


    public ServiceBarang getService() {
        return service;
    }

    public void setService(ServiceBarang service) {
        this.service = service;
    }

    public Teknisi getId_tekinisi() {
        return id_tekinisi;
    }

    public void setId_tekinisi(Teknisi id_tekinisi) {
        this.id_tekinisi = id_tekinisi;
    }

    public Teknisi getId_take() {
        return id_take;
    }

    public void setId_take(Teknisi id_take) {
        this.id_take = id_take;
    }
}
