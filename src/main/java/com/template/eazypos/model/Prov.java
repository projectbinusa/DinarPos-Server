package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "prov")
public class Prov extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prov")
    private Long idProv;

    @Column(name = "nama_prov", nullable = false, length = 30)
    private String namaProv;

    public Long getIdProv() {
        return idProv;
    }

    public void setIdProv(Long idProv) {
        this.idProv = idProv;
    }

    public String getNamaProv() {
        return namaProv;
    }

    public void setNamaProv(String namaProv) {
        this.namaProv = namaProv;
    }
}