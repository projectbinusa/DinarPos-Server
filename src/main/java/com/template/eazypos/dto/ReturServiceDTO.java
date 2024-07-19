package com.template.eazypos.dto;

import com.template.eazypos.model.ServiceBarang;

import java.util.Date;

public class ReturServiceDTO {
    private Long id;
    private Date tanggalRetur;
    private ServiceBarangDTO ttBaru;
    private ServiceBarangDTO ttLama;

    public ReturServiceDTO(Long id, Date tanggalRetur,
                           Long idTTLama, String checkerLama, String namaCustomerLama, String namaLama, String alamatLama, String cpLama, int poinLama, String ketLama, String produkLama, String merkLama, String typeLama, String snLama, String keluhanLama, String perlengkapanLama, String penerimaLama, Date tanggalMasukLama, Date tanggalJadiLama, Date tanggalAmbilLama, int biayaSparepartLama, int biayaServiceLama, int totalLama, int estimasiLama, int bmaxLama, String catatanLama, String statusEndLama, String takenLama, String fbLama, String faLama, Date timestampLama,
                           Long idTTBaru, String checkerBaru, String namaCustomerBaru, String namaBaru, String alamatBaru, String cpBaru, int poinBaru, String ketBaru, String produkBaru, String merkBaru, String typeBaru, String snBaru, String keluhanBaru, String perlengkapanBaru, String penerimaBaru, Date tanggalMasukBaru, Date tanggalJadiBaru, Date tanggalAmbilBaru, int biayaSparepartBaru, int biayaServiceBaru, int totalBaru, int estimasiBaru, int bmaxBaru, String catatanBaru, String statusEndBaru, String takenBaru, String fbBaru, String faBaru, Date timestampBaru) {

        this.id = id;
        this.tanggalRetur = tanggalRetur;
        this.ttLama = new ServiceBarangDTO(idTTLama, checkerLama, namaCustomerLama, namaLama, alamatLama, cpLama, poinLama, ketLama, produkLama, merkLama, typeLama, snLama, keluhanLama, perlengkapanLama, penerimaLama, tanggalMasukLama, tanggalJadiLama, tanggalAmbilLama, biayaSparepartLama, biayaServiceLama, totalLama, estimasiLama, bmaxLama, catatanLama, statusEndLama, takenLama, fbLama, faLama, timestampLama);
        this.ttBaru = new ServiceBarangDTO(idTTBaru, checkerBaru, namaCustomerBaru, namaBaru, alamatBaru, cpBaru, poinBaru, ketBaru, produkBaru, merkBaru, typeBaru, snBaru, keluhanBaru, perlengkapanBaru, penerimaBaru, tanggalMasukBaru, tanggalJadiBaru, tanggalAmbilBaru, biayaSparepartBaru, biayaServiceBaru, totalBaru, estimasiBaru, bmaxBaru, catatanBaru, statusEndBaru, takenBaru, fbBaru, faBaru, timestampBaru);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTanggalRetur() {
        return tanggalRetur;
    }

    public void setTanggalRetur(Date tanggalRetur) {
        this.tanggalRetur = tanggalRetur;
    }

    public ServiceBarangDTO getTtBaru() {
        return ttBaru;
    }

    public void setTtBaru(ServiceBarangDTO ttBaru) {
        this.ttBaru = ttBaru;
    }

    public ServiceBarangDTO getTtLama() {
        return ttLama;
    }

    public void setTtLama(ServiceBarangDTO ttLama) {
        this.ttLama = ttLama;
    }
}
