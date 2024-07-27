package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_persediaan_barang")
public class PersediaanBarang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_barcode_barang", updatable = false)
    private Barang barang;

    @Column(name = "tanggal")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date tanggal;

    @Column(name = "masuk")
    private String masuk;

    @Column(name = "keluar")
    private String keluar;

    @Column(name = "stok_awal")
    private String stok_awal;

    @Column(name = "stok_keluar")
    private String stok_keluar;

    // getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getMasuk() {
        return masuk;
    }

    public void setMasuk(String masuk) {
        this.masuk = masuk;
    }

    public String getKeluar() {
        return keluar;
    }

    public void setKeluar(String keluar) {
        this.keluar = keluar;
    }

    public String getStok_awal() {
        return stok_awal;
    }

    public void setStok_awal(String stok_awal) {
        this.stok_awal = stok_awal;
    }

    public String getStok_keluar() {
        return stok_keluar;
    }

    public void setStok_keluar(String stok_keluar) {
        this.stok_keluar = stok_keluar;
    }
}
