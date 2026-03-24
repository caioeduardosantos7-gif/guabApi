package com.dashapi.demo.domain.model;

public record DashboardStats(
        StatItem totalProducts,
        StatItem completedOrders,
        StatItem cancelledOrders,
        StatItem bestProducts
) {}
