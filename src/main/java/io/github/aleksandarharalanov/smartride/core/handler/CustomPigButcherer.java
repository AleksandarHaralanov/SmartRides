package io.github.aleksandarharalanov.smartride.core.handler;

import io.github.aleksandarharalanov.smartride.core.SmartRide;
import io.github.aleksandarharalanov.smartride.core.config.ConfigManager;
import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import io.github.aleksandarharalanov.smartride.util.log.LogUtil;
import io.github.aleksandarharalanov.smartride.util.misc.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class CustomPigButcherer {

    private static int butcherTaskId = -1;

    private CustomPigButcherer() {}

    public static void startButcherTask() {
        if (butcherTaskId != -1) {
            Bukkit.getServer().getScheduler().cancelTask(butcherTaskId);
        }

        long timer = ConfigManager.autoButcherTimerTicks();
        if (timer <= 0) {
            LogUtil.logConsoleWarning("[SmartRide] Invalid automatic butcher timer in config. Defaulting to 18000L ticks.");
            timer = 18000L;
        }

        butcherTaskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SmartRide.getInstance(), () -> {
            LogUtil.logConsoleInfo("[SmartRide] Beginning automatic ride butcher task.");
            butcher(null);
        }, 0L, timer);
    }

    public static void butcher(CommandSender sender) {
        Bukkit.getServer().getWorlds().forEach(world -> {
            int count = 0;
            for (Entity entity : world.getLivingEntities()) {
                if (entity instanceof CraftPig) {
                    if (((CraftPig) entity).getHandle() instanceof EntityCustomPig) {
                        entity.remove();
                        count++;
                    }
                }
            }
            CustomPigManager.removeActiveRides();

            LogUtil.logConsoleInfo(String.format("[SmartRide] Butchered %d ride(s) in '%s'.", count, world.getName()));
            if (sender instanceof Player) {
                sender.sendMessage(ColorUtil.translateColorCodes(String.format(
                        "&e[SmartRide] Butchered %d ride(s) in '%s'.", count, world.getName()
                )));
            }
        });
    }

    public static void butcher() {
        butcher(null);
    }
}
