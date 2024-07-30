package com.template.eazypos.dto;

import java.util.List;

public class TransaksiPenjualanDTO {

    private int totalBelanja;
    private int pembayaran;

    private int potongan;
    private int diskon;
    private int totalBayarBarang;
    private Long idCustomer;
    private Long idSalesman;
    private String noFaktur;
    private String keterangan;
    private String cashKredit;
    private int sisa;

    private String kekurangan;
    private int ttlBayarHemat;
    private List<BarangTransaksiDTO> produk;

    public int getTotalBelanja() {
        return totalBelanja;
    }

    public String getKekurangan() {
        return kekurangan;
    }

    public void setKekurangan(String kekurangan) {
        this.kekurangan = kekurangan;
    }

    public void setTotalBelanja(int totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public int getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(int pembayaran) {
        this.pembayaran = pembayaran;
    }

    public int getPotongan() {
        return potongan;
    }

    public void setPotongan(int potongan) {
        this.potongan = potongan;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public int getTotalBayarBarang() {
        return totalBayarBarang;
    }

    public void setTotalBayarBarang(int totalBayarBarang) {
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

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
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

    public int getSisa() {
        return sisa;
    }

    public void setSisa(int sisa) {
        this.sisa = sisa;
    }

    public int getTtlBayarHemat() {
        return ttlBayarHemat;
    }

    public void setTtlBayarHemat(int ttlBayarHemat) {
        this.ttlBayarHemat = ttlBayarHemat;
    }

    public List<BarangTransaksiDTO> getProduk() {
        return produk;
    }

    public void setProduk(List<BarangTransaksiDTO> produk) {
        this.produk = produk;
    }
}
