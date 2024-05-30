package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "service")
public class ServiceBarang extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tt")
    private Long idTT;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_teknisi", nullable = false)
    private Teknisi teknisi;

    @Column(name = "checker", nullable = false, length = 255)
    private String checker;

//    @Column(name = "customer_id", nullable = false)
//    private Long customerId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @Column(name = "nama", nullable = false, length = 50)
    private String nama;

    @Column(name = "alamat", nullable = false, columnDefinition = "text")
    private String alamat;

    @Column(name = "cp", nullable = false, length = 100)
    private String cp;

    @Column(name = "poin", nullable = false)
    private int poin;

    @Column(name = "ket", nullable = false, columnDefinition = "text")
    private String ket;

    @Column(name = "produk", nullable = false, length = 50)
    private String produk;

    @Column(name = "merk", nullable = false, length = 50)
    private String merk;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "sn", nullable = false, length = 50)
    private String sn;

    @Column(name = "keluhan", nullable = false, columnDefinition = "text")
    private String keluhan;

    @Column(name = "perlengkapan", nullable = false, columnDefinition = "text")
    private String perlengkapan;

    @Column(name = "penerima", nullable = false, length = 30)
    private String penerima;

    @Column(name = "tgl_masuk", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalMasuk;

    @Column(name = "tgl_jadi", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalJadi;

    @Column(name = "tgl_ambil", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalAmbil;

    @Column(name = "b_sparepart", nullable = false)
    private int biayaSparepart;

    @Column(name = "b_service", nullable = false)
    private int biayaService;

    @Column(name = "total", nullable = false)
    private int total;

    @Column(name = "estimasi", nullable = false)
    private int estimasi;

    @Column(name = "bmax", nullable = false)
    private int bmax;

    @Column(name = "catatan", nullable = false, columnDefinition = "text")
    private String catatan;

    @Column(name = "status_end", nullable = false, length = 10, columnDefinition = "varchar(10) default 'N_A'")
    private String statusEnd;

    @Column(name = "taken", nullable = false, length = 1, columnDefinition = "varchar(1) default 'N'")
    private String taken;

    @Lob
    @Column(name = "f_b", nullable = false, columnDefinition = "text")
    private String fb;

    @Lob
    @Column(name = "f_a", nullable = false, columnDefinition = "text")
    private String fa;

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date timestamp;

    public Long getIdTT() {
        return idTT;
    }

    public void setIdTT(Long idTT) {
        this.idTT = idTT;
    }

    public Teknisi getTeknisi() {
        return teknisi;
    }

    public void setTeknisi(Teknisi teknisi) {
        this.teknisi = teknisi;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }
//    public Long getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(Long customerId) {
//        this.customerId = customerId;
//    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getPerlengkapan() {
        return perlengkapan;
    }

    public void setPerlengkapan(String perlengkapan) {
        this.perlengkapan = perlengkapan;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
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

    public Date getTanggalAmbil() {
        return tanggalAmbil;
    }

    public void setTanggalAmbil(Date tanggalAmbil) {
        this.tanggalAmbil = tanggalAmbil;
    }

    public int getBiayaSparepart() {
        return biayaSparepart;
    }

    public void setBiayaSparepart(int biayaSparepart) {
        this.biayaSparepart = biayaSparepart;
    }

    public int getBiayaService() {
        return biayaService;
    }

    public void setBiayaService(int biayaService) {
        this.biayaService = biayaService;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(int estimasi) {
        this.estimasi = estimasi;
    }

    public int getBmax() {
        return bmax;
    }

    public void setBmax(int bmax) {
        this.bmax = bmax;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getStatusEnd() {
        return statusEnd;
    }

    public void setStatusEnd(String statusEnd) {
        this.statusEnd = statusEnd;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
