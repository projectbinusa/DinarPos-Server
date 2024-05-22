package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bon_barang")
public class BonBarang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_teknisi", nullable = false, updatable = false)
    private Teknisi teknisi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt", nullable = false, updatable = false)
    private ServiceBarang serviceBarang;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_barang", nullable = false, updatable = false)
    private Barang barang;

    @Column(name = "tgl_ambil", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tgl_ambil;

    @Column(name = "tgl_kembalikan", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tgl_kembalikan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teknisi getTeknisi() {
        return teknisi;
    }

    public void setTeknisi(Teknisi teknisi) {
        this.teknisi = teknisi;
    }

    public ServiceBarang getServiceBarang() {
        return serviceBarang;
    }

    public void setServiceBarang(ServiceBarang serviceBarang) {
        this.serviceBarang = serviceBarang;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Date getTgl_ambil() {
        return tgl_ambil;
    }

    public void setTgl_ambil(Date tgl_ambil) {
        this.tgl_ambil = tgl_ambil;
    }

    public Date getTgl_kembalikan() {
        return tgl_kembalikan;
    }

    public void setTgl_kembalikan(Date tgl_kembalikan) {
        this.tgl_kembalikan = tgl_kembalikan;
    }
}
