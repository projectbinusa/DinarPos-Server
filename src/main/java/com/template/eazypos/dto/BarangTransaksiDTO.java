package com.template.eazypos.dto;

public class BarangTransaksiDTO {
    private Long idTransaksi;
    private String barcodeBarang;
    private int qty;
    private int diskon;
    private int hargaBrng;
    private int totalHarga;
    private int totalHargaBarang;

    public Long getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

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
}
