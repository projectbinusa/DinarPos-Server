package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "garansi")
public class Garansi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_garansi")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt", updatable = false)
    private ServiceBarang serviceBarang;

    @Column(name = "nama_brg")
    private String namaBrg;

    @Column(name = "merek")
    private String merek;

    @Column(name = "masuk_ke")
    private String masukKe;

    @Column(name = "kerusakan")
    private String kerusakan;

    @Column(name = "tgl_masuk")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalMasuk;

    @Column(name ="tgl_jadi")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalJadi;

    // getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaBrg() {
        return namaBrg;
    }

    public void setNamaBrg(String namaBrg) {
        this.namaBrg = namaBrg;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getMasukKe() {
        return masukKe;
    }

    public void setMasukKe(String masukKe) {
        this.masukKe = masukKe;
    }

    public String getKerusakan() {
        return kerusakan;
    }

    public void setKerusakan(String kerusakan) {
        this.kerusakan = kerusakan;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public Date getTanggalJadi() {
        return tanggalJadi;
    }

    public void setTanggalJadi(Date tanggalJadi) {
        this.tanggalJadi = tanggalJadi;
    }

    public ServiceBarang getServiceBarang() {
        return serviceBarang;
    }

    public void setServiceBarang(ServiceBarang serviceBarang) {
        this.serviceBarang = serviceBarang;
    }
}
