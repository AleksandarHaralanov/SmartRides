package io.github.aleksandarharalanov.smartride.command.subcommand;

import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import io.github.aleksandarharalanov.smartride.core.handler.CustomPigManager;
import io.github.aleksandarharalanov.smartride.util.auth.AccessUtil;
import io.github.aleksandarharalanov.smartride.util.misc.ColorUtil;
import io.github.aleksandarharalanov.smartride.core.handler.CustomPigSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class RideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (AccessUtil.denyIfNotPlayer(sender, null)) return true;
        if (!AccessUtil.senderHasPermission(sender, "hogrider.ride",
                "[SmartRide] You don't have permission to summon a ride.")) {
            return true;
        }

        Player player = (Player) sender;
        if (CustomPigManager.hasActiveRide(player)) {
            EntityCustomPig activeRide = CustomPigManager.getActiveRide(player);
            if (activeRide != null) {
                activeRide.getBukkitEntity().remove();
                CustomPigManager.removeRide(player);
            }
            player.sendMessage(ColorUtil.translateColorCodes("&e[SmartRide] Previous ride removed."));
        }
        CustomPigSpawner.spawn(player);
        player.sendMessage(ColorUtil.translateColorCodes("&a[SmartRide] Ride summoned."));

        return true;
    }
}
