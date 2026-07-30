package dev.project.mastersys.api.controllers;

import dev.project.mastersys.api.docs.GymReportDoc;
import dev.project.mastersys.application.dtos.MonthlyBillingResponseDTO;
import dev.project.mastersys.application.dtos.OpenInvoicesResponseDTO;
import dev.project.mastersys.application.dtos.StudentsByCityResponseDTO;
import dev.project.mastersys.application.services.GymReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class GymReportController implements GymReportDoc {

    private final GymReportService gymReportService;

    public GymReportController(GymReportService gymReportService) {
        this.gymReportService = gymReportService;
    }

    @GetMapping("/students-by-city")
    public ResponseEntity<List<StudentsByCityResponseDTO>> getStudentsCountByCity() {
        List<StudentsByCityResponseDTO> report = gymReportService.getStudentsCountByCity();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly-billing")
    public ResponseEntity<List<MonthlyBillingResponseDTO>> getMonthlyBillingSummary() {
        List<MonthlyBillingResponseDTO> report = gymReportService.getMonthlyBillingSummary();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/open-invoices")
    public ResponseEntity<List<OpenInvoicesResponseDTO>> getOpenInvoicesReport() {
        List<OpenInvoicesResponseDTO> report = gymReportService.getOpenInvoicesReport();
        return ResponseEntity.ok(report);
    }
}
