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
    @Column(name = "id_tt_baru")
    private Long TTBaru;

//    @Column(name = "id_tt_lama", nullable = false)
//    private Long idTTLama;

    @JoinColumn(name = "id_tt_lama", updatable = false)
    private ServiceBarang TTLama;


    @Column(name = "tgl_retur", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
    private Date tanggalRetur;



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


    public Long getTTBaru() {
        return TTBaru;
    }

    public void setTTBaru(Long TTBaru) {
        this.TTBaru = TTBaru;
    }

    public Date getTanggalRetur() {
        return tanggalRetur;
    }

    public void setTanggalRetur(Date tanggalRetur) {
        this.tanggalRetur = tanggalRetur;
    }
}
