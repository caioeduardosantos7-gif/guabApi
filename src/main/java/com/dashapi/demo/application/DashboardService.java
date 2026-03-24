package com.dashapi.demo.application;

import com.dashapi.demo.domain.model.BestsellerProduct;
import com.dashapi.demo.domain.model.DashboardStats;
import com.dashapi.demo.domain.model.SalesReport;
import com.dashapi.demo.domain.model.StatItem;
import com.dashapi.demo.domain.port.in.DashboardUseCase;
import com.dashapi.demo.domain.port.out.DashboardQueryRepository;
import com.dashapi.demo.domain.port.out.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardService implements DashboardUseCase {

    private final ProductRepository productRepository;
    private final DashboardQueryRepository dashboardQueryRepository;

    public DashboardService(ProductRepository productRepository,
                            DashboardQueryRepository dashboardQueryRepository) {
        this.productRepository = productRepository;
        this.dashboardQueryRepository = dashboardQueryRepository;
    }

    @Override
    public DashboardStats getStats() {
        long totalProducts = productRepository.count();
        long completed = dashboardQueryRepository.countCompletedOrders();
        long cancelled = dashboardQueryRepository.countCancelledOrders();

        return new DashboardStats(
                new StatItem("totalProducts", String.valueOf(totalProducts), "0", true),
                new StatItem("completedOrders", String.valueOf(completed), "0", true),
                new StatItem("cancelledOrders", String.valueOf(cancelled), "0", false),
                new StatItem("bestProducts", "—", "0", true)
        );
    }

    @Override
    public SalesReport getSalesReport(String period, int month) {
        int year = Year.now().getValue();
        LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime to = from.plusMonths(1).minusSeconds(1);

        // previous period for delta calculation
        LocalDateTime prevFrom = from.minusMonths(1);
        LocalDateTime prevTo = from.minusSeconds(1);

        BigDecimal current = dashboardQueryRepository.sumSalesBetween(from, to);
        BigDecimal previous = dashboardQueryRepository.sumSalesBetween(prevFrom, prevTo);

        BigDecimal delta = current.subtract(previous);
        BigDecimal deltaPercent = previous.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : delta.divide(previous, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        return new SalesReport(
                current,
                delta,
                deltaPercent.setScale(2, RoundingMode.HALF_UP),
                dashboardQueryRepository.findSalesSeries(from, to, period),
                dashboardQueryRepository.findProductsSeries(from, to, period)
        );
    }

    @Override
    public List<BestsellerProduct> getBestsellers() {
        return dashboardQueryRepository.findBestsellers(10);
    }
}
