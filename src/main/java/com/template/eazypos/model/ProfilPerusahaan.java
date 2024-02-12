package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "tabel_profil_perusahaan")
public class ProfilPerusahaan extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perusahaan")
    private Long idPerusahaan;

    @Column(name = "nama_perusahaan", nullable = false, length = 100)
    private String namaPerusahaan;

    @Column(name = "nama_toko_1", nullable = false, length = 100)
    private String namaToko1;

    @Column(name = "alamat_toko_1", nullable = false, length = 100)
    private String alamatToko1;

    @Column(name = "no_telp_1", nullable = false, length = 13)
    private String noTelp1;

    @Column(name = "nama_toko_2", nullable = false, length = 100)
    private String namaToko2;

    @Column(name = "alamat_toko_2", nullable = false, length = 100)
    private String alamatToko2;

    @Column(name = "no_telp_2", nullable = false, length = 13)
    private String noTelp2;

    @Column(name = "npwp", nullable = false, length = 30)
    private String npwp;

    @Column(name = "bankers", nullable = false, length = 100)
    private String bankers;

    @Column(name = "del_flag", nullable = false)
    private int delFlag;


    public Long getIdPerusahaan() {
        return idPerusahaan;
    }

    public void setIdPerusahaan(Long idPerusahaan) {
        this.idPerusahaan = idPerusahaan;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getNamaToko1() {
        return namaToko1;
    }

    public void setNamaToko1(String namaToko1) {
        this.namaToko1 = namaToko1;
    }

    public String getAlamatToko1() {
        return alamatToko1;
    }

    public void setAlamatToko1(String alamatToko1) {
        this.alamatToko1 = alamatToko1;
    }

    public String getNoTelp1() {
        return noTelp1;
    }

    public void setNoTelp1(String noTelp1) {
        this.noTelp1 = noTelp1;
    }

    public String getNamaToko2() {
        return namaToko2;
    }

    public void setNamaToko2(String namaToko2) {
        this.namaToko2 = namaToko2;
    }

    public String getAlamatToko2() {
        return alamatToko2;
    }

    public void setAlamatToko2(String alamatToko2) {
        this.alamatToko2 = alamatToko2;
    }

    public String getNoTelp2() {
        return noTelp2;
    }

    public void setNoTelp2(String noTelp2) {
        this.noTelp2 = noTelp2;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getBankers() {
        return bankers;
    }

    public void setBankers(String bankers) {
        this.bankers = bankers;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
