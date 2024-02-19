package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.eazypos.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/salesman")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesmanController {
    @Autowired
    private SalesmanService salesmanService;

    @PostMapping("/add")
    public CommonResponse<Salesman> add(@RequestBody Salesman salesman){
        return ResponseHelper.ok( salesmanService.add(salesman));
    }
    @GetMapping("/{id}")
    public CommonResponse <Salesman> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( salesmanService.get(id));
    }
    @GetMapping
    public CommonResponse<List<Salesman>> getAll(){
        return ResponseHelper.ok( salesmanService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Salesman> put(@PathVariable("id") Long id , @RequestBody Salesman salesman){
        return ResponseHelper.ok( salesmanService.edit(salesman , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( salesmanService.delete(id));
    }
}
