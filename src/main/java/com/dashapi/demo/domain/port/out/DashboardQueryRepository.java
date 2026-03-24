package com.dashapi.demo.domain.port.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.dashapi.demo.domain.model.BestsellerProduct;
import com.dashapi.demo.domain.model.SalesReportPoint;

public interface DashboardQueryRepository {
    long countCompletedOrders();
    long countCancelledOrders();
    BigDecimal sumSalesBetween(LocalDateTime from, LocalDateTime to);
    List<SalesReportPoint> findSalesSeries(LocalDateTime from, LocalDateTime to, String groupBy);
    List<SalesReportPoint> findProductsSeries(LocalDateTime from, LocalDateTime to, String groupBy);
    List<BestsellerProduct> findBestsellers(int limit);
}
