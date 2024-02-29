package com.template.eazypos.dto;

public class StokKeluarDTO {

    private Long id_barang;
    private String jumlah_stok;

    private String keterangan;

    public Long getId_barang() {
        return id_barang;
    }

    public void setId_barang(Long id_barang) {
        this.id_barang = id_barang;
    }

    public String getJumlah_stok() {
        return jumlah_stok;
    }

    public void setJumlah_stok(String jumlah_stok) {
        this.jumlah_stok = jumlah_stok;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
