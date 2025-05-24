package io.github.aleksandarharalanov.smartride.core.validation;

import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;

public final class EntityValidator {

    private EntityValidator() {}

    public static EntityCustomPig getCustomPig(Player player) {
        if (!isValidCustomPigVehicle(player)) return null;
        return (EntityCustomPig) ((CraftPig) player.getVehicle()).getHandle();
    }

    public static boolean isValidCustomPigVehicle(Player player) {
        if (!player.isInsideVehicle()) return false;
        return isValidCustomPigVehicle(player.getVehicle());
    }

    public static boolean isValidCustomPigVehicle(Vehicle vehicle) {
        return isEntityCustomPig(vehicle);
    }

    public static boolean isEntityCustomPig(Entity entity) {
        if (!(entity instanceof CraftPig)) return false;
        return ((CraftPig) entity).getHandle() instanceof EntityCustomPig;
    }
}
