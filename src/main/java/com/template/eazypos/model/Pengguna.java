package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabel_pengguna")
public class Pengguna extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pengguna")
    private Long idPengguna;

    @Column(name = "username_pengguna", nullable = false, length = 255)
    private String usernamePengguna;

    @Column(name = "password_pengguna", nullable = false, length = 255)
    private String passwordPengguna;

    @Column(name = "nama_pengguna", nullable = false, length = 255)
    private String namaPengguna;

    @Column(name = "level_pengguna", nullable = false)
    private String levelPengguna;


    @Column(name = "last_login", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public Long getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Long idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getUsernamePengguna() {
        return usernamePengguna;
    }

    public void setUsernamePengguna(String usernamePengguna) {
        this.usernamePengguna = usernamePengguna;
    }

    public String getPasswordPengguna() {
        return passwordPengguna;
    }

    public void setPasswordPengguna(String passwordPengguna) {
        this.passwordPengguna = passwordPengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public String getLevelPengguna() {
        return levelPengguna;
    }

    public void setLevelPengguna(String levelPengguna) {
        this.levelPengguna = levelPengguna;
    }


    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}