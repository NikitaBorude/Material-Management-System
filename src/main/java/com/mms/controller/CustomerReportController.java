package com.mms.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mms.model.CustomerReport;
import com.mms.repository.CustomerReportRepository;

@Controller
@RequestMapping("/reports")
public class CustomerReportController {

    @Autowired
    private CustomerReportRepository reportRepository;

    // Show Add Form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("report", new CustomerReport());
        return "add-report";
    }

    // Save Report
    @PostMapping("/save")
    public String saveReport(@ModelAttribute CustomerReport report) {
        report.setReportDate(LocalDate.now());
        report.setStatus("Pending");
        reportRepository.save(report);
        return "redirect:/reports/view";
    }

    // View All Reports
    @GetMapping("/view")
    public String viewReports(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        return "view-reports";
    }

    // Update Status
    @GetMapping("/resolve/{id}")
    public String resolveReport(@PathVariable Integer id) {
        CustomerReport report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            report.setStatus("Resolved");
            reportRepository.save(report);
        }
        return "redirect:/reports/view";
    }
}
