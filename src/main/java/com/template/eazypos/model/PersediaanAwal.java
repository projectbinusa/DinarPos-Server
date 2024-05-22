package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persediaan_awal")
public class PersediaanAwal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persediaan_awal")
    private Long id;

    @Column(name = "nominal")
    private String nominal;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_transaksi_jual",  updatable = false)
    private Transaksi transaksi;

    @Column(name = "tanggal", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}
