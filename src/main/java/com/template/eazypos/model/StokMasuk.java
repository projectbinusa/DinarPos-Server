package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_stok_masuk")
public class StokMasuk extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stok_masuk")
    private Long idStokMasuk;

//    @Column(name = "id_suplier", nullable = false)
//    private Long idSuplier;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_suplier", nullable = false, updatable = false)
    private Suplier suplier;

//    @Column(name = "id_barang", nullable = false)
//    private Long idBarang;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_barang", nullable = false, updatable = false)
    private Barang barang;

    @Column(name = "jumlah_stok", nullable = false, length = 10)
    private String jumlahStok;

    @Column(name = "keterangan_stok_masuk", nullable = false, length = 255)
    private String keteranganStokMasuk;

    @Column(name = "c_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cDate;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdStokMasuk() {
        return idStokMasuk;
    }

    public void setIdStokMasuk(Long idStokMasuk) {
        this.idStokMasuk = idStokMasuk;
    }

//    public Long getIdSuplier() {
//        return idSuplier;
//    }
//
//    public void setIdSuplier(Long idSuplier) {
//        this.idSuplier = idSuplier;
//    }

//    public Long getIdBarang() {
//        return idBarang;
//    }
//
//    public void setIdBarang(Long idBarang) {
//        this.idBarang = idBarang;
//    }

    public String getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(String jumlahStok) {
        this.jumlahStok = jumlahStok;
    }

    public String getKeteranganStokMasuk() {
        return keteranganStokMasuk;
    }

    public void setKeteranganStokMasuk(String keteranganStokMasuk) {
        this.keteranganStokMasuk = keteranganStokMasuk;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
