package dev.project.mastersys.api.docs;

import dev.project.mastersys.application.dtos.MonthlyBillingResponseDTO;
import dev.project.mastersys.application.dtos.OpenInvoicesResponseDTO;
import dev.project.mastersys.application.dtos.StudentsByCityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Tag(name = "Gym Reports", description = "Endpoints for academic and financial statistics")
public interface GymReportDoc {

    @Operation(summary = "Get students count grouped by city", description = "Retrieves a breakdown of how many gym students are registered in each city")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report generated successfully")
    })
    ResponseEntity<List<StudentsByCityResponseDTO>> getStudentsCountByCity();

    @Operation(summary = "Get monthly billing summary", description = "Retrieves total financial revenue from paid invoices grouped by year and month")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report generated successfully")
    })
    ResponseEntity<List<MonthlyBillingResponseDTO>> getMonthlyBillingSummary();

    @Operation(summary = "Get open invoices report", description = "Lists all customer invoices that are currently unpaid, ordered by proximity to the due date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report generated successfully")
    })
    ResponseEntity<List<OpenInvoicesResponseDTO>> getOpenInvoicesReport();
}
