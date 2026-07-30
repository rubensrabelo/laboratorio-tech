package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.EnrollmentInvoice;
import dev.project.mastersys.infra.projections.MonthlyBillingProjection;
import dev.project.mastersys.infra.projections.OpenInvoicesProjection;
import dev.project.mastersys.infra.projections.StudentsByCityProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface GymReportRepository extends Repository<EnrollmentInvoice, Long> {

    @Query(value = "SELECT city AS city, COUNT(*) AS quantity FROM students GROUP BY city", nativeQuery = true)
    List<StudentsByCityProjection> findStudentsCountByCity();

    @Query(value = "SELECT TO_CHAR(payment_date, 'YYYY-MM') AS month, SUM(amount) AS total " +
            "FROM enrollment_invoices " +
            "WHERE status = 'PAID' " +
            "GROUP BY TO_CHAR(payment_date, 'YYYY-MM') " +
            "ORDER BY month DESC", nativeQuery = true)
    List<MonthlyBillingProjection> findMonthlyBillingSummary();

    @Query(value = "SELECT ei.enrollment_id AS enrollment_id, s.name AS student_name, " +
            "ei.due_date AS due_date, ei.amount AS amount " +
            "FROM enrollment_invoices ei " +
            "INNER JOIN enrollments e ON ei.enrollment_id = e.id " +
            "INNER JOIN students s ON e.student_id = s.id " +
            "WHERE ei.status = 'OPEN' " +
            "ORDER BY ei.due_date ASC", nativeQuery = true)
    List<OpenInvoicesProjection> findOpenInvoicesReport();
}
