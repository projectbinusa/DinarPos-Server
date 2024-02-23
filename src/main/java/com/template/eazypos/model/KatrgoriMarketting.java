package com.template.eazypos.model;

import javax.persistence.*;

@Entity
@Table(name = "kategori_marketting")
public class KatrgoriMarketting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_kategori_marketting;

    @Column(name = "nama_kategori")
    private String nama_kategori;

    public Long getId_kategori_marketting() {
        return id_kategori_marketting;
    }

    public void setId_kategori_marketting(Long id_kategori_marketting) {
        this.id_kategori_marketting = id_kategori_marketting;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }
}
