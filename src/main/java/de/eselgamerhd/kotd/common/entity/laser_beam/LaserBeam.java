package de.eselgamerhd.kotd.common.entity.laser_beam;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LaserBeam extends Projectile {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(LaserBeam.class, EntityDataSerializers.FLOAT);

    public LaserBeam(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return EntityDimensions.scalable(this.getPickRadius() * 2.0F, this.getPickRadius() * 2.0F);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        builder.define(DATA_RADIUS, 0F);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
        if (DATA_RADIUS.equals(pKey)) {
            this.refreshDimensions();
            if (getRadius() < .1f)
                this.discard();
        }
        super.onSyncedDataUpdated(pKey);
    }
    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }
    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        pCompound.putFloat("Radius", this.getRadius());
        super.addAdditionalSaveData(pCompound);
    }
    public void setRadius(float pRadius) {
        if (!this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 24));
        }
    }
    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        if (pCompound.getInt("Radius") > 0)
            this.setRadius(pCompound.getFloat("Radius"));
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(Vec3.ZERO);
        if (this.tickCount > 60) {
            this.discard();
        }
    }
}
