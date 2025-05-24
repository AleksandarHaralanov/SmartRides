package io.github.aleksandarharalanov.smartride.listener.player;

import io.github.aleksandarharalanov.smartride.core.config.ConfigManager;
import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import io.github.aleksandarharalanov.smartride.core.animation.PlayerAnimator;
import io.github.aleksandarharalanov.smartride.core.handler.CustomPigMovement;
import io.github.aleksandarharalanov.smartride.core.validation.EntityValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public final class PlayerInteractListener extends PlayerListener {

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        EntityCustomPig pig = EntityValidator.getCustomPig(player);
        if (pig == null) return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        if (player.getItemInHand().getTypeId() != ConfigManager.interactItemId()) return;

        PlayerAnimator.playArmSwingAnimation(player);
        CustomPigMovement.toggleMovementTask(player, pig);
    }
}
