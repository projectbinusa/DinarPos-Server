package com.template.eazypos.controller;

import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.service.itc.admin.PoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/poin")
@CrossOrigin(origins = "*")
public class PoinController {
    @Autowired
    private PoinService poinService;

    @GetMapping("/month/{month}")
    public List<PoinHistory> getPoinByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate month) {
        return poinService.getPoinByMonth(month);
    }
}
