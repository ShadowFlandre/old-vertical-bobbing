package aubymori.oldverticalbobbing.mixin;

import aubymori.oldverticalbobbing.OldVerticalBobbing;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import aubymori.oldverticalbobbing.CameraPitching;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final Minecraft minecraft;
    @Shadow @Final Camera mainCamera;

    @Inject(
        method = "bobView",
        at = @At(
            ordinal = 1,
            shift = At.Shift.AFTER,
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V"
        )
    )
    private void onBobView(PoseStack poseStack, float f, CallbackInfo callback) {
        if (this.minecraft.getCameraEntity() instanceof Player player) {
            CameraPitching cameraPitching = (CameraPitching)player;
            float pitch = Mth.lerp(f, cameraPitching.getPrevCameraPitch(), cameraPitching.getCameraPitch());

            poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
        }
    }
}
