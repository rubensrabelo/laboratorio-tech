package dev.project.mastersys.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.project.mastersys.domain.enums.Gender;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Gender gender;

    @Size(max = 30)
    @Column(length = 30)
    private String phone;

    @Size(max = 30)
    @Column(name = "cell_phone", length = 30)
    private String cellPhone;

    @Email
    @Size(max = 150)
    @Column(length = 150)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Size(max = 150)
    @Column(length = 150)
    private String address;

    @Size(max = 20)
    @Column(length = 20)
    private String number;

    @Size(max = 100)
    @Column(length = 100)
    private String complement;

    @Size(max = 100)
    @Column(length = 100)
    private String neighborhood;

    @Size(max = 100)
    @Column(length = 100)
    private String city;

    @Size(max = 2)
    @Column(length = 2)
    private String state;

    @Size(max = 20)
    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
