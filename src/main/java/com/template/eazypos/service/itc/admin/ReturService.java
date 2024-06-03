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
        Customer customer = customerRepository.findById(serviceDTO.getId_customer()).get();
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
        service.setCp(customer.getTelp());
        service.setStatusEnd("N_A");
        service.setAlamat(customer.getAlamat());
        service.setNama(customer.getNama_customer());
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

}
