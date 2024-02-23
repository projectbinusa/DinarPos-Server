package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "marketting")
public class Marketting extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marketting")
    private Long idMarketting;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nama", nullable = false)
    private String nama;

    @Lob
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @Column(name = "nohp", nullable = false)
    private String nohp;

    @Lob
    @Column(name = "fotopp", nullable = false)
    private String fotoPp;

    @Column(name = "target", nullable = false)
    private Long target;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "id_kategori_marketting", nullable = false, updatable = false)
    private KatrgoriMarketting katrgoriMarketting;

    @Column(name = "status", nullable = false)
    private Integer status =1;

    public Long getIdMarketting() {
        return idMarketting;
    }

    public void setIdMarketting(Long idMarketting) {
        this.idMarketting = idMarketting;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public KatrgoriMarketting getKatrgoriMarketting() {
        return katrgoriMarketting;
    }

    public void setKatrgoriMarketting(KatrgoriMarketting katrgoriMarketting) {
        this.katrgoriMarketting = katrgoriMarketting;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getFotoPp() {
        return fotoPp;
    }

    public void setFotoPp(String fotoPp) {
        this.fotoPp = fotoPp;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}