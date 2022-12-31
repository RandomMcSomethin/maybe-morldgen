package net.fabricmc.example.world;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.fabricmc.example.ExampleMod;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

import java.util.function.Consumer;

public class MorldgenGeneration extends Region implements Runnable, TerraBlenderApi {
    public MorldgenGeneration() {
        super(new Identifier("maybemorldgen", "overworld"), RegionType.OVERWORLD, 13);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addBiomeSimilar(mapper, BiomeKeys.BEACH, ExampleMod.SHRUBLAND);
        this.addBiomeSimilar(mapper, BiomeKeys.SNOWY_PLAINS, ExampleMod.TUNDRA);
        this.addBiomeSimilar(mapper, BiomeKeys.FOREST, ExampleMod.MIXED_FOREST);
        this.addBiomeSimilar(mapper, BiomeKeys.OLD_GROWTH_PINE_TAIGA, ExampleMod.OLD_GROWTH_MIXED_FOREST);
    }

    @Override
    public void run() {
        ExampleMod.LOGGER.info("Registering biome generation");
        Regions.register(this);
    }
}
