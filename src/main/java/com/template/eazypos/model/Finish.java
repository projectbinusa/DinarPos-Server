package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "finish")
public class Finish extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "id_report")
//    private Long id_report;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_report", nullable = false, updatable = false)
    private Kunjungan kunjungan;

    @Lob
    @Column(name = "BAST")
    private String BAST;

    @Lob
    @Column(name = "BAUT")
    private String BAUT;

    @Lob
    @Column(name = "BASP")
    private String BASP;

    @Lob
    @Column(name = "SPK")
    private String SPK;

    @Lob
    @Column(name = "ev_dtg")
    private String ev_dtg;

    @Lob
    @Column(name = "ev_pro")
    private String ev_pro;

    @Lob
    @Column(name = "ev_fin")
    private String ev_fin;

    @Lob
    @Column(name = "file_spk")
    private String file_spk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getId_report() {
//        return id_report;
//    }
//
//    public void setId_report(Long id_report) {
//        this.id_report = id_report;
//    }


    public Kunjungan getKunjungan() {
        return kunjungan;
    }

    public void setKunjungan(Kunjungan kunjungan) {
        this.kunjungan = kunjungan;
    }

    public String getBAST() {
        return BAST;
    }

    public void setBAST(String BAST) {
        this.BAST = BAST;
    }

    public String getBAUT() {
        return BAUT;
    }

    public void setBAUT(String BAUT) {
        this.BAUT = BAUT;
    }

    public String getBASP() {
        return BASP;
    }

    public void setBASP(String BASP) {
        this.BASP = BASP;
    }

    public String getSPK() {
        return SPK;
    }

    public void setSPK(String SPK) {
        this.SPK = SPK;
    }

    public String getEv_dtg() {
        return ev_dtg;
    }

    public void setEv_dtg(String ev_dtg) {
        this.ev_dtg = ev_dtg;
    }

    public String getEv_pro() {
        return ev_pro;
    }

    public void setEv_pro(String ev_pro) {
        this.ev_pro = ev_pro;
    }

    public String getEv_fin() {
        return ev_fin;
    }

    public void setEv_fin(String ev_fin) {
        this.ev_fin = ev_fin;
    }

    public String getFile_spk() {
        return file_spk;
    }

    public void setFile_spk(String file_spk) {
        this.file_spk = file_spk;
    }
}
