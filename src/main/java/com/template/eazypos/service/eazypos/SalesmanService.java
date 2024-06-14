package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesmanService {
    @Autowired
    private SalesmanRepository salesmanRepository;

    // Menambahkan seorang salesman baru
    public Salesman add(Salesman salesman){
        salesman.setDelFlag(1);
        return salesmanRepository.save(salesman);
    }

    // Mengambil data salesman berdasarkan ID
    public Salesman get(Long id) {
        return salesmanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Mengambil semua data salesman
    public List<Salesman> getAll(){
        return salesmanRepository.findAll();
    }

    // Mengedit data salesman berdasarkan ID
    public Salesman edit(Salesman salesman , Long id){
        Salesman update = salesmanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setAlamatSalesman(salesman.getAlamatSalesman());
        update.setNamaSalesman(salesman.getNamaSalesman());
        update.setNoTelpSalesman(salesman.getNoTelpSalesman());
        return salesmanRepository.save(update);
    }

    // Menghapus data salesman berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            salesmanRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    // Mengambil data salesman dengan sistem paginasi
    public Page<Salesman> getAllWithPagination(Long page, Long limit, String sort, String search) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
        if (search != null && !search.isEmpty()) {
            return salesmanRepository.findAllByKeyword(search, pageable);
        } else {
            return salesmanRepository.findAll(pageable);
        }
    }
}
