package com.galedesma.poscontrol.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class POSMap {
    private final Map<Integer, PointOfSale> pos;

    public POSMap(){
        this.pos = new HashMap<>();
    }

    public PointOfSale addPos(Integer id, String posName){
        PointOfSale newPos = new PointOfSale(id, posName);
        this.pos.put(id, newPos);

        return newPos;
    }

    public PointOfSale removePos(Integer id){
        PointOfSale removed = this.pos.remove(id);

        Map<Integer, Path> neighbors = removed.getPaths();

        for (Integer neighborId : new ArrayList<>(neighbors.keySet())){
            removed.deletePath(neighborId);
            this.pos.get(neighborId).deletePath(removed.getId());
        }

        return removed;
    }

    public void addPath(Integer originId, Integer destinationId, Integer weight){
        if (originId < 0){
            throw new IndexOutOfBoundsException();
        }

        if (destinationId < 0){
            throw new IndexOutOfBoundsException();
        }

        this.pos.get(originId).addPath(destinationId, weight);
        this.pos.get(destinationId).addPath(originId, weight);
    }

    public void removePath(Integer originId, Integer destinationId){
        if (originId < 0){
            throw new IndexOutOfBoundsException();
        }

        if (destinationId < 0){
            throw new IndexOutOfBoundsException();
        }

        this.pos.get(originId).deletePath(destinationId);
        this.pos.get(destinationId).deletePath(originId);
    }
}
