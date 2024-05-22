package com.template.eazypos.dto;

import java.util.List;

public class TransaksiPenjualanDTO {

    private Double totalBelanja;
    private int pembayaran;
    private Double potongan;
    private Double diskon;
    private Double totalBayarBarang;
    private Long idCustomer;
    private Long idSalesman;
    private String noFaktur;
    private String keterangan;
    private String cashKredit;
    private Double sisa;

    private String kekurangan;
    private Double ttlBayarHemat;
    private List<BarangTransaksiDTO> produk;

    public Double getTotalBelanja() {
        return totalBelanja;
    }

    public String getKekurangan() {
        return kekurangan;
    }

    public void setKekurangan(String kekurangan) {
        this.kekurangan = kekurangan;
    }

    public void setTotalBelanja(Double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public int getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(int pembayaran) {
        this.pembayaran = pembayaran;
    }

    public Double getPotongan() {
        return potongan;
    }

    public void setPotongan(Double potongan) {
        this.potongan = potongan;
    }

    public Double getDiskon() {
        return diskon;
    }

    public void setDiskon(Double diskon) {
        this.diskon = diskon;
    }

    public Double getTotalBayarBarang() {
        return totalBayarBarang;
    }

    public void setTotalBayarBarang(Double totalBayarBarang) {
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

    public Double getSisa() {
        return sisa;
    }

    public void setSisa(Double sisa) {
        this.sisa = sisa;
    }

    public Double getTtlBayarHemat() {
        return ttlBayarHemat;
    }

    public void setTtlBayarHemat(Double ttlBayarHemat) {
        this.ttlBayarHemat = ttlBayarHemat;
    }

    public List<BarangTransaksiDTO> getProduk() {
        return produk;
    }

    public void setProduk(List<BarangTransaksiDTO> produk) {
        this.produk = produk;
    }
}
