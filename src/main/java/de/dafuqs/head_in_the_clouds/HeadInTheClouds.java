package de.dafuqs.head_in_the_clouds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

@Mod(HeadInTheClouds.MODID)
public class HeadInTheClouds {
    public static final String MODID = "head_in_the_clouds";

    public HeadInTheClouds() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static float getCloudHeight(Level level) {
        if (level.isClientSide) {
            return getCloudHeightClient(level);
        } else {
            String regKey = level.dimension().location().toString();
            return Config.worldCloudHeights.getOrDefault(regKey, Float.MAX_VALUE);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static float getCloudHeightClient(Level level) {
        if (level instanceof ClientLevel clientLevel) {
            return clientLevel.effects().getCloudHeight();
        }
        return level.getMinBuildHeight();
    }

    private static final float ADDITIONAL_CLOUD_HEIGHT = 3.0F;
    private static final float GRADIENT_HEIGHT = 6.0F;
    private static final float INVERTED_GRADIENT_HEIGHT = 1.0F / GRADIENT_HEIGHT;

    @OnlyIn(Dist.CLIENT)
    public static float getRainGradient(Level level, float original) {
        Minecraft client = Minecraft.getInstance();
        if (client.getCameraEntity() != null) {
            double playerY = client.getCameraEntity().position().y;
            float cloudY = getCloudHeight(level) + ADDITIONAL_CLOUD_HEIGHT;

            if (playerY < cloudY - GRADIENT_HEIGHT) {
            } else if (playerY < cloudY) {
                return (float) ((cloudY - playerY) * INVERTED_GRADIENT_HEIGHT) * original;
            } else {
                return 0.0F;
            }
        }
        return original;
    }
}
