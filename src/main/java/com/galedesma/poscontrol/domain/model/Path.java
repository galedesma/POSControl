package com.galedesma.poscontrol.domain.model;

import lombok.Getter;


@Getter
public class Path {
    private Integer origin;
    private Integer destination;
    private Integer cost;

    public Path(Integer origin, Integer destination, Integer cost){
        setOrigin(origin);
        setDestination(destination);
        setCost(cost);
    }

    public void setOrigin(Integer origin){
        validateNonNegative(origin, "origin");
        this.origin = origin;
    }

    public void setDestination(Integer destination){
        validateNonNegative(destination, "destination");
        this.destination = destination;
    }

    public void setCost(Integer cost){
        validateNonNegative(cost, "cost");
        if (origin.equals(destination)){
            cost = 0;
        }
        this.cost = cost;
    }

    private void validateNonNegative(Integer value, String fieldName){
        if (value == null || value < 0){
            throw new IllegalArgumentException(fieldName + "must be non-negative");
        }
    }
}
