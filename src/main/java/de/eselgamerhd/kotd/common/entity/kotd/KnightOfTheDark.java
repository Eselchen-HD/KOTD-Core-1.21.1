package de.eselgamerhd.kotd.common.entity.kotd;

import de.eselgamerhd.kotd.common.init.KotdSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.minecraft.network.chat.Component.literal;

public class KnightOfTheDark extends Monster implements GeoEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(KnightOfTheDark.class, EntityDataSerializers.INT);
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("move.walk");
    protected static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenLoop("death");
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            literal("§4§lKnight Of The Dark"),
            BossEvent.BossBarColor.PINK,
            BossEvent.BossBarOverlay.PROGRESS
    );

    public KnightOfTheDark(EntityType<? extends KnightOfTheDark> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 75;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 1.2, 32.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.5, true));

        this.goalSelector.addGoal(5, new BreathAirGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(7, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 500d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 120d)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        this.setVariant(KOTDVariant.byId(this.random.nextInt(KOTDVariant.values().length)));
        return data;
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant")) {
            this.entityData.set(VARIANT, tag.getInt("Variant"));
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }
    public KOTDVariant getVariant() {
        return KOTDVariant.byId(this.getTypeVariant() & 255);
    }
    public void setVariant(KOTDVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return literal("Knight Of The Dark");
    }

    @Override
    public boolean isPushedByFluid(@NotNull FluidType type) {
        return false;
    }

    /* Animation System */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Main", 5, this::mainController));
    }
    private <E extends KnightOfTheDark> PlayState mainController(AnimationState<E> event) {
        if (this.dead || this.getHealth() <= 0) {
            return event.setAndContinue(DEATH_ANIM);
        } else if (event.isMoving()) {
            return event.setAndContinue(WALK_ANIM);
        } else {
            return event.setAndContinue(IDLE_ANIM);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    /* Sound System */
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BEACON_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.ANVIL_STEP;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return KotdSounds.KOTD_STEP.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, BlockState block) {
        @SuppressWarnings("deprecation") SoundType soundtype = block.getSoundType();
        this.playSound(getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
    }

    /* Boss Bar System */
    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false; // Keep this boss in peaceful mode
    }
}