package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "kec")
public class Kec extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "id_kabkot")
//    private Long id_kabkot;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_kabkot", nullable = false, updatable = false)
    private KabKot kabKot;

//    @Column(name = "id_prov")
//    private Long id_prov;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_prov", nullable = false, updatable = false)
    private Prov prov;

    @Column(name = "nama_kec")
    private String nama_kec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getId_kabkot() {
//        return id_kabkot;
//    }
//
//    public void setId_kabkot(Long id_kabkot) {
//        this.id_kabkot = id_kabkot;
//    }
//
//    public Long getId_prov() {
//        return id_prov;
//    }
//
//    public void setId_prov(Long id_prov) {
//        this.id_prov = id_prov;
//    }


    public KabKot getKabKot() {
        return kabKot;
    }

    public void setKabKot(KabKot kabKot) {
        this.kabKot = kabKot;
    }

    public Prov getProv() {
        return prov;
    }

    public void setProv(Prov prov) {
        this.prov = prov;
    }

    public String getNama_kec() {
        return nama_kec;
    }

    public void setNama_kec(String nama_kec) {
        this.nama_kec = nama_kec;
    }
}
