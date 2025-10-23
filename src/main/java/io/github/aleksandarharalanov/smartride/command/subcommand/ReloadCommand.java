package io.github.aleksandarharalanov.smartride.command.subcommand;

import io.github.aleksandarharalanov.smartride.core.SmartRides;
import io.github.aleksandarharalanov.smartride.core.handler.CustomPigButcherer;
import io.github.aleksandarharalanov.smartride.util.auth.AccessUtil;
import io.github.aleksandarharalanov.smartride.util.log.LogUtil;
import io.github.aleksandarharalanov.smartride.util.misc.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!AccessUtil.senderHasPermission(sender, "hogrider.staff",
                "[SmartRides] You don't have permission to reload the config.")) {
            return true;
        }

        if (sender instanceof Player) {
            sender.sendMessage(ColorUtil.translateColorCodes("&a[SmartRides] Configurations reloaded."));
        }
        LogUtil.logConsoleInfo("[SmartRides] Configurations reloaded.");

        SmartRides.getConfig().loadAndLog();
        CustomPigButcherer.startButcherTask();

        return true;
    }
}
