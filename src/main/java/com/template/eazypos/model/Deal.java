package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deal")
public class Deal extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "id_report")
//    private Long id_report;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_report", nullable = false, updatable = false)
    private Kunjungan kunjungan;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    @Column(name = "tanggal_input")
    private Date tanggal_input;

    @Lob
    @Column(name = "foto")
    private String foto;

    @Lob
    @Column(name = "ket")
    private String ket;
    @Lob
    @Column(name = "file_po")
    private String file_po;

    @Column(name = "status")
    private String status;

    @Column(name = "ket_status")
    private String ket_status;

    @Column(name = "administrasi")
    private String administrasi;

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

    public Date getTanggal_input() {
        return tanggal_input;
    }

    public void setTanggal_input(Date tanggal_input) {
        this.tanggal_input = tanggal_input;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getFile_po() {
        return file_po;
    }

    public void setFile_po(String file_po) {
        this.file_po = file_po;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKet_status() {
        return ket_status;
    }

    public void setKet_status(String ket_status) {
        this.ket_status = ket_status;
    }

    public String getAdministrasi() {
        return administrasi;
    }

    public void setAdministrasi(String administrasi) {
        this.administrasi = administrasi;
    }
}
