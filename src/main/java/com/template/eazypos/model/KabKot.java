package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "kabkot")
public class KabKot extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_prov")
//    private Long id_prov;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_prov", nullable = false, updatable = false)
    private Prov prov;

    @Column(name = "nama_kabkot")
    private String nama_kabkot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getId_prov() {
//        return id_prov;
//    }
//
//    public void setId_prov(Long id_prov) {
//        this.id_prov = id_prov;
//    }


    public Prov getProv() {
        return prov;
    }

    public void setProv(Prov prov) {
        this.prov = prov;
    }

    public String getNama_kabkot() {
        return nama_kabkot;
    }

    public void setNama_kabkot(String nama_kabkot) {
        this.nama_kabkot = nama_kabkot;
    }
}
