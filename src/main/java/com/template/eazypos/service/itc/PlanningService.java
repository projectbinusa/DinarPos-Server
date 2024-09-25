package com.template.eazypos.service.itc;

import com.template.eazypos.dto.PlanningDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Planning;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.PlanningRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository planningRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Planning> getAll() {
        return planningRepository.findAll();
    }

    public List<Planning> getByTanggal(Date tglAwal, Date tglAkhir) {
        return planningRepository.findByTgl(tglAwal, tglAkhir);
    }

    public List<Planning> getByCustomer(Long id) {
        return planningRepository.findByIdCustomer(id);
    }

    public Planning add(PlanningDTO dto) {
        Planning planning = new Planning();
        planning.setBertemu(dto.getBertemu());
        planning.setCustomer(customerRepository.findById(dto.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer Not Found")));
        planning.setKet(dto.getKet());
        planning.setSalesman(salesmanRepository.findByNama(dto.getNama()).orElseThrow(() -> new NotFoundException("Nama Salesman Not Found")));
        planning.setTgl(dto.getTgl());
        planning.setTimestamp(new Date());
        return planningRepository.save(planning);
    }

    public Planning edit(Long id, PlanningDTO planningDTO) {
        Planning update = planningRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setBertemu(planningDTO.getBertemu());
        update.setCustomer(customerRepository.findById(planningDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer Not Found")));
        update.setKet(planningDTO.getKet());
        update.setSalesman(salesmanRepository.findByNama(planningDTO.getNama()).orElseThrow(() -> new NotFoundException("Id Salesman Not Found")));
        update.setTgl(planningDTO.getTgl());
        return planningRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            planningRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
    public Planning getById(Long id){
        return planningRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public List<Planning> getByTgl(Date date){
        return planningRepository.findByTglPlanning(date);
    }
    public List<Planning> getByTglAndSalesman(Date date, Long id) {
        return planningRepository.findByTglPlanningAndSalesman(date, id);
    }
    public Page<Planning> getAllWithPagination(Long page, Long limit, String sort , Date date , Long id) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
            return planningRepository.findByTglPlanningAndSalesman(date , id,pageable);

    }
    public List<Planning> getPlanning(Date date , Long id){
        return planningRepository.findPlanningsWithoutKunjungan(date , id);
    }
    public List<Planning> getPlanningByDateAndSalesman(Date tglKunjungan, Long idSalesman) {
        return planningRepository.findByTglAndSalesman(tglKunjungan, idSalesman);
    }

    public List<Planning> getBySalesmanGroupByDate(Long id) {
        return planningRepository.findBySalesmanGroupByDate(id);
    }

    public List<Object[]> getPlanningAndSalesmanWithMaxTgl() {
        return planningRepository.findPlanningAndSalesmanWithMaxTgl();
    }

    public List<Planning> getTglBetweenAndSalesman(Date tglAwal, Date tglAkhir, Long idSalesman) {
        return planningRepository.findByTglBetweenAndSalesman(tglAwal, tglAkhir, idSalesman);
    }
}
