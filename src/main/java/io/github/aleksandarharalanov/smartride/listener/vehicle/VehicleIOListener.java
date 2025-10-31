package io.github.aleksandarharalanov.smartride.listener.vehicle;

import io.github.aleksandarharalanov.smartride.core.animation.PlayerAnimator;
import io.github.aleksandarharalanov.smartride.core.entity.EntityCustomPig;
import io.github.aleksandarharalanov.smartride.core.validation.EntityValidator;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;

public final class VehicleIOListener extends VehicleListener {

    @Override
    public void onVehicleEnter(VehicleEnterEvent event) {
        Vehicle vehicle = event.getVehicle();
        if (!EntityValidator.isValidCustomPigVehicle(vehicle)) return;

        Player player = (Player) event.getEntered();
        ((EntityCustomPig)vehicle).setRiderHandle(player);
        PlayerAnimator.playArmSwingAnimation(player);
    }

    @Override
    public void onVehicleExit(VehicleExitEvent event) {
        Vehicle vehicle = event.getVehicle();
        if (!EntityValidator.isValidCustomPigVehicle(vehicle)) return;

        Player player = (Player) event.getExited();
        ((EntityCustomPig)vehicle).eject();
        PlayerAnimator.playArmSwingAnimation(player);
    }
}
