package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kas_harian")
public class KasHarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_transaksi",  updatable = false)
    private Transaksi transaksi;

    @Column(name = "penjualan")
    private String  penjualan;

    @Column(name = "pelunasan")
    private String  pelunasan;

    @Column(name = "piutang")
    private String  piutang;

    @Column(name = "return_penjualan")
    private String  return_penjualan;

    @Column(name = "bank")
    private String  bank;

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date timestamp;

    public Date getTimestamp() { 
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public String getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(String penjualan) {
        this.penjualan = penjualan;
    }

    public String getPelunasan() {
        return pelunasan;
    }

    public void setPelunasan(String pelunasan) {
        this.pelunasan = pelunasan;
    }

    public String getPiutang() {
        return piutang;
    }

    public void setPiutang(String piutang) {
        this.piutang = piutang;
    }

    public String getReturn_penjualan() {
        return return_penjualan;
    }

    public void setReturn_penjualan(String return_penjualan) {
        this.return_penjualan = return_penjualan;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
