package com.galedesma.poscontrol.util;

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
}
