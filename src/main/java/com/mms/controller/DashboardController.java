package com.mms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mms.model.Product;
import com.mms.repository.ProductRepository;
import com.mms.repository.PurchaseRepository;
import com.mms.repository.SalesRepository;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private PurchaseRepository purchaseRepo;

    @Autowired
    private SalesRepository salesRepo;

    // ================= DASHBOARD PAGE =================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        // -------- TOTAL PRODUCTS --------
        long totalProducts = productRepo.count();

        // -------- TOTAL STOCK (SUM OF ALL PRODUCT QUANTITY) --------
        long totalStock = productRepo.findAll()
                .stream()
                .mapToLong(Product::getQuantity)
                .sum();

        // -------- TOTAL PURCHASES --------
        long totalPurchases = purchaseRepo.count();

        // -------- TOTAL SALES --------
        long totalSales = salesRepo.count();

        // -------- LOW STOCK PRODUCTS (LESS THAN 5) --------
        List<Product> lowStockProducts = productRepo.findAll()
                .stream()
                .filter(p -> p.getQuantity() < 5)
                .toList();

        // -------- PASS DATA TO VIEW --------
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalStock", totalStock);
        model.addAttribute("totalPurchases", totalPurchases);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("lowStockCount", lowStockProducts.size());

        return "dashboard";
    }
}