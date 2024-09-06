package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_customer")
    private String nama_customer;

//    @Column(name = "id_salesman")

//    private Long salesman;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_salesman", nullable = false, updatable = false)
    private Salesman salesman;

    @Column(name = "jenis")
    private String jenis;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_prov", nullable = false, updatable = false)
    private Prov prov;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_kabkot", nullable = false, updatable = false)
    private KabKot kabKot;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_kec", nullable = false, updatable = false)
    private Kec kec;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "email")
    private String email;

    @Column(name = "telp")
    private String telp;

    @Column(name = "printer")
    private String printer;

    @Column(name = "proyektor")
    private String proyektor;

    @Column(name = "internet")
    private String internet;

    @Column(name = "web")
    private String web;

    @Column(name = "jml")
    private Integer jml;

    @Column(name = "kls3")
    private Integer kls3;

    @Column(name = "pc")
    private Integer pc;

    @Column(name = "murid")
    private String murid;

    @Column(name = "unbk")
    private String unbk;

    @Column(name = "jurusan")
    private String jurusan;

    @Column(name = "LastEditedBy")
    private String LastEditedBy;

    @Column(name = "poin", nullable = false)
    private int poin;

    @Column(name = "del_flag")
    private Integer del_flag = 1;

    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

//    public Long getSalesman() {
//        return salesman;
//    }
//
//    public void setSalesman(Long salesman) {
//        this.salesman = salesman;
//    }


    public String getMurid() {
        return murid;
    }

    public void setMurid(String murid) {
        this.murid = murid;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public KabKot getKabKot() {
        return kabKot;
    }

    public void setKabKot(KabKot kabKot) {
        this.kabKot = kabKot;
    }

    public Kec getKec() {
        return kec;
    }

    public void setKec(Kec kec) {
        this.kec = kec;
    }

    public Prov getProv() {
        return prov;
    }

    public void setProv(Prov prov) {
        this.prov = prov;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getProyektor() {
        return proyektor;
    }

    public void setProyektor(String proyektor) {
        this.proyektor = proyektor;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Integer getJml() {
        return jml;
    }

    public void setJml(Integer jml) {
        this.jml = jml;
    }

    public Integer getKls3() {
        return kls3;
    }

    public void setKls3(Integer kls3) {
        this.kls3 = kls3;
    }

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public String getUnbk() {
        return unbk;
    }

    public void setUnbk(String unbk) {
        this.unbk = unbk;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getLastEditedBy() {
        return LastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        LastEditedBy = lastEditedBy;
    }
}
