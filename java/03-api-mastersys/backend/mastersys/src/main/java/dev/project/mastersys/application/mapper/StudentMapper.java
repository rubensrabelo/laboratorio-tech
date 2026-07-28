package dev.project.mastersys.application.mapper;

import dev.project.mastersys.application.dtos.StudentRequestDTO;
import dev.project.mastersys.application.dtos.StudentResponseDTO;
import dev.project.mastersys.domain.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Student student = new Student();
        student.setName(dto.name());
        student.setBirthDate(dto.birthDate());
        student.setGender(dto.gender());
        student.setPhone(dto.phone());
        student.setCellPhone(dto.cellPhone());
        student.setEmail(dto.email());
        student.setObservation(dto.observation());
        student.setAddress(dto.address());
        student.setNumber(dto.number());
        student.setComplement(dto.complement());
        student.setNeighborhood(dto.neighborhood());
        student.setCity(dto.city());
        student.setState(dto.state());
        student.setZipCode(dto.zipCode());
        
        return student;
    }

    public StudentResponseDTO toDto(Student entity) {
        if (entity == null) {
            return null;
        }

        return new StudentResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getBirthDate(),
            entity.getGender(),
            entity.getPhone(),
            entity.getCellPhone(),
            entity.getEmail(),
            entity.getObservation(),
            entity.getAddress(),
            entity.getNumber(),
            entity.getComplement(),
            entity.getNeighborhood(),
            entity.getCity(),
            entity.getState(),
            entity.getZipCode(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public void updateEntityFromDto(StudentRequestDTO dto, Student entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.name());
        entity.setBirthDate(dto.birthDate());
        entity.setGender(dto.gender());
        entity.setPhone(dto.phone());
        entity.setCellPhone(dto.cellPhone());
        entity.setEmail(dto.email());
        entity.setObservation(dto.observation());
        entity.setAddress(dto.address());
        entity.setNumber(dto.number());
        entity.setComplement(dto.complement());
        entity.setNeighborhood(dto.neighborhood());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setZipCode(dto.zipCode());
    }
}
