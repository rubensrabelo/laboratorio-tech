package dev.project.mastersys.api.docs;

import dev.project.mastersys.application.dtos.StudentFilterRequestDTO;
import dev.project.mastersys.application.dtos.StudentRequestDTO;
import dev.project.mastersys.application.dtos.StudentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Students", description = "Endpoints for managing gym students")
public interface StudentDoc {

    @Operation(summary = "Find all students with dynamic filters", description = "Retrieves a paginated list of students based on specifications")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<StudentResponseDTO>> findAll(StudentFilterRequestDTO filter, Pageable pageable);

    @Operation(summary = "Find student by ID", description = "Retrieves a single student by their unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    ResponseEntity<StudentResponseDTO> getById(Long id); // Ajustado para corresponder ao Controller

    @Operation(summary = "Create a new student", description = "Registers a new student into the gym system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student successfully created"),
        @ApiResponse(responseCode = "400", description = "Business rule violation or invalid data")
    })
    ResponseEntity<StudentResponseDTO> create(StudentRequestDTO dto);

    @Operation(summary = "Update an existing student", description = "Updates student information by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid data or email already taken"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    ResponseEntity<StudentResponseDTO> update(Long id, StudentRequestDTO dto);

    @Operation(summary = "Delete a student", description = "Removes a student record from the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student successfully deleted"),
        @ApiResponse(responseCode = "400", description = "Data integrity violation (student is linked to other resources)"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    ResponseEntity<Void> delete(Long id);
}
