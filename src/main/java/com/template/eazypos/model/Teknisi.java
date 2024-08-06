package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "teknisi")
public class Teknisi extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teknisi")
    private Long id;

    @Column(name = "nama", nullable = false, length = 20)
    private String nama;

    @Column(name = "alamat", nullable = false, columnDefinition = "text")
    private String alamat;

    @Column(name = "bagian", nullable = false, length = 10)
    private String bagian;

    @Column(name = "nohp", nullable = false, length = 12)
    private String nohp;

    @Column(name = "total_poin", nullable = false)
    private int totalPoin;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;


    public Teknisi() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public int getTotalPoin() {
        return totalPoin;
    }

    public void setTotalPoin(int totalPoin) {
        this.totalPoin = totalPoin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
