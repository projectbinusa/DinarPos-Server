package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Retur;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.ReturRepository;
import com.template.eazypos.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReturService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReturRepository returRepository;

    @Autowired
    private CustomerRepository customerRepository;



    public ServiceBarang retur(Long id , AddServiceDTO serviceDTO){
        ServiceBarang service = new ServiceBarang();
        service.setCustomer(customerRepository.findById(serviceDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer tidak dinemukan")));
        service.setKet(serviceDTO.getKet());
        service.setProduk(serviceDTO.getJenis_produk());
        service.setMerk(serviceDTO.getMerek());
        service.setType(serviceDTO.getType());
        service.setSn(serviceDTO.getNo_seri());
        service.setPerlengkapan(serviceDTO.getPerlengkapan());
        service.setKeluhan(serviceDTO.getKeluhan());
        service.setPenerima(serviceDTO.getPenerima());
        service.setTanggalMasuk(serviceDTO.getTanggal_masuk());
        service.setBmax(serviceDTO.getBiaya_maximal());
        service.setEstimasi(serviceDTO.getEstimasi_biaya());
        service.setChecker(serviceDTO.getChecker());
        service.setCp(serviceDTO.getCp());
        service.setStatusEnd("N_A");
        service.setAlamat(serviceDTO.getAlamat());
        service.setNama(serviceDTO.getNama());
        ServiceBarang serviceBarang = serviceRepository.save(service);

        Retur retur = new Retur();
        retur.setTanggalRetur(new Date());
        retur.setTTLama(serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Old Not Found")));
        retur.setTTBaru(serviceBarang);
        returRepository.save(retur);

        return serviceBarang;
    }

    public List<Retur> getAll() {
        return returRepository.findAll();
    }

    public List<ServiceBarang> getServiceReady(String tglAwal, String tglAkhir) {
        return serviceRepository.findByStatusEndContainingAndTakenAndTglMasukBetween("%READY%", "Y", tglAwal, tglAkhir);
    }

    public List<ServiceBarang> getServiceNA(String tglAwal, String tglAkhir) {
        return serviceRepository.findByStatusEndAndTglMasukBetween("N_A", tglAwal, tglAkhir);
    }

    public List<ServiceBarang> getServiceByStatusAndTanggalMasuk(String status, String tglAwal, String tglAkhir) {
        return serviceRepository.findByStatusAndTanggalMasukBetween(status, tglAwal, tglAkhir);
    }

    public List<ServiceBarang> getServiceRetur(String tglAwal, String tglAkhir) {
        return serviceRepository.findServiceRetur(tglAwal, tglAkhir);
    }

    public List<ServiceBarang> getServiceCancel(String tglAwal, String tglAkhir) {
        return serviceRepository.findServiceCansel(tglAwal, tglAkhir);
    }

    public List<ServiceBarang> getServiceNotif(String tglAwal, String tglAkhir) {
        return serviceRepository.findServicesNotTakenAndOlderThanOneWeek();
    }

    public List<ServiceBarang> getTakenServices(String tglAwal, String tglAkhir) {
        return serviceRepository.findTakenServices(tglAwal, tglAkhir);
    }

}
