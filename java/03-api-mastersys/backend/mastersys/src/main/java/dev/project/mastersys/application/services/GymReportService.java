package dev.project.mastersys.application.services;

import dev.project.mastersys.application.dtos.MonthlyBillingResponseDTO;
import dev.project.mastersys.application.dtos.OpenInvoicesResponseDTO;
import dev.project.mastersys.application.dtos.StudentsByCityResponseDTO;
import dev.project.mastersys.infra.repositories.GymReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GymReportService {

    private final GymReportRepository gymReportRepository;

    public GymReportService(GymReportRepository gymReportRepository) {
        this.gymReportRepository = gymReportRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentsByCityResponseDTO> getStudentsCountByCity() {
        return gymReportRepository.findStudentsCountByCity().stream()
                .map(p -> new StudentsByCityResponseDTO(p.getCity(), p.getQuantity()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MonthlyBillingResponseDTO> getMonthlyBillingSummary() {
        return gymReportRepository.findMonthlyBillingSummary().stream()
                .map(p -> new MonthlyBillingResponseDTO(p.getMonth(), p.getTotal()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OpenInvoicesResponseDTO> getOpenInvoicesReport() {
        return gymReportRepository.findOpenInvoicesReport().stream()
                .map(p -> new OpenInvoicesResponseDTO(
                        p.getEnrollmentId(), 
                        p.getStudentName(), 
                        p.getDueDate(), 
                        p.getAmount()
                ))
                .toList();
    }
}
