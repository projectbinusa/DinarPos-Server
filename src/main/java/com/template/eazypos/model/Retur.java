package com.template.eazypos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retur")
public class Retur extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "id_tt_lama", nullable = false)
//    private Long idTTLama;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt_lama", updatable = false)
    private ServiceBarang TTLama;

//    @Column(name = "id_tt_baru", nullable = false)
//    private Long idTTBaru;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt_baru", updatable = false)
    private ServiceBarang TTBaru;

    @Column(name = "tgl_retur", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalRetur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getIdTTLama() {
//        return idTTLama;
//    }
//
//    public void setIdTTLama(Long idTTLama) {
//        this.idTTLama = idTTLama;
//    }
//
//    public Long getIdTTBaru() {
//        return idTTBaru;
//    }
//
//    public void setIdTTBaru(Long idTTBaru) {
//        this.idTTBaru = idTTBaru;
//    }


    public ServiceBarang getTTLama() {
        return TTLama;
    }

    public void setTTLama(ServiceBarang TTLama) {
        this.TTLama = TTLama;
    }

    public ServiceBarang getTTBaru() {
        return TTBaru;
    }

    public void setTTBaru(ServiceBarang TTBaru) {
        this.TTBaru = TTBaru;
    }

    public Date getTanggalRetur() {
        return tanggalRetur;
    }

    public void setTanggalRetur(Date tanggalRetur) {
        this.tanggalRetur = tanggalRetur;
    }
}
