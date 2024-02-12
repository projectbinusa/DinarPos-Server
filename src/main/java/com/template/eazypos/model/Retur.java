package com.template.eazypos.model;

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
    private Service TTLama;

//    @Column(name = "id_tt_baru", nullable = false)
//    private Long idTTBaru;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_tt_baru", updatable = false)
    private Service TTBaru;

    @Column(name = "tgl_retur", nullable = false)
    @Temporal(TemporalType.DATE)
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


    public Service getTTLama() {
        return TTLama;
    }

    public void setTTLama(Service TTLama) {
        this.TTLama = TTLama;
    }

    public Service getTTBaru() {
        return TTBaru;
    }

    public void setTTBaru(Service TTBaru) {
        this.TTBaru = TTBaru;
    }

    public Date getTanggalRetur() {
        return tanggalRetur;
    }

    public void setTanggalRetur(Date tanggalRetur) {
        this.tanggalRetur = tanggalRetur;
    }
}
