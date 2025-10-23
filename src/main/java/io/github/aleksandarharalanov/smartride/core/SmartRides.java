package io.github.aleksandarharalanov.smartride.core;

import io.github.aleksandarharalanov.smartride.command.SmartRideCommand;
import io.github.aleksandarharalanov.smartride.core.handler.CustomPigButcherer;
import io.github.aleksandarharalanov.smartride.listener.player.PlayerInteractListener;
import io.github.aleksandarharalanov.smartride.listener.vehicle.VehicleIOListener;
import io.github.aleksandarharalanov.smartride.util.config.ConfigUtil;
import io.github.aleksandarharalanov.smartride.util.log.UpdateUtil;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.aleksandarharalanov.smartride.util.log.LogUtil;

public class SmartRides extends JavaPlugin {

    private static ConfigUtil config;
    private static SmartRides plugin;

    @Override
    public void onEnable() {
        UpdateUtil.checkAvailablePluginUpdates(this, "https://api.github.com/repos/AleksandarHaralanov/SmartRide/releases/latest");

        plugin = this;

        config = new ConfigUtil(this, "config.yml");
        config.load();

        PluginManager pM = getServer().getPluginManager();

        final PlayerInteractListener pIL = new PlayerInteractListener();
        pM.registerEvent(Event.Type.PLAYER_INTERACT, pIL, Event.Priority.Lowest, this);

        final VehicleIOListener vIOL = new VehicleIOListener();
        pM.registerEvent(Event.Type.VEHICLE_ENTER, vIOL, Event.Priority.Lowest, this);
        pM.registerEvent(Event.Type.VEHICLE_EXIT, vIOL, Event.Priority.Lowest, this);

        final SmartRideCommand command = new SmartRideCommand(this);
        getCommand("smartride").setExecutor(command);

        CustomPigButcherer.startButcherTask();

        LogUtil.logConsoleInfo(String.format("[%s] v%s Enabled.", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        CustomPigButcherer.butcher();
        LogUtil.logConsoleInfo(String.format("[%s] v%s Disabled.", getDescription().getName(), getDescription().getVersion()));
    }

    public static SmartRides getInstance() {
        return plugin;
    }

    public static ConfigUtil getConfig() {
        return config;
    }
}