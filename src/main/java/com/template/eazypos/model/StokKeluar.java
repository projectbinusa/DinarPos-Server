package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_stok_keluar")
public class StokKeluar extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stok_keluar")
    private Long idStokKeluar;

//    @Column(name = "id_barang", nullable = false)
//    private Long idBarang;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_barang", nullable = false, updatable = false)
    private Barang barang;

    @Column(name = "jumlah_stok", nullable = false, length = 10)
    private String jumlahStok;

    @Column(name = "keterangan_stok_keluar", nullable = false, length = 255)
    private String keteranganStokKeluar;


    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdStokKeluar() {
        return idStokKeluar;
    }

    public void setIdStokKeluar(Long idStokKeluar) {
        this.idStokKeluar = idStokKeluar;
    }

//    public Long getIdBarang() {
//        return idBarang;
//    }
//
//    public void setIdBarang(Long idBarang) {
//        this.idBarang = idBarang;
//    }


    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public String getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(String jumlahStok) {
        this.jumlahStok = jumlahStok;
    }

    public String getKeteranganStokKeluar() {
        return keteranganStokKeluar;
    }

    public void setKeteranganStokKeluar(String keteranganStokKeluar) {
        this.keteranganStokKeluar = keteranganStokKeluar;
    }


    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
