package com.template.eazypos.dto;

import java.util.List;

public class TransaksiBeliDTO {
    private String totalBelanja;
    private int pembayaran;
    private String potongan;
    private int diskon;
    private String totalBayarBarang;
    private Long idSuplier;
    private String keterangan;
    private String cashCredit;
    private String sisa;
    private String ttlBayarHemat;
    private String kekurangan;
    private List<BarangTransaksiDTO> produk;

    public String getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(String totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public int getPembayaran() {
        return pembayaran;
    }

    public String getKekurangan() {
        return kekurangan;
    }

    public void setKekurangan(String kekurangan) {
        this.kekurangan = kekurangan;
    }

    public void setPembayaran(int pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getPotongan() {
        return potongan;
    }

    public void setPotongan(String potongan) {
        this.potongan = potongan;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public String getTotalBayarBarang() {
        return totalBayarBarang;
    }

    public void setTotalBayarBarang(String totalBayarBarang) {
        this.totalBayarBarang = totalBayarBarang;
    }

    public Long getIdSuplier() {
        return idSuplier;
    }

    public void setIdSuplier(Long idSuplier) {
        this.idSuplier = idSuplier;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCashCredit() {
        return cashCredit;
    }

    public void setCashCredit(String cashCredit) {
        this.cashCredit = cashCredit;
    }

    public String getSisa() {
        return sisa;
    }

    public void setSisa(String sisa) {
        this.sisa = sisa;
    }

    public String getTtlBayarHemat() {
        return ttlBayarHemat;
    }

    public void setTtlBayarHemat(String ttlBayarHemat) {
        this.ttlBayarHemat = ttlBayarHemat;
    }

    public List<BarangTransaksiDTO> getProduk() {
        return produk;
    }

    public void setProduk(List<BarangTransaksiDTO> produk) {
        this.produk = produk;
    }
}
