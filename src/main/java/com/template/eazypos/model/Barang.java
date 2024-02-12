package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_barang")
public class Barang extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_barang")
    private Long idBarang;

//    @Column(name = "id_suplier")
//    private Long idSuplier;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_suplier", nullable = false, updatable = false)
    private Suplier suplier;

    @Column(name = "barcode_barang", nullable = false, length = 100)
    private String barcodeBarang;

    @Column(name = "nama_barang", nullable = false, length = 200)
    private String namaBarang;

    @Column(name = "unit", nullable = false, length = 50)
    private String unit;

    @Column(name = "harga_beli", nullable = false, length = 50)
    private String hargaBeli;

    @Column(name = "harga_barang", length = 50)
    private String hargaBarang;

    @Column(name = "cash_credit", length = 25)
    private String cashCredit;

    @Column(name = "jumlah_stok", nullable = false)
    private int jumlahStok;



    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(Long idBarang) {
        this.idBarang = idBarang;
    }

//    public Long getIdSuplier() {
//        return idSuplier;
//    }
//
//    public void setIdSuplier(Long idSuplier) {
//        this.idSuplier = idSuplier;
//    }


    public Suplier getSuplier() {
        return suplier;
    }

    public void setSuplier(Suplier suplier) {
        this.suplier = suplier;
    }

    public String getBarcodeBarang() {
        return barcodeBarang;
    }

    public void setBarcodeBarang(String barcodeBarang) {
        this.barcodeBarang = barcodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getCashCredit() {
        return cashCredit;
    }

    public void setCashCredit(String cashCredit) {
        this.cashCredit = cashCredit;
    }

    public int getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(int jumlahStok) {
        this.jumlahStok = jumlahStok;
    }


    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}