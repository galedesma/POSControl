package com.galedesma.poscontrol.domain.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PointOfSale {
    private Integer id;
    private String name;
    private final Map<Integer, Path> paths;

    public PointOfSale(Integer id, String name){
        setId(id);
        setName(name);
        this.paths = new HashMap<>();
    }

    public void setId(Integer id) {
        if (id == null || id < 0){
            throw new IllegalArgumentException("Id must be non-negative");
        }

        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name = name;
    }

    public void addPath(Integer neighborId, Integer cost){
        this.paths.put(neighborId, new Path(this.id, neighborId, cost));
    }

    public void deletePath(Integer neighborId){
        this.paths.remove(neighborId);
    }
}
