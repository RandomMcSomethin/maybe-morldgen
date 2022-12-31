package net.fabricmc.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MorldgenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PINK_GLADIOLUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_PINK_GLADIOLUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PURPLE_GLADIOLUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_PURPLE_GLADIOLUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.RED_GLADIOLUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_RED_GLADIOLUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PINCUSHION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.WAX_HEATH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_WAX_HEATH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.ARCTIC_POPPY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_ARCTIC_POPPY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PASQUEFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_PASQUEFLOWER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.POTTED_PINE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PINE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PINE_TRAPDOOR, RenderLayer.getCutout());
    }
}
