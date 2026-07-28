package dev.project.mastersys.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(
    name = "graduations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"modality_id", "name"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graduation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modality_id", nullable = false)
    private Modality modality;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;
}
