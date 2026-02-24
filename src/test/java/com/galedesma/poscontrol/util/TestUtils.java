package com.galedesma.poscontrol.util;

import com.galedesma.poscontrol.entity.Path;
import com.galedesma.poscontrol.entity.PointOfSale;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class TestUtils {

    @SneakyThrows
    public static PointOfSale createPersistedPOS(Integer id, String name) {
        PointOfSale pos = new PointOfSale();
        pos.setName(name);

        Field idField = PointOfSale.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(pos, id);

        return pos;
    }

    @SneakyThrows
    public static Path createPersistedPath(Integer id, PointOfSale pos1, PointOfSale pos2, Integer cost){
        Path path = new Path();
        path.setPos1(pos1);
        path.setPos2(pos2);
        path.setCost(cost);

        Field idField = Path.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(path, id);

        return path;
    }
}
