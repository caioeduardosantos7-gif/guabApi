package com.dashapi.demo.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record SalesReport(
        BigDecimal totalSales,
        BigDecimal deltaSales,
        BigDecimal deltaPercent,
        List<SalesReportPoint> transactionsSeries,
        List<SalesReportPoint> productsSeries
) {}
