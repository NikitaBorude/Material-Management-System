package com.mms.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mms.model.Product;
import com.mms.model.SaleItem;
import com.mms.model.Sales;
import com.mms.repository.ProductRepository;
import com.mms.repository.SalesRepository;

@Controller
public class BillingController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesRepository salesRepository;

    // ================= BILLING PAGE =================
    @GetMapping("/billing")
    public String billingPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "billing";
    }

    // ================= SAVE BILL =================
    @Transactional
    @PostMapping("/save-bill")
    public String saveBill(
            @RequestParam String customerName,
            @RequestParam String paymentMode,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {

        // Create Sales Object
        Sales sales = new Sales();

        String invoiceNo = "INV-" + System.currentTimeMillis();
        sales.setInvoiceNumber(invoiceNo);
        sales.setCustomerName(customerName);
        sales.setSaleDate(LocalDate.now());
        sales.setPaymentMode(paymentMode);

        double subtotal = 0;
        List<SaleItem> items = new ArrayList<>();

        // Fetch Product
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return "redirect:/billing";
        }

        int qty = quantity;

        // ========== STOCK CHECK ==========
        if (product.getQuantity() >= qty) {

            double price = product.getPrice();
            double itemTotal = price * qty;

            // Reduce stock
            product.setQuantity(product.getQuantity() - qty);
            productRepository.save(product);

            // Create Sale Item
            SaleItem item = new SaleItem();
            item.setSales(sales);
            item.setProduct(product);
            item.setQuantity(qty);
            item.setPrice(price);
            item.setSubtotal(itemTotal);

            subtotal += itemTotal;
            items.add(item);

        } else {
            return "redirect:/billing?error=stock";
        }

        // ========== GST & TOTAL ==========
        double gst = subtotal * 0.18;
        double total = subtotal + gst;

        sales.setSubtotal(subtotal);
        sales.setGstAmount(gst);
        sales.setTotalAmount(total);
        sales.setItems(items);

        // Save Sales
        Sales savedSales = salesRepository.save(sales);

        return "redirect:/invoice/" + savedSales.getId();
    }

    // ================= SHOW INVOICE =================
    @GetMapping("/invoice/{id}")
    public String showInvoice(@PathVariable Integer id, Model model) {

        Sales sale = salesRepository.findById(id).orElse(null);

        if (sale == null) {
            return "redirect:/billing";
        }

        model.addAttribute("sale", sale);

        return "invoice";
    }
}