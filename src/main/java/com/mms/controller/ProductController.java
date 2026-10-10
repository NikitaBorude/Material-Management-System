package com.mms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mms.model.Product;
import com.mms.repository.ProductRepository;
import com.mms.repository.PurchaseRepository;
import com.mms.repository.SaleItemRepository;

@Controller
public class ProductController {

    private final ProductRepository repo;
    private final PurchaseRepository purchaseRepo;

    @Autowired
    private SaleItemRepository saleItemRepository;

    // =============== CONSTRUCTOR INJECTION ===============
    public ProductController(ProductRepository repo,
                             PurchaseRepository purchaseRepo) {
        this.repo = repo;
        this.purchaseRepo = purchaseRepo;
    }

    // =============== ADD PRODUCT PAGE ===============
    @GetMapping("/product/add")
    public String addProduct() {
        return "add-product";
    }

    // =============== SAVE PRODUCT ===============
    @PostMapping("/product/save")
    public String saveProduct(Product product) {
        repo.save(product);
        return "redirect:/product/add";
    }

    // =============== VIEW PRODUCTS ===============
    @GetMapping("/product/view")
    public String viewProducts(Model model) {
        model.addAttribute("products", repo.findAll());
        return "view-products";
    }

    // =============== DELETE PRODUCT ===============
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {

        if (repo.existsById(id)) {

            // Step 1: Delete related purchases
            purchaseRepo.deleteByProductId(id);

            // Step 2: Delete related sale items
            saleItemRepository.deleteByProduct_Id(id);

            // Step 3: Delete product
            repo.deleteById(id);
        }

        return "redirect:/product/view";
    }

    // =============== EDIT PRODUCT PAGE ===============
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {

        Product product = repo.findById(id).orElse(null);
        model.addAttribute("product", product);

        return "edit-product";
    }

    // =============== UPDATE PRODUCT ===============
    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute Product product) {

        repo.save(product);

        return "redirect:/product/view";
    }
    
}