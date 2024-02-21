package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_barang_transaksi")
public class BarangTransaksi extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_brg_transaksi")
    private Long idBrgTransaksi;

//    @Column(name = "id_transaksi", nullable = false)
//    private Long idTransaksi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_transaksi", nullable = false, updatable = false)
    private Transaksi transaksi;

    @Column(name = "status", nullable = false, length = 50, columnDefinition = "varchar(50) default 'excelcom'")
    private String status;

    @Column(name = "barcode_barang", nullable = false, length = 50)
    private String barcodeBarang;

    @Column(name = "harga_brng", nullable = false)
    private int hargaBrng;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "total_harga_barang", nullable = false)
    private int totalHargaBarang;

    @Column(name = "diskon", nullable = false)
    private int diskon;

    @Column(name = "total_harga", nullable = false)
    private int totalHarga;

    @Column(name = "hemat", nullable = false, length = 50)
    private String hemat;

    @Column(name = "tanggal", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    @Column(name = "7_hari", nullable = false, columnDefinition = "int default 1")
    private int hari7;

    @Column(name = "30_hari", nullable = false, columnDefinition = "int default 1")
    private int hari30;

    @Column(name = "90_hari", nullable = false, columnDefinition = "int default 1")
    private int hari90;

    @Column(name = "120_hari", nullable = false, columnDefinition = "int default 1")
    private int hari120;

    @Column(name = "367_hari", nullable = false, columnDefinition = "int default 1")
    private int hari367;


    public Long getIdBrgTransaksi() {
        return idBrgTransaksi;
    }

    public void setIdBrgTransaksi(Long idBrgTransaksi) {
        this.idBrgTransaksi = idBrgTransaksi;
    }

//    public Long getIdTransaksi() {
//        return idTransaksi;
//    }
//
//    public void setIdTransaksi(Long idTransaksi) {
//        this.idTransaksi = idTransaksi;
//    }



    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcodeBarang() {
        return barcodeBarang;
    }

    public void setBarcodeBarang(String barcodeBarang) {
        this.barcodeBarang = barcodeBarang;
    }

    public int getHargaBrng() {
        return hargaBrng;
    }

    public void setHargaBrng(int hargaBrng) {
        this.hargaBrng = hargaBrng;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotalHargaBarang() {
        return totalHargaBarang;
    }

    public void setTotalHargaBarang(int totalHargaBarang) {
        this.totalHargaBarang = totalHargaBarang;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getHemat() {
        return hemat;
    }

    public void setHemat(String hemat) {
        this.hemat = hemat;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getHari7() {
        return hari7;
    }

    public void setHari7(int hari7) {
        this.hari7 = hari7;
    }

    public int getHari30() {
        return hari30;
    }

    public void setHari30(int hari30) {
        this.hari30 = hari30;
    }

    public int getHari90() {
        return hari90;
    }

    public void setHari90(int hari90) {
        this.hari90 = hari90;
    }

    public int getHari120() {
        return hari120;
    }

    public void setHari120(int hari120) {
        this.hari120 = hari120;
    }

    public int getHari367() {
        return hari367;
    }

    public void setHari367(int hari367) {
        this.hari367 = hari367;
    }
}