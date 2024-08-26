package com.template.eazypos.dto;

import java.util.Date;

public class KunjunganDTO {
    private Date tgl;

    private Long id_salesman;

    private Long id_customer;

    private Long id_plan;

    private String visit;

    private Long deal;

    private String action;



    private Date tanggal_deal;

    private String tujuan;

    private Long pembayaran;
    private String peluang;
    private String infoDpt;

    private Long serviceTt;

    private String cp;

    private Long nVisit;

    private String waktuPengadaan;


    private String lokasiLat;

    private String lokasiLon;

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public Long getId_salesman() {
        return id_salesman;
    }

    public void setId_salesman(Long id_salesman) {
        this.id_salesman = id_salesman;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public Long getId_plan() {
        return id_plan;
    }

    public void setId_plan(Long id_plan) {
        this.id_plan = id_plan;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public Long getDeal() {
        return deal;
    }

    public void setDeal(Long deal) {
        this.deal = deal;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public Date getTanggal_deal() {
        return tanggal_deal;
    }

    public void setTanggal_deal(Date tanggal_deal) {
        this.tanggal_deal = tanggal_deal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public Long getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Long pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getPeluang() {
        return peluang;
    }

    public void setPeluang(String peluang) {
        this.peluang = peluang;
    }

    public String getInfoDpt() {
        return infoDpt;
    }

    public void setInfoDpt(String infoDpt) {
        this.infoDpt = infoDpt;
    }

    public Long getServiceTt() {
        return serviceTt;
    }

    public void setServiceTt(Long serviceTt) {
        this.serviceTt = serviceTt;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Long getnVisit() {
        return nVisit;
    }

    public void setnVisit(Long nVisit) {
        this.nVisit = nVisit;
    }

    public String getWaktuPengadaan() {
        return waktuPengadaan;
    }

    public void setWaktuPengadaan(String waktuPengadaan) {
        this.waktuPengadaan = waktuPengadaan;
    }


    public String getLokasiLat() {
        return lokasiLat;
    }

    public void setLokasiLat(String lokasiLat) {
        this.lokasiLat = lokasiLat;
    }

    public String getLokasiLon() {
        return lokasiLon;
    }

    public void setLokasiLon(String lokasiLon) {
        this.lokasiLon = lokasiLon;
    }
}
