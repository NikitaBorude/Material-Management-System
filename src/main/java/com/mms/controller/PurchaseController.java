package com.mms.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mms.model.Purchase;
import com.mms.repository.*;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SupplierRepository supplierRepo;

    // ---------------- ADD PAGE ----------------
    @GetMapping("/purchase/add")
    public String addPurchasePage(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("suppliers", supplierRepo.findAll());
        return "add-purchase";
    }

    // ---------------- SAVE ----------------
    @PostMapping("/purchase/save")
    public String savePurchase(@RequestParam int productId,
                               @RequestParam int supplierId,
                               @RequestParam int quantity) {

        Purchase p = new Purchase();
        p.setProduct(productRepo.findById(productId).orElse(null));
        p.setSupplier(supplierRepo.findById(supplierId).orElse(null));
        p.setQuantity(quantity);
        p.setPurchaseDate(LocalDate.now());

        purchaseRepo.save(p);

        // STOCK IN
        var product = p.getProduct();
        product.setQuantity(product.getQuantity() + quantity);
        productRepo.save(product);

        return "redirect:/purchase/add";
    }

    // ---------------- VIEW ----------------
    @GetMapping("/purchase/view")
    public String viewPurchase(Model model){
        model.addAttribute("purchases", purchaseRepo.findAll());
        return "view-purchase";
    }

    // ---------------- DELETE ----------------
    @GetMapping("/purchase/delete/{id}")
    public String deletePurchase(@PathVariable int id) {

        if(purchaseRepo.existsById(id)) {
            purchaseRepo.deleteById(id);
        }

        return "redirect:/purchase/view";
    }

    // ---------------- EDIT PAGE ----------------
    @GetMapping("/purchase/edit/{id}")
    public String editPurchase(@PathVariable int id, Model model) {

        Purchase purchase = purchaseRepo.findById(id).orElse(null);

        model.addAttribute("purchase", purchase);
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("suppliers", supplierRepo.findAll());

        return "edit-purchase";
    }

    // ---------------- UPDATE ----------------
    @PostMapping("/purchase/update")
    public String updatePurchase(@RequestParam int id,
                                 @RequestParam int productId,
                                 @RequestParam int supplierId,
                                 @RequestParam int quantity) {

        Purchase p = purchaseRepo.findById(id).orElse(null);

        if(p != null) {
            p.setProduct(productRepo.findById(productId).orElse(null));
            p.setSupplier(supplierRepo.findById(supplierId).orElse(null));
            p.setQuantity(quantity);
            purchaseRepo.save(p);
        }

        return "redirect:/purchase/view";
    }
}