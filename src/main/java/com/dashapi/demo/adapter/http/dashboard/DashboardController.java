package com.dashapi.demo.adapter.http.dashboard;

import com.dashapi.demo.domain.model.BestsellerProduct;
import com.dashapi.demo.domain.model.DashboardStats;
import com.dashapi.demo.domain.model.SalesReport;
import com.dashapi.demo.domain.port.in.DashboardUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardUseCase dashboardUseCase;

    public DashboardController(DashboardUseCase dashboardUseCase) {
        this.dashboardUseCase = dashboardUseCase;
    }

    @GetMapping("/stats")
    public DashboardStats stats() {
        return dashboardUseCase.getStats();
    }

    @GetMapping("/sales-report")
    public SalesReport salesReport(
            @RequestParam(defaultValue = "monthly") String period,
            @RequestParam(defaultValue = "1") int month) {
        return dashboardUseCase.getSalesReport(period, month);
    }

    @GetMapping("/bestsellers")
    public List<BestsellerProduct> bestsellers() {
        return dashboardUseCase.getBestsellers();
    }
}
