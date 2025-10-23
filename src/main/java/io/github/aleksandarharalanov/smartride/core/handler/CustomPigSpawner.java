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
        EntityCustomPig customPig = new EntityCustomPig(((CraftWorld)loc.getWorld()).getHandle());
        spawnAtLocation(customPig, loc);
        CustomPigManager.addRide(player, customPig);
    }

    public static void spawnAtLocation(EntityCustomPig customPig, Location loc)
    {
        CraftWorld craftWorld = (CraftWorld) loc.getWorld();
        World nmsWorld = craftWorld.getHandle();

        if (ConfigManager.spawnSaddle()) {
            customPig.setSaddle(true);
        }

        customPig.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        nmsWorld.addEntity(customPig);
    }
}
