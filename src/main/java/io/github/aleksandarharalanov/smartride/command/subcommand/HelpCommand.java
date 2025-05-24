package io.github.aleksandarharalanov.smartride.command.subcommand;

import io.github.aleksandarharalanov.smartride.util.log.LogUtil;
import io.github.aleksandarharalanov.smartride.util.misc.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class HelpCommand {

    public static void sendHelp(CommandSender sender) {
        String[] messages = {
                "&bSmartRide commands:",
                "&e/sr &7- Displays this content.",
                "&e/sr about &7- About SmartRide.",
                "&e/sr ride &7 - Summons your pig ride.",
                "&bSmartRide staff commands:",
                "&e/sr reload &7- Reload SmartRide config.",
                "&e/sr butcher &7- Butcher all active rides."
        };

        for (String message : messages) {
            if (sender instanceof Player) {
                sender.sendMessage(ColorUtil.translateColorCodes(message));
            } else {
                LogUtil.logConsoleInfo(message.replaceAll("&.", ""));
            }
        }
    }
}
