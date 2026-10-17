package de.dafuqs.head_in_the_clouds.mixin;

import de.dafuqs.head_in_the_clouds.HeadInTheClouds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class WorldMixin {

    @Inject(method = "isRainingAt(Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    public void clouds$hasRain(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (pos.getY() > HeadInTheClouds.getCloudHeight((Level) (Object) this)) {
            cir.setReturnValue(false);
        }
    }
}
