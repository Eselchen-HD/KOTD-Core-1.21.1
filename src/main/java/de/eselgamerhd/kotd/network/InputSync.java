package de.eselgamerhd.kotd.network;

import java.util.UUID;
import java.util.WeakHashMap;

@SuppressWarnings("unused")
public class InputSync {

    private static final WeakHashMap<UUID, Boolean> SPRINT_STATE = new WeakHashMap<>();

    public static Boolean getSprintState(UUID uuid) {
        return SPRINT_STATE.getOrDefault(uuid, false);
    }

    public static void setSprintState(UUID uuid, Boolean sprintState) {
        SPRINT_STATE.put(uuid, sprintState);
    }

}
