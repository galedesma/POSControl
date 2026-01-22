package com.galedesma.poscontrol.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Map {
    private final List<PointOfSale> pos;

    public Map(){
        this.pos = new ArrayList<>();
    }

    public PointOfSale addPos(String posName){
        PointOfSale newPos = new PointOfSale(this.pos.size(), posName);
        this.pos.add(newPos);

        return newPos;
    }

    public void addPath(Integer originId, Integer destinationId, Integer weight){
        if (originId < 0 || originId >= this.pos.size()){
            throw new IndexOutOfBoundsException();
        }

        if (destinationId < 0 || destinationId >= this.pos.size()){
            throw new IndexOutOfBoundsException();
        }

        this.pos.get(originId).addPath(destinationId, weight);
        this.pos.get(destinationId).addPath(originId, weight);
    }

    public void removePath(Integer originId, Integer destinationId){
        if (originId < 0 || originId >= this.pos.size()){
            throw new IndexOutOfBoundsException();
        }

        if (destinationId < 0 || destinationId >= this.pos.size()){
            throw new IndexOutOfBoundsException();
        }

        this.pos.get(originId).deletePath(destinationId);
        this.pos.get(destinationId).deletePath(originId);
    }
}
