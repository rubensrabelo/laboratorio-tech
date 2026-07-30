package dev.project.mastersys.application.services;

import dev.project.mastersys.application.dtos.StudentFilterRequestDTO;
import dev.project.mastersys.application.dtos.StudentRequestDTO;
import dev.project.mastersys.application.dtos.StudentResponseDTO;
import dev.project.mastersys.application.mapper.StudentMapper;
import dev.project.mastersys.domain.Student;
import dev.project.mastersys.exceptions.domain.BusinessRuleException;
import dev.project.mastersys.exceptions.domain.DataIntegrityViolationException;
import dev.project.mastersys.exceptions.domain.ResourceNotFoundException;
import dev.project.mastersys.infra.repositories.StudentRepository;
import dev.project.mastersys.infra.specifications.StudentSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> findAll(StudentFilterRequestDTO filter, Pageable pageable) {
        Specification<Student> spec = 
                StudentSpecification.withFilters(filter);
                
        return studentRepository.findAll(spec, pageable)
                .map(studentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO findById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

    @Transactional
    public StudentResponseDTO create(StudentRequestDTO dto) {
        if (dto.email() != null && studentRepository.findByEmail(dto.email()).isPresent()) {
            throw new BusinessRuleException("Email address is already registered.");
        }

        Student student = studentMapper.toEntity(dto);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
        
        if (dto.email() != null) {
            Optional<Student> existingStudent = studentRepository.findByEmail(dto.email());
            if (existingStudent.isPresent() && !existingStudent.get().getId().equals(id)) {
                throw new BusinessRuleException("Email address is already registered by another student.");
            }
        }
        
        studentMapper.updateEntityFromDto(dto, student);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student", id);
        }
        try {
            studentRepository.deleteById(id);
            studentRepository.flush();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("This record cannot be deleted because it is being used by another resource.");
        }
    }
}
