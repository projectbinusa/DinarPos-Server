package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_transaksi")
public class Transaksi extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi")
    private Long idTransaksi;

//    @Column(name = "id_customer", nullable = false, length = 100)
//    private String idCustomer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_customer", nullable = false, updatable = false)
    private Customer customer;

//    @Column(name = "id_salesman", nullable = false)
//    private Long idSalesman;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_salesman", nullable = false, updatable = false)
    private Salesman salesman;

    @Column(name = "status", nullable = false, length = 50, columnDefinition = "varchar(50) default 'excelcom'")
    private String status;

    @Column(name = "nama_customer", nullable = false, length = 100)
    private String namaCustomer;

    @Column(name = "nama_salesman", nullable = false, length = 100)
    private String namaSalesman;

    @Column(name = "total_bayar_barang", nullable = false, length = 50)
    private Double totalBayarBarang;

    @Column(name = "pembayaran", nullable = false, length = 50)
    private Double pembayaran;

    @Column(name = "diskon", nullable = false, length = 50)
    private Double diskon;

    @Column(name = "total_belanja", nullable = false, length = 50)
    private Double totalBelanja;

    @Column(name = "sisa", nullable = false, length = 50)
    private Double sisa;

    @Column(name = "potongan", nullable = false, length = 50)
    private Double potongan;

    @Column(name = "ttl_bayar_hemat", nullable = false, length = 50)
    private Double ttlBayarHemat;

    @Column(name = "nota", nullable = false, length = 50)
    private String nota;

    @Column(name = "no_faktur", nullable = false, length = 50)
    private String noFaktur;

    @Column(name = "keterangan", nullable = false, length = 100)
    private String keterangan;

    @Column(name = "cash_kredit", nullable = false, length = 20)
    private String cashKredit;

    @Column(name = "tanggal", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;


    @Column(name = "tanggal_notif_30", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tanggalNotif30;

    @Column(name = "tanggal_notif_7", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tanggalNotif7;

    @Column(name = "tanggal_notif_90", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tanggalNotif90;

    @Column(name = "tanggal_notif_120", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tanggalNotif120;

    @Column(name = "tanggal_notif_365", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tanggalNotif365;

    @Column(name = "del_flag", nullable = false)
    private int delFlag;

    @Column(name = "7_hari", nullable = false, columnDefinition = "int default 1")
    private int hari7;

    @Column(name = "30_hari", nullable = false, columnDefinition = "int default 1")
    private int hari30;

    @Column(name = "90_hari", nullable = false, columnDefinition = "int default 1")
    private int hari90;

    @Column(name = "120_hari", nullable = false, columnDefinition = "int default 1")
    private int hari120;

    @Column(name = "365_hari", nullable = false, columnDefinition = "int default 1")
    private int hari365;

    @Column(name = "ket_7_hari", nullable = false, columnDefinition = "text")
    private String ket7Hari;

    @Column(name = "ket_30_hari", nullable = false, columnDefinition = "text")
    private String ket30Hari;

    @Column(name = "ket_90_hari", nullable = false, columnDefinition = "text")
    private String ket90Hari;

    @Column(name = "ket_120_hari", nullable = false, columnDefinition = "text")
    private String ket120Hari;

    @Column(name = "ket_365_hari", nullable = false, columnDefinition = "text")
    private String ket365Hari;

    @Column(name = "salesman_7_hari", nullable = false, length = 100)
    private String salesman7Hari;

    @Column(name = "salesman_30_hari", nullable = false, length = 100)
    private String salesman30Hari;

    @Column(name = "salesman_90_hari", nullable = false, length = 100)
    private String salesman90Hari;

    @Column(name = "salesman_120_hari", nullable = false, length = 100)
    private String salesman120Hari;

    @Column(name = "salesman_365_hari", nullable = false, length = 100)
    private String salesman365Hari;

    @Column(name = "tanggal_konfirmasi_7", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalKonfirmasi7;

    @Column(name = "tanggal_konfirmasi_30", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalKonfirmasi30;

    @Column(name = "tanggal_konfirmasi_90", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalKonfirmasi90;

    @Column(name = "tanggal_konfirmasi_120", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalKonfirmasi120;

    @Column(name = "tanggal_konfirmasi_365", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalKonfirmasi365;


    public Long getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

//    public String getIdCustomer() {
//        return idCustomer;
//    }
//
//    public void setIdCustomer(String idCustomer) {
//        this.idCustomer = idCustomer;
//    }
//
//    public Long getIdSalesman() {
//        return idSalesman;
//    }
//
//    public void setIdSalesman(Long idSalesman) {
//        this.idSalesman = idSalesman;
//    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getNamaSalesman() {
        return namaSalesman;
    }

    public void setNamaSalesman(String namaSalesman) {
        this.namaSalesman = namaSalesman;
    }

    public Double getTotalBayarBarang() {
        return totalBayarBarang;
    }

    public void setTotalBayarBarang(Double totalBayarBarang) {
        this.totalBayarBarang = totalBayarBarang;
    }

    public Double getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Double pembayaran) {
        this.pembayaran = pembayaran;
    }

    public Double getDiskon() {
        return diskon;
    }

    public void setDiskon(Double diskon) {
        this.diskon = diskon;
    }

    public Double getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(Double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public Double getSisa() {
        return sisa;
    }

    public void setSisa(Double sisa) {
        this.sisa = sisa;
    }

    public Double getPotongan() {
        return potongan;
    }

    public void setPotongan(Double potongan) {
        this.potongan = potongan;
    }

    public Double getTtlBayarHemat() {
        return ttlBayarHemat;
    }

    public void setTtlBayarHemat(Double ttlBayarHemat) {
        this.ttlBayarHemat = ttlBayarHemat;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Date getTanggalNotif30() {
        return tanggalNotif30;
    }

    public void setTanggalNotif30(Date tanggalNotif30) {
        this.tanggalNotif30 = tanggalNotif30;
    }

    public Date getTanggalNotif7() {
        return tanggalNotif7;
    }

    public void setTanggalNotif7(Date tanggalNotif7) {
        this.tanggalNotif7 = tanggalNotif7;
    }

    public Date getTanggalNotif90() {
        return tanggalNotif90;
    }

    public void setTanggalNotif90(Date tanggalNotif90) {
        this.tanggalNotif90 = tanggalNotif90;
    }

    public Date getTanggalNotif120() {
        return tanggalNotif120;
    }

    public void setTanggalNotif120(Date tanggalNotif120) {
        this.tanggalNotif120 = tanggalNotif120;
    }

    public Date getTanggalNotif365() {
        return tanggalNotif365;
    }

    public void setTanggalNotif365(Date tanggalNotif365) {
        this.tanggalNotif365 = tanggalNotif365;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getHari7() {
        return hari7;
    }

    public void setHari7(int hari7) {
        this.hari7 = hari7;
    }

    public int getHari30() {
        return hari30;
    }

    public void setHari30(int hari30) {
        this.hari30 = hari30;
    }

    public int getHari90() {
        return hari90;
    }

    public void setHari90(int hari90) {
        this.hari90 = hari90;
    }

    public int getHari120() {
        return hari120;
    }

    public void setHari120(int hari120) {
        this.hari120 = hari120;
    }

    public int getHari365() {
        return hari365;
    }

    public void setHari365(int hari365) {
        this.hari365 = hari365;
    }

    public String getKet7Hari() {
        return ket7Hari;
    }

    public void setKet7Hari(String ket7Hari) {
        this.ket7Hari = ket7Hari;
    }

    public String getKet30Hari() {
        return ket30Hari;
    }

    public void setKet30Hari(String ket30Hari) {
        this.ket30Hari = ket30Hari;
    }

    public String getKet90Hari() {
        return ket90Hari;
    }

    public void setKet90Hari(String ket90Hari) {
        this.ket90Hari = ket90Hari;
    }

    public String getKet120Hari() {
        return ket120Hari;
    }

    public void setKet120Hari(String ket120Hari) {
        this.ket120Hari = ket120Hari;
    }

    public String getKet365Hari() {
        return ket365Hari;
    }

    public void setKet365Hari(String ket365Hari) {
        this.ket365Hari = ket365Hari;
    }

    public String getSalesman7Hari() {
        return salesman7Hari;
    }

    public void setSalesman7Hari(String salesman7Hari) {
        this.salesman7Hari = salesman7Hari;
    }

    public String getSalesman30Hari() {
        return salesman30Hari;
    }

    public void setSalesman30Hari(String salesman30Hari) {
        this.salesman30Hari = salesman30Hari;
    }

    public String getSalesman90Hari() {
        return salesman90Hari;
    }

    public void setSalesman90Hari(String salesman90Hari) {
        this.salesman90Hari = salesman90Hari;
    }

    public String getSalesman120Hari() {
        return salesman120Hari;
    }

    public void setSalesman120Hari(String salesman120Hari) {
        this.salesman120Hari = salesman120Hari;
    }

    public String getSalesman365Hari() {
        return salesman365Hari;
    }

    public void setSalesman365Hari(String salesman365Hari) {
        this.salesman365Hari = salesman365Hari;
    }

    public Date getTanggalKonfirmasi7() {
        return tanggalKonfirmasi7;
    }

    public void setTanggalKonfirmasi7(Date tanggalKonfirmasi7) {
        this.tanggalKonfirmasi7 = tanggalKonfirmasi7;
    }

    public Date getTanggalKonfirmasi30() {
        return tanggalKonfirmasi30;
    }

    public void setTanggalKonfirmasi30(Date tanggalKonfirmasi30) {
        this.tanggalKonfirmasi30 = tanggalKonfirmasi30;
    }

    public Date getTanggalKonfirmasi90() {
        return tanggalKonfirmasi90;
    }

    public void setTanggalKonfirmasi90(Date tanggalKonfirmasi90) {
        this.tanggalKonfirmasi90 = tanggalKonfirmasi90;
    }

    public Date getTanggalKonfirmasi120() {
        return tanggalKonfirmasi120;
    }

    public void setTanggalKonfirmasi120(Date tanggalKonfirmasi120) {
        this.tanggalKonfirmasi120 = tanggalKonfirmasi120;
    }

    public Date getTanggalKonfirmasi365() {
        return tanggalKonfirmasi365;
    }

    public void setTanggalKonfirmasi365(Date tanggalKonfirmasi365) {
        this.tanggalKonfirmasi365 = tanggalKonfirmasi365;
    }
}
