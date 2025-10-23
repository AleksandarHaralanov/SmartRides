package io.github.aleksandarharalanov.smartride.core.handler;

import io.github.aleksandarharalanov.smartride.core.SmartRides;
import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import io.github.aleksandarharalanov.smartride.core.validation.EntityValidator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class CustomPigMovement {

    private static final Map<Player, Integer> activeTasks = new HashMap<>();
    private static final Set<Player> movementEnabled = new HashSet<>();

    private CustomPigMovement() {}

    public static void toggleMovementTask(Player player, EntityCustomPig customPig) {
        if (movementEnabled.contains(player)) {
            stopMovementTask(player);
            movementEnabled.remove(player);
        } else {
            startMovementTask(player, customPig);
            movementEnabled.add(player);
        }
    }

    public static void startMovementTask(Player player, EntityCustomPig customPig) {
        stopMovementTask(player);

        int taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SmartRides.getInstance(), () -> {
            if (!player.isOnline() || EntityValidator.getCustomPig(player) == null) {
                stopMovementTask(player);
                movementEnabled.remove(player);
                return;
            }

            Vector direction = player.getEyeLocation().getDirection();
            customPig.setPathToLookDirection(direction);
        }, 0L, 1L);

        activeTasks.put(player, taskId);
    }

    public static void stopMovementTask(Player player) {
        Integer taskId = activeTasks.remove(player);
        if (taskId != null) {
            Bukkit.getServer().getScheduler().cancelTask(taskId);
        }
    }
}
