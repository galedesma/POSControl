package com.galedesma.poscontrol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "point_of_sale")
@Getter
@NoArgsConstructor
public class PointOfSale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "pos1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Path> pathsFrom = new HashSet<>();
    @OneToMany(mappedBy = "pos2", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Path> pathsTo = new HashSet<>();
}
