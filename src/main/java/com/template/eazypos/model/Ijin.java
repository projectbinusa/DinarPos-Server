package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ijin")
public class Ijin extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "id_marketting")
//    private Long id_marketting;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_marketting", nullable = false, updatable = false)
    private Marketting marketting;

    @Column(name = "jenis")
    private String jenis;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    @Column(name = "tgl_a")
    private Date tgl_a;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    @Column(name = "tgl_b")
    private Date tgl_b;

    @Lob
    @Column(name = "ket")
    private String ket;

    @Lob
    @Column(name = "foto")
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getId_marketting() {
//        return id_marketting;
//    }
//
//    public void setId_marketting(Long id_marketting) {
//        this.id_marketting = id_marketting;
//    }


    public Marketting getMarketting() {
        return marketting;
    }

    public void setMarketting(Marketting marketting) {
        this.marketting = marketting;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Date getTgl_a() {
        return tgl_a;
    }

    public void setTgl_a(Date tgl_a) {
        this.tgl_a = tgl_a;
    }

    public Date getTgl_b() {
        return tgl_b;
    }

    public void setTgl_b(Date tgl_b) {
        this.tgl_b = tgl_b;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
