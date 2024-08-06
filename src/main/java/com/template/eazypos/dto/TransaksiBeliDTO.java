package com.template.eazypos.dto;

import com.template.eazypos.model.BarangTransaksiBeliDTO;

import java.util.List;

public class TransaksiBeliDTO {
    private String totalBelanja;
    private int total3;
    private String hutang;

    private int totalBayar2;

    private String ppn;
    private String dpp;
    private String dpp2;
    private String ppn2;
    private int pembayaran;
    private String potongan;
    private int diskon;
    private String totalBayarBarang;
    private Long idSuplier;
    private String keterangan;
    private String cashCredit;
    private String sisa;
    private String ttlBayarHemat;
    private List<BarangTransaksiBeliDTO> produk;

    public String getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(String totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public int getPembayaran() {
        return pembayaran;
    }

    public int getTotal3() {
        return total3;
    }

    public void setTotal3(int total3) {
        this.total3 = total3;
    }

    public String getHutang() {
        return hutang;
    }

    public void setHutang(String hutang) {
        this.hutang = hutang;
    }

    public int getTotalBayar2() {
        return totalBayar2;
    }

    public void setTotalBayar2(int totalBayar2) {
        this.totalBayar2 = totalBayar2;
    }

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getDpp() {
        return dpp;
    }

    public void setDpp(String dpp) {
        this.dpp = dpp;
    }

    public String getDpp2() {
        return dpp2;
    }

    public void setDpp2(String dpp2) {
        this.dpp2 = dpp2;
    }

    public String getPpn2() {
        return ppn2;
    }

    public void setPpn2(String ppn2) {
        this.ppn2 = ppn2;
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

    public List<BarangTransaksiBeliDTO> getProduk() {
        return produk;
    }

    public void setProduk(List<BarangTransaksiBeliDTO> produk) {
        this.produk = produk;
    }
}
