package io.github.aleksandarharalanov.smartride.core.config;

import io.github.aleksandarharalanov.smartride.core.SmartRides;

public final class ConfigManager {

    private ConfigManager() {}

    public static boolean playAnimation() {
        return SmartRides.getConfig().getBoolean("misc.play-animation", true);
    }

    public static boolean returnSaddle() {
        return SmartRides.getConfig().getBoolean("saddle.return", true);
    }

    public static boolean spawnSaddle() {
        return SmartRides.getConfig().getBoolean("saddle.spawn", false);
    }

    public static boolean immunityFall() {
        return SmartRides.getConfig().getBoolean("immunity.fall", false);
    }

    public static boolean immunityFire() {
        return SmartRides.getConfig().getBoolean("immunity.fire", false);
    }

    public static boolean immunityDrown() {
        return SmartRides.getConfig().getBoolean("immunity.drown", false);
    }

    public static double modifierSpeed() {
        return SmartRides.getConfig().getDouble("modifier.speed", 0.15F);
    }

    public static double modifierJump() {
        return SmartRides.getConfig().getDouble("modifier.jump", 0.42F);
    }

    public static long autoButcherTimerTicks() {
        return SmartRides.getConfig().getInt("auto-butcher-timer-ticks", 18000);
    }

    public static int interactItemId() {
        return SmartRides.getConfig().getInt("interact-item-id", 296);
    }
}
