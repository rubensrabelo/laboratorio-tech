package dev.project.restaurant.domain;

import dev.project.restaurant.domain.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer number;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Integer capacity = 4;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TableStatus status = TableStatus.AVAILABLE;
}
