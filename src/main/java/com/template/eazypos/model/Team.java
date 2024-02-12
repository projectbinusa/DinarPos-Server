package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "team")
public class Team extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_marketting", nullable = false)
//    private Long idMarketting;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_marketting", nullable = false, updatable = false)
    private Marketting marketting;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_teknisi", nullable = false, updatable = false)
    private Teknisi teknisi;

//    @Column(name = "id_teknisi", nullable = false, length = 10)
//    private String idTeknisi;


    @Column(name = "id_team", nullable = false)
    private Long idTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getIdMarketting() {
//        return idMarketting;
//    }
//
//    public void setIdMarketting(Long idMarketting) {
//        this.idMarketting = idMarketting;
//    }


    public Marketting getMarketting() {
        return marketting;
    }

    public void setMarketting(Marketting marketting) {
        this.marketting = marketting;
    }

    public Teknisi getTeknisi() {
        return teknisi;
    }

    public void setTeknisi(Teknisi teknisi) {
        this.teknisi = teknisi;
    }

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

//    public String getIdTeknisi() {
//        return idTeknisi;
//    }
//
//    public void setIdTeknisi(String idTeknisi) {
//        this.idTeknisi = idTeknisi;
//    }
}
