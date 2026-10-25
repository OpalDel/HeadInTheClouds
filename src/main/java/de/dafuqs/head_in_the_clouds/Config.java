package de.dafuqs.head_in_the_clouds;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = HeadInTheClouds.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> WORLD_CLOUD_HEIGHTS = BUILDER
            .comment("Cloud height per dimension, formatted as dimension_id=height. Servers need this because cloud height is only known on clients.")
            .defineListAllowEmpty("worldCloudHeights", List.of("minecraft:overworld=192.0"), o -> o instanceof String);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Map<String, Float> worldCloudHeights = new HashMap<>();

    @SubscribeEvent
    static void onConfigEvent(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;

        Map<String, Float> parsed = new HashMap<>();
        for (String entry : WORLD_CLOUD_HEIGHTS.get()) {
            String[] parts = entry.split("=", 2);
            if (parts.length != 2) continue;
            try {
                parsed.put(parts[0].trim(), Float.parseFloat(parts[1].trim()));
            } catch (NumberFormatException ignored) {
            }
        }
        if (parsed.isEmpty()) {
            parsed.put("minecraft:overworld", 192.0F);
        }
        worldCloudHeights = parsed;
    }
}
