package com.mms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mms.repository.SalesRepository;

@Controller
public class SalesController {

    @Autowired
    private SalesRepository salesRepository;

    // ================= VIEW SALES =================
    @GetMapping("/view-sales")
    public String viewSales(Model model) {

        model.addAttribute("list", salesRepository.findAll());

        return "view-sales";
    }

    // ================= DELETE SALE =================
    @GetMapping("/delete-sale/{id}")
    public String deleteSale(@PathVariable int id) {

        if (salesRepository.existsById(id)) {
            salesRepository.deleteById(id);
        }

        return "redirect:/view-sales";
    }
}