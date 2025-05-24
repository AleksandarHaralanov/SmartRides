package io.github.aleksandarharalanov.smartride.command.subcommand;

import io.github.aleksandarharalanov.smartride.core.SmartRide;
import io.github.aleksandarharalanov.smartride.util.misc.AboutUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public final class AboutCommand implements CommandExecutor {

    private final SmartRide plugin;

    public AboutCommand(SmartRide plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Contributors: If you've contributed code, you can add your name here to be credited
        List<String> contributors = Arrays.asList();
        AboutUtil.aboutPlugin(sender, plugin, contributors);
        return true;
    }
}
