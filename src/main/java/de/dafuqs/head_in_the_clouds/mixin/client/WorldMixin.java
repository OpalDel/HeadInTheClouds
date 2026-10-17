package de.dafuqs.head_in_the_clouds.mixin.client;

import de.dafuqs.head_in_the_clouds.HeadInTheClouds;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class WorldMixin {

    @Shadow @Final public boolean isClientSide;

    @Inject(method = "getRainLevel(F)F", at = @At("RETURN"), cancellable = true)
    public void clouds$getRainGradient(float delta, CallbackInfoReturnable<Float> cir) {
        if (isClientSide && cir.getReturnValue() > 0) {
            cir.setReturnValue(HeadInTheClouds.getRainGradient((Level) (Object) this, cir.getReturnValue()));
        }
    }
}
