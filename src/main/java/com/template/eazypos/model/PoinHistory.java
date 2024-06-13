package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "poin_history")
public class PoinHistory extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_teknisi", nullable = false)
//    private Long idTeknisi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_teknisi", nullable = false, updatable = false)
    private Teknisi teknisi;

    @Column(name = "tgl", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggal;

    @Column(name = "poin", nullable = false)
    private double poin;

    @Column(name = "ket", nullable = false, columnDefinition = "text")
    private String keterangan;

    @Column(name = "nominal", nullable = false)
    private int nominal;


    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

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

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getPoin() {
        return poin;
    }

    public void setPoin(double poin) {
        this.poin = poin;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}