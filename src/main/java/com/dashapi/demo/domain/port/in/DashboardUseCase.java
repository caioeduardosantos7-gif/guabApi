package com.dashapi.demo.domain.port.in;

import java.util.List;

import com.dashapi.demo.domain.model.BestsellerProduct;
import com.dashapi.demo.domain.model.DashboardStats;
import com.dashapi.demo.domain.model.SalesReport;

public interface DashboardUseCase {
    DashboardStats getStats();
    SalesReport getSalesReport(String period, int month);
    List<BestsellerProduct> getBestsellers();
}
