package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_suplier")
public class Suplier extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suplier")
    private Long idSuplier;

    @Column(name = "kode_suplier", nullable = false, length = 50)
    private String kodeSuplier;

    @Column(name = "nama_suplier", nullable = false, length = 255)
    private String namaSuplier;

    @Column(name = "alamat_suplier", nullable = false, length = 255)
    private String alamatSuplier;

    @Column(name = "no_telp_suplier", nullable = false, length = 50)
    private String noTelpSuplier;

    @Column(name = "keterangan", nullable = false, length = 255)
    private String keterangan;


    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdSuplier() {
        return idSuplier;
    }

    public void setIdSuplier(Long idSuplier) {
        this.idSuplier = idSuplier;
    }

    public String getKodeSuplier() {
        return kodeSuplier;
    }

    public void setKodeSuplier(String kodeSuplier) {
        this.kodeSuplier = kodeSuplier;
    }

    public String getNamaSuplier() {
        return namaSuplier;
    }

    public void setNamaSuplier(String namaSuplier) {
        this.namaSuplier = namaSuplier;
    }

    public String getAlamatSuplier() {
        return alamatSuplier;
    }

    public void setAlamatSuplier(String alamatSuplier) {
        this.alamatSuplier = alamatSuplier;
    }

    public String getNoTelpSuplier() {
        return noTelpSuplier;
    }

    public void setNoTelpSuplier(String noTelpSuplier) {
        this.noTelpSuplier = noTelpSuplier;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }


    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}