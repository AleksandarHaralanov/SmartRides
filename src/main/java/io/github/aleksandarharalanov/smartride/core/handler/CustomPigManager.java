package io.github.aleksandarharalanov.smartride.core.handler;

import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class CustomPigManager {

    private static final Map<String, EntityCustomPig> activeRides = new HashMap<>();

    private CustomPigManager() {}

    public static EntityCustomPig getActiveRide(Player player) {
        return activeRides.get(player.getName());
    }

    public static boolean hasActiveRide(Player player) {
        return activeRides.containsKey(player.getName());
    }

    public static void addRide(Player player, EntityCustomPig customPig) {
        activeRides.put(player.getName(), customPig);
    }

    public static void removeRide(Player player) {
        activeRides.remove(player.getName());
    }

    public static void removeActiveRides() {
        activeRides.clear();
    }
}
