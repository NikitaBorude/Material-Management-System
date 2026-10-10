package com.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mms.model.CustomerReport;

public interface CustomerReportRepository extends JpaRepository<CustomerReport, Integer> {

}
