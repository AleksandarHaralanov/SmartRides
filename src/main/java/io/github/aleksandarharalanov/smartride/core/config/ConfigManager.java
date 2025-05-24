package io.github.aleksandarharalanov.smartride.core.config;

import io.github.aleksandarharalanov.smartride.core.SmartRide;

public final class ConfigManager {

    private ConfigManager() {}

    public static boolean playAnimation() {
        return SmartRide.getConfig().getBoolean("misc.play-animation", true);
    }

    public static boolean returnSaddle() {
        return SmartRide.getConfig().getBoolean("saddle.return", true);
    }

    public static boolean spawnSaddle() {
        return SmartRide.getConfig().getBoolean("saddle.spawn", false);
    }

    public static boolean immunityFall() {
        return SmartRide.getConfig().getBoolean("immunity.fall", false);
    }

    public static boolean immunityFire() {
        return SmartRide.getConfig().getBoolean("immunity.fire", false);
    }

    public static boolean immunityDrown() {
        return SmartRide.getConfig().getBoolean("immunity.drown", false);
    }

    public static double modifierSpeed() {
        return SmartRide.getConfig().getDouble("modifier.speed", 0.15F);
    }

    public static double modifierJump() {
        return SmartRide.getConfig().getDouble("modifier.jump", 0.42F);
    }

    public static long autoButcherTimerTicks() {
        return SmartRide.getConfig().getInt("auto-butcher-timer-ticks", 18000);
    }

    public static int interactItemId() {
        return SmartRide.getConfig().getInt("interact-item-id", 296);
    }
}
