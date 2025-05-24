package io.github.aleksandarharalanov.smartride.command.subcommand;

import io.github.aleksandarharalanov.smartride.core.handler.CustomPigButcherer;
import io.github.aleksandarharalanov.smartride.util.auth.AccessUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class ButcherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!AccessUtil.senderHasPermission(sender, "hogrider.staff",
                "[SmartRide] You don't have permission to butcher all rides.")) {
            return true;
        }

        CustomPigButcherer.butcher(sender);

        return true;
    }
}
