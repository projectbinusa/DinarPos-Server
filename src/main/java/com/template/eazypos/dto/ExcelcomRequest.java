package com.template.eazypos.dto;

import com.template.eazypos.model.BarangTransaksi;

import java.util.List;

public class ExcelcomRequest {
    private String noFaktur;
    private double totalBayar;
    private double pembayaran;
    private double potongan;
    private double diskon;
    private double totalBayarBarang;
    private Long idCustomer;
    private Long idSalesman;
    private String keterangan;
    private String cashKredit;
    private double sisa;
    private double ttlBayarHemat;
    private List<BarangTransaksi> produk;

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    public double getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(double pembayaran) {
        this.pembayaran = pembayaran;
    }

    public double getPotongan() {
        return potongan;
    }

    public void setPotongan(double potongan) {
        this.potongan = potongan;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getTotalBayarBarang() {
        return totalBayarBarang;
    }

    public void setTotalBayarBarang(double totalBayarBarang) {
        this.totalBayarBarang = totalBayarBarang;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdSalesman() {
        return idSalesman;
    }

    public void setIdSalesman(Long idSalesman) {
        this.idSalesman = idSalesman;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCashKredit() {
        return cashKredit;
    }

    public void setCashKredit(String cashKredit) {
        this.cashKredit = cashKredit;
    }

    public double getSisa() {
        return sisa;
    }

    public void setSisa(double sisa) {
        this.sisa = sisa;
    }

    public double getTtlBayarHemat() {
        return ttlBayarHemat;
    }

    public void setTtlBayarHemat(double ttlBayarHemat) {
        this.ttlBayarHemat = ttlBayarHemat;
    }

    public List<BarangTransaksi> getProduk() {
        return produk;
    }

    public void setProduk(List<BarangTransaksi> produk) {
        this.produk = produk;
    }
}
