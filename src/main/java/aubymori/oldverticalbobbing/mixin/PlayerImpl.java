package aubymori.oldverticalbobbing.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import aubymori.oldverticalbobbing.CameraPitching;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerImpl extends LivingEntity implements CameraPitching {
    @Unique private float cameraPitch = 0.0F;
    @Unique private float prevCameraPitch = 0.0F;
    
    private PlayerImpl(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void setCameraPitch(float cameraPitch) {
        this.cameraPitch = cameraPitch;
    }

    @Override
    public void setPrevCameraPitch(float prevCameraPitch) {
        this.prevCameraPitch = prevCameraPitch;
    }

    @Override
    public float getCameraPitch() {
        return this.cameraPitch;
    }

    @Override
    public float getPrevCameraPitch() {
        return this.prevCameraPitch;
    }

    @Inject(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Inventory;tick()V"
        )
    )
    private void onPlayerAiStep(CallbackInfo callback) {
        this.setPrevCameraPitch(this.getCameraPitch());

        double deltaY = this.getDeltaMovement().y;
        float rotation = (float)(Math.atan(-deltaY * 0.20000000298023224D) * 15.0D);
        boolean isGrounded = deltaY < -0.07 && deltaY > -0.08 && !this.getBlockStateOn().isAir();

        if (this.onGround() || this.getHealth() <= 0.0F || isGrounded) {
            rotation = 0.0F;
        }

        float current = this.getCameraPitch();
        this.setCameraPitch(current + ((rotation - current)) * 0.8F);
    }
}
