package dev.project.mastersys.infra.projections;

import java.math.BigDecimal;

public interface MonthlyBillingProjection {

    String getMonth();
    BigDecimal getTotal();
}
