package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "tabel_barang_transaksi_beli")
public class BarangTransaksiBeli extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_brg_transaksi_beli")
    private Long idBrgTransaksiBeli;

//    @Column(name = "id_transaksi_beli", nullable = false)
//    private Long idTransaksiBeli;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_transakasi_beli", nullable = false, updatable = false)
    private TransaksiBeli transaksiBeli;

    @Column(name = "status", nullable = false, length = 50, columnDefinition = "varchar(50) default 'excelcom'")
    private String status;

    @Column(name = "barcode_barang", nullable = false, length = 50)
    private String barcodeBarang;

    @Column(name = "unit")
    private String  unit;
    @Column(name = "total_ppn")
    private String  totalPpn;

    @Column(name = "total_dpp")
    private String  totalDpp;

    @Column(name = "dpp")
    private String  dpp;

    @Column(name = "ppn")
    private String  ppn;

    @Column(name = "nama_barang", nullable = false, length = 200)
    private String namaBarang;

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

    @Column(name = "hemat", length = 50)
    private String hemat;

    @Column(name = "tanggal", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggal;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdBrgTransaksiBeli() {
        return idBrgTransaksiBeli;
    }

    public void setIdBrgTransaksiBeli(Long idBrgTransaksiBeli) {
        this.idBrgTransaksiBeli = idBrgTransaksiBeli;
    }

//    public Long getIdTransaksiBeli() {
//        return idTransaksiBeli;
//    }
//
//    public void setIdTransaksiBeli(Long idTransaksiBeli) {
//        this.idTransaksiBeli = idTransaksiBeli;
//    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public TransaksiBeli getTransaksiBeli() {
        return transaksiBeli;
    }

    public void setTransaksiBeli(TransaksiBeli transaksiBeli) {
        this.transaksiBeli = transaksiBeli;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalPpn() {
        return totalPpn;
    }

    public void setTotalPpn(String totalPpn) {
        this.totalPpn = totalPpn;
    }

    public String getTotalDpp() {
        return totalDpp;
    }

    public void setTotalDpp(String totalDpp) {
        this.totalDpp = totalDpp;
    }

    public String getDpp() {
        return dpp;
    }

    public void setDpp(String dpp) {
        this.dpp = dpp;
    }

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
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

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
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
}
