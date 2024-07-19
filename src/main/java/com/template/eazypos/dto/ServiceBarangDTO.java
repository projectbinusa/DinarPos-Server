package com.template.eazypos.dto;

import com.template.eazypos.model.Customer;

import java.util.Date;

public class ServiceBarangDTO {
    private Long idTT;
    private String checker;
    private String nama;
    private String alamat;
    private String cp;
    private int poin;
    private String ket;
    private String produk;
    private String merk;
    private String type;
    private String sn;
    private String keluhan;
    private String perlengkapan;
    private String penerima;
    private Date tanggalMasuk;
    private Date tanggalJadi;
    private Date tanggalAmbil;
    private int biayaSparepart;
    private int biayaService;
    private int total;
    private int estimasi;
    private int bmax;
    private String catatan;
    private String statusEnd;
    private String taken;
    private String fb;
    private String fa;
    private Date timestamp;
    private String namaCustomer;

    public ServiceBarangDTO(Long idTT, String checker, String namaCustomer, String nama, String alamat, String cp, int poin, String ket, String produk, String merk, String type, String sn, String keluhan, String perlengkapan, String penerima, Date tanggalMasuk, Date tanggalJadi, Date tanggalAmbil, int biayaSparepart, int biayaService, int total, int estimasi, int bmax, String catatan, String statusEnd, String taken, String fb, String fa, Date timestamp) {
        this.idTT = idTT;
        this.checker = checker;
        this.namaCustomer = namaCustomer;
        this.nama = nama;
        this.alamat = alamat;
        this.cp = cp;
        this.poin = poin;
        this.ket = ket;
        this.produk = produk;
        this.merk = merk;
        this.type = type;
        this.sn = sn;
        this.keluhan = keluhan;
        this.perlengkapan = perlengkapan;
        this.penerima = penerima;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalJadi = tanggalJadi;
        this.tanggalAmbil = tanggalAmbil;
        this.biayaSparepart = biayaSparepart;
        this.biayaService = biayaService;
        this.total = total;
        this.estimasi = estimasi;
        this.bmax = bmax;
        this.catatan = catatan;
        this.statusEnd = statusEnd;
        this.taken = taken;
        this.fb = fb;
        this.fa = fa;
        this.timestamp = timestamp;
    }
    public Long getIdTT() {
        return idTT;
    }

    public void setIdTT(Long idTT) {
        this.idTT = idTT;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
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
