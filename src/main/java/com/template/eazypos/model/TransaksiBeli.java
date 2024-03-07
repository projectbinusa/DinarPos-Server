package com.template.eazypos.model;

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

    @Column(name = "diskon", nullable = false)
    private int diskon;

    @Column(name = "total_belanja", nullable = false, length = 50)
    private String totalBelanja;

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
    private Date tanggal;

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
}
