package com.template.eazypos.service.itc;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ServiceBarang addService(AddServiceDTO serviceDTO){
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
        return serviceRepository.save(service);
    }

}

