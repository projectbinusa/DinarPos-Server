package com.template.eazypos.service.itc;

import com.template.eazypos.dto.CustomerITCDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerITCService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    KecRepository kecRepository;
    @Autowired
    KabKotRepository kabKotRepository;
    @Autowired
    ProvRepository provRepository;
    @Autowired
    SalesmanRepository salesmanRepository;

    public Customer add(CustomerITCDTO itcdto){
        Customer customer = new Customer();
        customer.setNama_customer(itcdto.getNama_customer());
        customer.setKec(kecRepository.findById(itcdto.getId_kec()).orElseThrow(() -> new NotFoundException("Id Kec Not Found")));
        customer.setKabKot(kabKotRepository.findById(itcdto.getId_kabkot()).orElseThrow(() -> new NotFoundException("Id Kab Not Found")));
        customer.setProv(provRepository.findById(itcdto.getId_prov()).orElseThrow(() -> new NotFoundException("Id Prov Not Found")));
        customer.setSalesman(salesmanRepository.findById(itcdto.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Prov Not Found")));
        customer.setAlamat(itcdto.getAlamat());
        customer.setEmail(itcdto.getEmail());
        customer.setTelp(itcdto.getNo_tlp());
        customer.setJml(itcdto.getJml_printer());
        customer.setProyektor(itcdto.getProyektor());
        customer.setInternet(itcdto.getInternet());
        customer.setWeb(itcdto.getWeb());
        customer.setMurid(itcdto.getMurid());
        customer.setKls3(itcdto.getKls3());
        customer.setPc(itcdto.getPc());
        customer.setUnbk(itcdto.getUnbk());
        customer.setJenis(itcdto.getJenis());
        customer.setJurusan(itcdto.getJurusan());
        if (customerRepository.findByEmailOrTelp(itcdto.getEmail(), itcdto.getNo_tlp()).isPresent()){
            throw new BadRequestException("Customer sudah ada");
        }
        return customerRepository.save(customer);
    }

    public Customer put(CustomerITCDTO customerITCDTO , Long id) {
        Customer update = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        update.setAlamat(customerITCDTO.getAlamat());
        update.setEmail(customerITCDTO.getEmail());
        update.setTelp(customerITCDTO.getNo_tlp());
        update.setJml(customerITCDTO.getJml_printer());
        update.setProyektor(customerITCDTO.getProyektor());
        update.setInternet(customerITCDTO.getInternet());
        update.setWeb(customerITCDTO.getWeb());
        update.setMurid(customerITCDTO.getMurid());
        update.setKls3(customerITCDTO.getKls3());
        update.setPc(customerITCDTO.getPc());
        update.setUnbk(customerITCDTO.getUnbk());
        update.setJenis(customerITCDTO.getJenis());
        update.setJurusan(customerITCDTO.getJurusan());
        return customerRepository.save(update);
    }
}
