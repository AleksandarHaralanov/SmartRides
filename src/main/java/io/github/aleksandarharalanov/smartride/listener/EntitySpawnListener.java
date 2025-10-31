package io.github.aleksandarharalanov.smartride.listener;

import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import io.github.aleksandarharalanov.smartride.core.handler.CustomPigSpawner;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class EntitySpawnListener extends EntityListener
{
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        // only pigs, and only natural spawns
        if (event.getCreatureType() == CreatureType.PIG && (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER))
        {
            Location spawnLoc = event.getLocation(); // get location where pig should have spawned
            event.setCancelled(true); // cancel the original spawn
            CustomPigSpawner.spawnAtLocation(new EntityCustomPig(((CraftWorld)spawnLoc.getWorld()).getHandle()), spawnLoc);
        }
    }
}
