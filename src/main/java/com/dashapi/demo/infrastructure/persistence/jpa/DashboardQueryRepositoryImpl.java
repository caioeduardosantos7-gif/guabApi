package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.BestsellerProduct;
import com.dashapi.demo.domain.model.SalesReportPoint;
import com.dashapi.demo.domain.port.out.DashboardQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DashboardQueryRepositoryImpl implements DashboardQueryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long countCompletedOrders() {
        Long result = (Long) em.createQuery(
                "SELECT COUNT(t) FROM TransactionJpaEntity t WHERE t.status = 'completed'")
                .getSingleResult();
        return result != null ? result : 0L;
    }

    @Override
    public long countCancelledOrders() {
        Long result = (Long) em.createQuery(
                "SELECT COUNT(t) FROM TransactionJpaEntity t WHERE t.status = 'cancelled'")
                .getSingleResult();
        return result != null ? result : 0L;
    }

    @Override
    public BigDecimal sumSalesBetween(LocalDateTime from, LocalDateTime to) {
        BigDecimal result = (BigDecimal) em.createQuery(
                "SELECT COALESCE(SUM(s.total), 0) FROM SaleJpaEntity s WHERE s.createdAt BETWEEN :from AND :to")
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SalesReportPoint> findSalesSeries(LocalDateTime from, LocalDateTime to, String groupBy) {
        List<Object[]> rows = em.createQuery(
                "SELECT DAY(s.createdAt), COALESCE(SUM(s.total), 0) " +
                "FROM SaleJpaEntity s " +
                "WHERE s.createdAt BETWEEN :from AND :to " +
                "GROUP BY DAY(s.createdAt) ORDER BY DAY(s.createdAt)")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
        return rows.stream()
                .map(r -> new SalesReportPoint(((Number) r[0]).intValue(), ((Number) r[1]).doubleValue()))
                .toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SalesReportPoint> findProductsSeries(LocalDateTime from, LocalDateTime to, String groupBy) {
        List<Object[]> rows = em.createQuery(
                "SELECT DAY(s.createdAt), COALESCE(SUM(si.quantity), 0) " +
                "FROM SaleJpaEntity s JOIN s.items si " +
                "WHERE s.createdAt BETWEEN :from AND :to " +
                "GROUP BY DAY(s.createdAt) ORDER BY DAY(s.createdAt)")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
        return rows.stream()
                .map(r -> new SalesReportPoint(((Number) r[0]).intValue(), ((Number) r[1]).doubleValue()))
                .toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BestsellerProduct> findBestsellers(int limit) {
        List<Object[]> rows = em.createQuery(
                "SELECT si.product, SUM(si.quantity) as sold " +
                "FROM SaleItemJpaEntity si " +
                "GROUP BY si.product ORDER BY sold DESC")
                .setMaxResults(limit)
                .getResultList();
        String[] emojis = {"🥇", "🥈", "🥉", "🏅", "🏅", "🏅", "🏅", "🏅", "🏅", "🏅"};
        return java.util.stream.IntStream.range(0, rows.size())
                .mapToObj(i -> new BestsellerProduct(
                        (String) rows.get(i)[0],
                        ((Number) rows.get(i)[1]).longValue(),
                        i < emojis.length ? emojis[i] : "🏅"))
                .toList();
    }
}
