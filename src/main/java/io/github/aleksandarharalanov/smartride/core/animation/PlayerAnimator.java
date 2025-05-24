package io.github.aleksandarharalanov.smartride.core.animation;

import io.github.aleksandarharalanov.smartride.core.config.ConfigManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet18ArmAnimation;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class PlayerAnimator {

    private PlayerAnimator() {}

    public static void playArmSwingAnimation(Player player) {
        if (ConfigManager.playAnimation()) {
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.netServerHandler.sendPacket(new Packet18ArmAnimation(nmsPlayer, 1));
        }
    }
}
