package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "poin")
public class Poin extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
//    @Column(name = "id_teknisi", nullable = false)
//    private Long idTeknisi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_teknisi", nullable = false, updatable = false)
    private Teknisi teknisi;
    @Column(name = "poin", nullable = false)
    private double poin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getIdTeknisi() {
//        return idTeknisi;
//    }
//
//    public void setIdTeknisi(Long idTeknisi) {
//        this.idTeknisi = idTeknisi;
//    }


    public Teknisi getTeknisi() {
        return teknisi;
    }

    public void setTeknisi(Teknisi teknisi) {
        this.teknisi = teknisi;
    }

    public double getPoin() {
        return poin;
    }

    public void setPoin(double poin) {
        this.poin = poin;
    }
}
