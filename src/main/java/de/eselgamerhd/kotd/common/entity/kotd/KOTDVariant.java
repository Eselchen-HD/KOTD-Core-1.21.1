package de.eselgamerhd.kotd.common.entity.kotd;

import java.util.Arrays;
import java.util.Comparator;

public enum KOTDVariant {
    NORMAL(0),
    PINK(1),
    GREEN(2);

    private static final KOTDVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(KOTDVariant::getId)).toArray(KOTDVariant[]::new);
    private final int id;

    KOTDVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static KOTDVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
