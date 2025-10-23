package io.github.aleksandarharalanov.smartride.core.entity;

import io.github.aleksandarharalanov.smartride.core.config.ConfigManager;
import io.github.aleksandarharalanov.smartride.util.log.LogUtil;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.*;

public class EntityCustomPig extends EntityPig {

    private static final Set<Integer> BLOCKS = new HashSet<>(Arrays.asList(
            0, 6, 30, 31, 32, 37, 38, 39, 40, 44, 50, 51, 55, 59,
            65, 66, 68, 69, 70, 72, 75, 76, 77, 78, 83, 90, 92, 96
    ));

    private String riderName;

    private Vector targetDirection = new Vector(0, 0, 0);
    private int jumpCooldown = 0;

    static {
        try {
            Method register = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            register.setAccessible(true);
            register.invoke(null, EntityCustomPig.class, "CustomPig", 90);
        } catch (Exception e) {
            LogUtil.logConsoleSevere(String.format("[SmartRides] Could not register custom pig entity class: %s", e.getMessage()));
        }
    }

    public EntityCustomPig(World world) {
        super(world);
    }

    private long ticksAlive = 0;

    @Override
    protected void c_() {
        ++this.ay;

        if (!hasSaddle() || this.passenger == null) {
            super.c_();
            this.U();
            return;
        }

        ++ticksAlive;

        Player ply = null;
        if (passenger.getBukkitEntity() instanceof Player) {
            ply = (Player) passenger.getBukkitEntity();
            if (ply.getInventory().getItemInHand().getTypeId() == Item.WHEAT.id) {
                if (ticksAlive % 3 == 0) {
                    updateMovementInput(ply);
                }
            } else {
                targetDirection.zero();
            }
        }

        // apply motion toward target direction
        double lerpFactor = this.onGround ? 0.3D : 0.1D;
        this.motX += (targetDirection.getX() - this.motX) * lerpFactor;
        this.motZ += (targetDirection.getZ() - this.motZ) * lerpFactor;

        this.motX *= this.onGround ? 0.91D : 0.98D;
        this.motZ *= this.onGround ? 0.91D : 0.98D;

        // rotate pig to face player yaw
        if (ply != null) {
            this.yaw += wrapAngle((ply.getLocation().getYaw() - this.yaw)) * 0.3F;
            this.pitch = ply.getLocation().getPitch() * 0.5F;
        }
    }

    private float wrapAngle(float angle) {
        while (angle <= -180.0F) angle += 360.0F;
        while (angle > 180.0F) angle -= 360.0F;
        return angle;
    }

    @Override
    protected int j() {
        if (!ConfigManager.spawnSaddle() && ConfigManager.returnSaddle() && this.hasSaddle()) {
            return Item.SADDLE.id;
        }
        return 0;
    }

    @Override
    protected void q() {
        List<ItemStack> loot = new ArrayList<>();
        if (this.j() == Item.SADDLE.id) {
            loot.add(new ItemStack(this.j(), 1));
        }

        CraftEntity entity = (CraftEntity) this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        org.bukkit.World bworld = this.world.getWorld();
        this.world.getServer().getPluginManager().callEvent(event);

        for (ItemStack stack : event.getDrops()) {
            bworld.dropItemNaturally(entity.getLocation(), stack);
        }
    }

    @Override
    public void O() {
        this.motY = ConfigManager.modifierJump();
    }

    @Override
    protected void a(float f) {
        if (!ConfigManager.immunityFall()) {
            super.a(f);
        }
    }

    @Override
    public void R() {
        super.R();

        if (ConfigManager.immunityFire()) {
            this.fireProof = true;
            this.fireTicks = 0;
        }

        if (ConfigManager.immunityDrown()) {
            this.airTicks = this.maxAirTicks;
        }
    }

    @Override
    public void v() {
        if (jumpCooldown > 0) jumpCooldown--;

        if (this.passenger instanceof EntityHuman) {
            if (this.onGround && isBumpingIntoBlock() && jumpCooldown == 0) {
                this.O();
                jumpCooldown = 10; // prevent spam jumping
            }
        }

        super.v();
    }

    private boolean isBumpingIntoBlock() {
        float yawRadians = (float) Math.toRadians(this.yaw);
        int dx = (int) Math.round(-Math.sin(yawRadians));
        int dz = (int) Math.round(Math.cos(yawRadians));

        int x = MathHelper.floor(this.locX + dx);
        int y = MathHelper.floor(this.locY);
        int z = MathHelper.floor(this.locZ + dz);

        int blockId = this.world.getTypeId(x, y, z);
        int aboveBlockId = this.world.getTypeId(x, y + 1, z);

        boolean isWater = (blockId == Block.WATER.id || blockId == Block.STATIONARY_WATER.id);
        boolean hasSolidBlockInFront = !BLOCKS.contains(blockId) && !isWater;
        boolean hasSpaceAbove = BLOCKS.contains(aboveBlockId);

        return hasSolidBlockInFront && hasSpaceAbove;
    }

    public void setPathToLookDirection(Vector direction) {
        if (direction == null) {
            targetDirection.setX(0);
            targetDirection.setZ(0);
            return;
        }

        double moveSpeed = ConfigManager.modifierSpeed();
        if (this.ad() || this.ae()) moveSpeed *= 0.15F;

        // Normalize direction to prevent speed scaling by look magnitude
        direction.normalize();
        targetDirection.setX(direction.getX() * moveSpeed);
        targetDirection.setZ(direction.getZ() * moveSpeed);
    }

    private void updateMovementInput(Player ply) {
        Vector dir = ply.getEyeLocation().getDirection();
        if (dir == null) {
            targetDirection.zero();
            return;
        }

        double moveSpeed = ConfigManager.modifierSpeed();
        if (this.ad() || this.ae()) moveSpeed *= 0.15F;

        dir.normalize();
        targetDirection.setX(dir.getX() * moveSpeed);
        targetDirection.setZ(dir.getZ() * moveSpeed);
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderName() {
        return this.riderName;
    }
}
