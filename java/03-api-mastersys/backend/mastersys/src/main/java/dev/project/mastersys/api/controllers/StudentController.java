package dev.project.mastersys.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.project.mastersys.api.docs.StudentDoc;
import dev.project.mastersys.application.dtos.StudentFilterRequestDTO;
import dev.project.mastersys.application.dtos.StudentRequestDTO;
import dev.project.mastersys.application.dtos.StudentResponseDTO;
import dev.project.mastersys.application.services.StudentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("students")
public class StudentController implements StudentDoc {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<StudentResponseDTO>> findAll(
            StudentFilterRequestDTO filter,
            org.springframework.data.domain.Pageable pageable) {

        Page<StudentResponseDTO> page = studentService.findAll(filter, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.findById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody @Valid StudentRequestDTO dto) {
        StudentResponseDTO createdStudent = studentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id, @RequestBody @Valid StudentRequestDTO dto) {
        StudentResponseDTO updatedStudent = studentService.update(id, dto);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
