package com.template.eazypos.model;
public class BarangTransaksiBeliDTO {
    private String barcodeBarang;
    private int qty;
    private int diskon;
    private int hargaBrng;
    private int totalHarga;
    private int totalHargaBarang;
    private int hemat;
    private String dpp;
    private String ppn;
    private String totalDpp;
    private String totalPpn;

    public String getBarcodeBarang() {
        return barcodeBarang;
    }

    public void setBarcodeBarang(String barcodeBarang) {
        this.barcodeBarang = barcodeBarang;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public int getHargaBrng() {
        return hargaBrng;
    }

    public void setHargaBrng(int hargaBrng) {
        this.hargaBrng = hargaBrng;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getTotalHargaBarang() {
        return totalHargaBarang;
    }

    public void setTotalHargaBarang(int totalHargaBarang) {
        this.totalHargaBarang = totalHargaBarang;
    }

    public int getHemat() {
        return hemat;
    }

    public void setHemat(int hemat) {
        this.hemat = hemat;
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

    public String getTotalDpp() {
        return totalDpp;
    }

    public void setTotalDpp(String totalDpp) {
        this.totalDpp = totalDpp;
    }

    public String getTotalPpn() {
        return totalPpn;
    }

    public void setTotalPpn(String totalPpn) {
        this.totalPpn = totalPpn;
    }
}
