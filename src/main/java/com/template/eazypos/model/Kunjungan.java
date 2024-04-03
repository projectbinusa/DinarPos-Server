package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kunjungan")
public class Kunjungan extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    private Long idReport;

//    @Column(name = "id_marketting")
//    private Long idMarketting;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_marketting", nullable = false, updatable = false)
    private Marketting marketting;

    @Column(name = "id_plan")
    private Long idPlan;

    @Column(name = "id_customer")
    private String idCustomer;

    @Column(name = "tgl_kunjungan")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalKunjungan;

    @Lob
    @Column(name = "info_dpt")
    private String infoDpt;
    @Lob
    @Column(name = "action")
    private String action;

    @Column(name = "tujuan")
    private String tujuan;

    @Column(name = "cp")
    private String cp;
    @Lob
    @Column(name = "peluang")
    private String peluang;

    @Column(name = "visit")
    private String visit;

    @Column(name = "n_visit")
    private Long nVisit;

    @Column(name = "deal")
    private Long deal;

    @Column(name = "pembayaran")
    private Long pembayaran;

    @Column(name = "waktu_pengadaan")
    private String waktuPengadaan;

    @Column(name = "tgl_deal")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalDeal;

    @Lob
    @Column(name = "foto")
    private String foto;

    @Column(name = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date timestamp;

    @Column(name = "lokasi_lat")
    private String lokasiLat;

    @Column(name = "lokasi_lon")
    private String lokasiLon;

    @Column(name = "service_tt")
    private Long serviceTt;

    public Long getIdReport() {
        return idReport;
    }

    public void setIdReport(Long idReport) {
        this.idReport = idReport;
    }

//    public Long getIdMarketting() {
//        return idMarketting;
//    }
//
//    public void setIdMarketting(Long idMarketting) {
//        this.idMarketting = idMarketting;
//    }


    public Marketting getMarketting() {
        return marketting;
    }

    public void setMarketting(Marketting marketting) {
        this.marketting = marketting;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Date getTanggalKunjungan() {
        return tanggalKunjungan;
    }

    public void setTanggalKunjungan(Date tanggalKunjungan) {
        this.tanggalKunjungan = tanggalKunjungan;
    }

    public String getInfoDpt() {
        return infoDpt;
    }

    public void setInfoDpt(String infoDpt) {
        this.infoDpt = infoDpt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPeluang() {
        return peluang;
    }

    public void setPeluang(String peluang) {
        this.peluang = peluang;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public Long getnVisit() {
        return nVisit;
    }

    public void setnVisit(Long nVisit) {
        this.nVisit = nVisit;
    }

    public Long getDeal() {
        return deal;
    }

    public void setDeal(Long deal) {
        this.deal = deal;
    }

    public Long getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Long pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getWaktuPengadaan() {
        return waktuPengadaan;
    }

    public void setWaktuPengadaan(String waktuPengadaan) {
        this.waktuPengadaan = waktuPengadaan;
    }

    public Date getTanggalDeal() {
        return tanggalDeal;
    }

    public void setTanggalDeal(Date tanggalDeal) {
        this.tanggalDeal = tanggalDeal;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public Long getServiceTt() {
        return serviceTt;
    }

    public void setServiceTt(Long serviceTt) {
        this.serviceTt = serviceTt;
    }
}
