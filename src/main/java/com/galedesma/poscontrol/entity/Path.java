package com.galedesma.poscontrol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "paths",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"pos1_id", "pos2_id"})
        }
)
@Getter
@NoArgsConstructor
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pos1_id", nullable = false)
    private PointOfSale pos1;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pos2_id", nullable = false)
    private PointOfSale pos2;
    @Setter
    @Column(nullable = false)
    private Integer cost;

    @PrePersist
    @PreUpdate
    public void normalizeNodes() {
        if (pos1.getId() > pos2.getId()) {
            PointOfSale temp = pos1;
            pos1 = pos2;
            pos2 = temp;
        }
    }
}
