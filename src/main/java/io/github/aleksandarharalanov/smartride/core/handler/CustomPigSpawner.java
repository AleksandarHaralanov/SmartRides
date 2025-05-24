package io.github.aleksandarharalanov.smartride.core.handler;

import io.github.aleksandarharalanov.smartride.core.config.ConfigManager;
import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import net.minecraft.server.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

public final class CustomPigSpawner {

    private CustomPigSpawner() {}

    public static void spawn(Player player) {
        Location loc = player.getLocation();
        CraftWorld craftWorld = (CraftWorld) loc.getWorld();
        World nmsWorld = craftWorld.getHandle();
        EntityCustomPig customPig = new EntityCustomPig(nmsWorld);

        if (ConfigManager.spawnSaddle()) {
            customPig.setSaddle(true);
        }

        customPig.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);

        nmsWorld.addEntity(customPig);
        CustomPigManager.addRide(player, customPig);
    }
}
