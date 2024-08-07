package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_transaksi_beli")
public class TransaksiBeli extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi_beli")
    private Long idTransaksiBeli;

//    @Column(name = "id_suplier", nullable = false)
//    private Long idSuplier;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_suplier", nullable = false, updatable = false)
    private Suplier suplier;

    @Column(name = "status", nullable = false, length = 50, columnDefinition = "varchar(50) default 'excelcom'")
    private String status;

    @Column(name = "nama_suplier", length = 100)
    private String namaSuplier;

    @Column(name = "total_bayar_barang", nullable = false, length = 50)
    private String totalBayarBarang;

    @Column(name = "pembayaran", nullable = false)
    private int pembayaran;

    @Column(name = "kekurangan")
    private String kekurangan;

    @Column(name = "diskon", nullable = false)
    private int diskon;

    @Column(name = "nominal_hutang")
    private Integer nominalHutang;

    @Column(name = "hutang")
    private String hutang;

    @Column(name = "total_belanja", nullable = false, length = 50)
    private String totalBelanja;

    @Column(name = "total_belanja_dua",  length = 50)
    private String totalBelanjaDua;

    @Column(name = "total_beli",  length = 50)
    private String totalBeli;

    @Column(name = "sisa", nullable = false, length = 50)
    private String sisa;

    @Column(name = "potongan", nullable = false, length = 50)
    private String potongan;

    @Column(name = "ttl_bayar_hemat", nullable = false, length = 50)
    private String ttlBayarHemat;

    @Column(name = "no_faktur", nullable = false, length = 50)
    private String noFaktur;

    @Column(name = "keterangan", nullable = false, length = 100)
    private String keterangan;

    @Column(name = "cash_credit", nullable = false, length = 20)
    private String cashCredit;

    @Column(name = "tanggal", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggal;

    @Column(name = "total_dpp",  length = 100)
    private String totalDpp;

    @Column(name = "total_ppn",  length = 100)
    private String totalPpn;

    @Column(name = "total_dpp_dua",  length = 100)
    private String totalDppDua;

    @Column(name = "total_ppn_dua",  length = 100)
    private String totalPpnDua;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdTransaksiBeli() {
        return idTransaksiBeli;
    }

    public void setIdTransaksiBeli(Long idTransaksiBeli) {
        this.idTransaksiBeli = idTransaksiBeli;
    }

//    public Long getIdSuplier() {
//        return idSuplier;
//    }
//
//    public void setIdSuplier(Long idSuplier) {
//        this.idSuplier = idSuplier;
//    }




    public Suplier getSuplier() {
        return suplier;
    }



    public String getKekurangan() {
        return kekurangan;
    }

    public void setKekurangan(String kekurangan) {
        this.kekurangan = kekurangan;
    }

    public void setSuplier(Suplier suplier) {
        this.suplier = suplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaSuplier() {
        return namaSuplier;
    }

    public void setNamaSuplier(String namaSuplier) {
        this.namaSuplier = namaSuplier;
    }

    public String getTotalBayarBarang() {
        return totalBayarBarang;
    }

    public void setTotalBayarBarang(String totalBayarBarang) {
        this.totalBayarBarang = totalBayarBarang;
    }

    public int getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(int pembayaran) {
        this.pembayaran = pembayaran;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public String getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(String totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public String getSisa() {
        return sisa;
    }

    public void setSisa(String sisa) {
        this.sisa = sisa;
    }

    public String getPotongan() {
        return potongan;
    }

    public void setPotongan(String potongan) {
        this.potongan = potongan;
    }

    public String getTtlBayarHemat() {
        return ttlBayarHemat;
    }

    public void setTtlBayarHemat(String ttlBayarHemat) {
        this.ttlBayarHemat = ttlBayarHemat;
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

    public String getCashCredit() {
        return cashCredit;
    }

    public void setCashCredit(String cashCredit) {
        this.cashCredit = cashCredit;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getNominalHutang() {
        return nominalHutang;
    }

    public void setNominalHutang(Integer nominalHutang) {
        this.nominalHutang = nominalHutang;
    }

    public String getHutang() {
        return hutang;
    }

    public void setHutang(String hutang) {
        this.hutang = hutang;
    }

    public String getTotalBelanjaDua() {
        return totalBelanjaDua;
    }

    public void setTotalBelanjaDua(String totalBelanjaDua) {
        this.totalBelanjaDua = totalBelanjaDua;
    }

    public String getTotalBeli() {
        return totalBeli;
    }

    public void setTotalBeli(String totalBeli) {
        this.totalBeli = totalBeli;
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

    public String getTotalDppDua() {
        return totalDppDua;
    }

    public void setTotalDppDua(String totalDppDua) {
        this.totalDppDua = totalDppDua;
    }

    public String getTotalPpnDua() {
        return totalPpnDua;
    }

    public void setTotalPpnDua(String totalPpnDua) {
        this.totalPpnDua = totalPpnDua;
    }
}
