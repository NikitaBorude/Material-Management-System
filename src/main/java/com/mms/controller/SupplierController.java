package com.mms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mms.model.Supplier;
import com.mms.repository.SupplierRepository;

@Controller
public class SupplierController {

    @Autowired
    private SupplierRepository repo;

    // ================= ADD SUPPLIER PAGE =================
    @GetMapping("/supplier/add")
    public String addSupplierPage(Model model) {
        model.addAttribute("supplier", new Supplier());  // 🔥 IMPORTANT
        return "add-supplier";
    }

    // ================= SAVE SUPPLIER =================
    @PostMapping("/supplier/save")
    public String saveSupplier(Supplier supplier) {

        repo.save(supplier);

        return "redirect:/supplier/add";
    }

    // ================= VIEW SUPPLIERS =================
    @GetMapping("/supplier/view")
    public String viewSupplier(Model model) {

        model.addAttribute("list", repo.findAll());

        return "view-supplier";
    }

    // ================= DELETE SUPPLIER =================
    @GetMapping("/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable int id) {

        if (repo.existsById(id)) {
            repo.deleteById(id);
        }

        return "redirect:/supplier/view";
    }

    // ================= EDIT SUPPLIER PAGE =================
    @GetMapping("/supplier/edit/{id}")
    public String editSupplier(@PathVariable int id, Model model) {

        Supplier supplier = repo.findById(id).orElse(null);

        model.addAttribute("supplier", supplier);

        return "edit-supplier";
    }

    // ================= UPDATE SUPPLIER =================
    @PostMapping("/supplier/update")
    public String updateSupplier(@ModelAttribute Supplier supplier) {

        repo.save(supplier);

        return "redirect:/supplier/view";
    }
    
    // =================TOGGLE ACTIVE/DEACTIVE=================
    @GetMapping("/supplier/toggle/{id}")
    public String toggleStatus(@PathVariable Integer id) {
        Supplier s = repo.findById(id).orElse(null);
        if (s != null) {
            s.setActive(!s.isActive());
            repo.save(s);
        }
        return "redirect:/supplier/view";
    }
}