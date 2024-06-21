package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hutang")
public class Hutang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_transakasi_beli", nullable = false, updatable = false)
    private TransaksiBeli transaksiBeli;

    @Column(name = "hutang")
    private String  hutang;

    @Column(name = "pelunasan")
    private String  pelunasan = "0";

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date date; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransaksiBeli getTransaksiBeli() {
        return transaksiBeli;
    }

    public void setTransaksiBeli(TransaksiBeli transaksiBeli) {
        this.transaksiBeli = transaksiBeli;
    }

    public String getHutang() {
        return hutang;
    }

    public void setHutang(String hutang) {
        this.hutang = hutang;
    }

    public String getPelunasan() {
        return pelunasan;
    }

    public void setPelunasan(String pelunasan) {
        this.pelunasan = pelunasan;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
