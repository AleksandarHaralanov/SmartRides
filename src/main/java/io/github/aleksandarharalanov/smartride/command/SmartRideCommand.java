package io.github.aleksandarharalanov.smartride.command;

import io.github.aleksandarharalanov.smartride.core.SmartRide;
import io.github.aleksandarharalanov.smartride.command.subcommand.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public final class SmartRideCommand implements CommandExecutor {

    private final Map<String, CommandExecutor> subcommands = new HashMap<>();

    public SmartRideCommand(SmartRide plugin) {
        subcommands.put("about", new AboutCommand(plugin));
        subcommands.put("butcher", new ButcherCommand());
        subcommands.put("reload", new ReloadCommand());
        subcommands.put("ride", new RideCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            HelpCommand.sendHelp(sender);
            return true;
        }

        CommandExecutor subcommand = subcommands.get(args[0].toLowerCase());
        if (subcommand != null) {
            return subcommand.onCommand(sender, command, label, args);
        }

        HelpCommand.sendHelp(sender);
        return true;
    }
}
