package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.helper.ModHelper;
import net.fabricmc.example.mixin.BlocksAccessor;
import net.fabricmc.example.mixin.SignTypeInvoker;
import net.fabricmc.example.world.MorldgenGeneration;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SpruceSaplingGenerator;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.TerraBlenderApi;

import java.util.ArrayList;
import java.util.List;

public class ExampleMod implements ModInitializer, TerraBlenderApi {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("maybemorldgen");

	// mod registry
	public static List<ModHelper.Registry.ModRegistryEntry<Block>> blocksToRegister = new ArrayList<>();
	public static List<ModHelper.Registry.ModRegistryEntry<Item>> itemsToRegister = new ArrayList<>();

	// keys
	public static final RegistryKey<Biome> SHRUBLAND = RegistryKey.of(RegistryKeys.BIOME, new Identifier("maybemorldgen", "shrubland"));
	public static final RegistryKey<Biome> TUNDRA = RegistryKey.of(RegistryKeys.BIOME, new Identifier("maybemorldgen", "tundra"));
	public static final RegistryKey<Biome> MIXED_FOREST = RegistryKey.of(RegistryKeys.BIOME, new Identifier("maybemorldgen", "mixed_forest"));
	public static final RegistryKey<Biome> OLD_GROWTH_MIXED_FOREST = RegistryKey.of(RegistryKeys.BIOME, new Identifier("maybemorldgen", "old_growth_mixed_forest"));

	RegistryKey<PlacedFeature> FLOWER_TUNDRA = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("maybemorldgen:flower_tundra"));

	// sign types
	public static class ModdedSignTypes {
		public static final SignType PINE = SignTypeInvoker.registerSignType(SignTypeInvoker.init("pine"));
	}

	// blocks
	// flowers
	public static final Block PINK_GLADIOLUS = new FlowerBlock(StatusEffects.STRENGTH, 9, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));
	public static final Block PURPLE_GLADIOLUS = new FlowerBlock(StatusEffects.STRENGTH, 9, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));
	public static final Block RED_GLADIOLUS = new FlowerBlock(StatusEffects.STRENGTH, 9, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));
	public static final Block PINCUSHION = new TallFlowerBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));
	public static final Block WAX_HEATH = new PlantBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));
	public static final Block ARCTIC_POPPY = new FlowerBlock(((FlowerBlock)Blocks.POPPY).getEffectInStew(), ((FlowerBlock)Blocks.POPPY).getEffectInStewDuration(), FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));
	public static final Block PASQUEFLOWER = new FlowerBlock(StatusEffects.POISON, 9, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ));

	// pine
	public static final Block PINE_LOG = ModHelper.Registry.registerBlockWithItem(
			new PillarBlock(FabricBlockSettings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.SPRUCE_BROWN : MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD)),
			"pine_log");
	public static final Block PINE_WOOD = ModHelper.Registry.registerBlockWithItem(
			new PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD)),
			"pine_wood");
	public static final Block STRIPPED_PINE_LOG = ModHelper.Registry.registerBlockWithItem(
			new PillarBlock(FabricBlockSettings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.SPRUCE_BROWN : MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD)),
			"stripped_pine_log");
	public static final Block STRIPPED_PINE_WOOD = ModHelper.Registry.registerBlockWithItem(
			new PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD)),
			"stripped_pine_wood");
	public static final Block PINE_LEAVES = ModHelper.Registry.registerBlockWithItem(
			new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksAccessor::getCanSpawnOnLeaves).suffocates(ModHelper.Predicates.Block::never).blockVision(ModHelper.Predicates.Block::never)),
			"pine_leaves");
	public static final Block PINE_SAPLING = ModHelper.Registry.registerBlockWithItem(
			new SaplingBlock(new SpruceSaplingGenerator(), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)),
			"pine_sapling");

	public static final Block PINE_PLANKS = ModHelper.Registry.registerBlockWithItem(
			new Block(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)),
			"pine_planks");
	public static final Block PINE_SLAB = ModHelper.Registry.registerBlockWithItem(
			new SlabBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)),
			"pine_slab");
	public static final Block PINE_STAIRS = ModHelper.Registry.registerBlockWithItem(
			new StairsBlock(PINE_PLANKS.getDefaultState(), FabricBlockSettings.copy(PINE_PLANKS)),
			"pine_stairs");

	public static final Block PINE_SIGN = ModHelper.Registry.registerBlockWithItem(
			new SignBlock(FabricBlockSettings.of(Material.WOOD, PINE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), ModdedSignTypes.PINE),
			"pine_sign");
	public static final Block PINE_WALL_SIGN = ModHelper.Registry.registerBlock(
			new WallSignBlock(FabricBlockSettings.of(Material.WOOD, PINE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(PINE_SIGN), ModdedSignTypes.PINE),
			"pine_wall_sign");
	public static final Block PINE_HANGING_SIGN = ModHelper.Registry.registerBlockWithItem(
			new HangingSignBlock(FabricBlockSettings.of(Material.WOOD, PINE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.HANGING_SIGN).requires(FeatureFlags.UPDATE_1_20), ModdedSignTypes.PINE),
			"pine_hanging_sign");
	public static final Block PINE_WALL_HANGING_SIGN = ModHelper.Registry.registerBlock(
			new WallHangingSignBlock(FabricBlockSettings.of(Material.WOOD, PINE_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.HANGING_SIGN).dropsLike(PINE_HANGING_SIGN).requires(FeatureFlags.UPDATE_1_20), ModdedSignTypes.PINE),
			"pine_wall_hanging_sign");
	public static final Block PINE_BUTTON = ModHelper.Registry.registerBlockWithItem(
			new ButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD), 30, true, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON),
			"pine_button");
	public static final Block PINE_PRESSURE_PLATE = ModHelper.Registry.registerBlockWithItem(
			new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, PINE_PLANKS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD), SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON),
			"pine_pressure_plate");
	public static final Block PINE_DOOR = ModHelper.Registry.registerBlockWithItem(
			new DoorBlock(FabricBlockSettings.of(Material.WOOD, PINE_PLANKS.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque(), SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundEvents.BLOCK_WOODEN_DOOR_OPEN),
			"pine_door");
	public static final Block PINE_TRAPDOOR = ModHelper.Registry.registerBlockWithItem(
			new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(ModHelper.Predicates.Block::never), SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN),
			"pine_trapdoor");

	public static final Block PINE_FENCE_GATE = ModHelper.Registry.registerBlockWithItem(
			new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, PINE_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN),
			"pine_fence_gate");
	public static final Block PINE_FENCE = ModHelper.Registry.registerBlockWithItem(
			new FenceBlock(FabricBlockSettings.of(Material.WOOD, PINE_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)),
			"pine_fence");


	// potted plants
	public static final Block POTTED_PINE_SAPLING = new FlowerPotBlock(PINE_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_PINK_GLADIOLUS = new FlowerPotBlock(PINK_GLADIOLUS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_PURPLE_GLADIOLUS = new FlowerPotBlock(PURPLE_GLADIOLUS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_RED_GLADIOLUS = new FlowerPotBlock(RED_GLADIOLUS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_WAX_HEATH = new FlowerPotBlock(WAX_HEATH, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_ARCTIC_POPPY = new FlowerPotBlock(ARCTIC_POPPY, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_PASQUEFLOWER = new FlowerPotBlock(PASQUEFLOWER, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

	boolean init = false;
	boolean needsToRun = false;

	// generator
	MorldgenGeneration gen = new MorldgenGeneration();

	// creative tabs
	static {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.LILY_OF_THE_VALLEY.getDefaultStack(),
				new Block[]{
						PINK_GLADIOLUS,
						PURPLE_GLADIOLUS,
						RED_GLADIOLUS,
						WAX_HEATH,
						PASQUEFLOWER
				}
		));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.PEONY.getDefaultStack(),
				new Block[]{
						PINCUSHION
				}
		));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.POPPY.getDefaultStack(),
				new Block[]{
						ARCTIC_POPPY
				}
		));
		// pine
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> entries.addAfter(Items.SPRUCE_BUTTON.getDefaultStack(),
				new Block[]{
						PINE_LOG,
						PINE_WOOD,
						STRIPPED_PINE_LOG,
						STRIPPED_PINE_WOOD,
						PINE_PLANKS,
						PINE_STAIRS,
						PINE_SLAB,
						PINE_FENCE,
						PINE_FENCE_GATE,
						PINE_DOOR,
						PINE_TRAPDOOR,
						PINE_PRESSURE_PLATE,
						PINE_BUTTON
				}
		));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.SPRUCE_LOG.getDefaultStack(),
				new Block[]{
						PINE_LOG
				}
		));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.SPRUCE_SAPLING.getDefaultStack(),
				new Block[]{
						PINE_SAPLING
				}
		));
	}
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		for (ModHelper.Registry.ModRegistryEntry<Block> bl : blocksToRegister) {
			Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", bl.getIdentifier()), bl.getObject());
		}

		for (ModHelper.Registry.ModRegistryEntry<Item> i : itemsToRegister) {
			Registry.register(Registries.ITEM, new Identifier("maybemorldgen", i.getIdentifier()), i.getObject());
		}

		// blocks
		// pine
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_pine_sapling"), POTTED_PINE_SAPLING);

		// flowers
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "pink_gladiolus"), PINK_GLADIOLUS);
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_pink_gladiolus"), POTTED_PINK_GLADIOLUS);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "pink_gladiolus"), new BlockItem(PINK_GLADIOLUS, new FabricItemSettings()));

		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "purple_gladiolus"), PURPLE_GLADIOLUS);
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_purple_gladiolus"), POTTED_PURPLE_GLADIOLUS);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "purple_gladiolus"), new BlockItem(PURPLE_GLADIOLUS, new FabricItemSettings()));

		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "red_gladiolus"), RED_GLADIOLUS);
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_red_gladiolus"), POTTED_RED_GLADIOLUS);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "red_gladiolus"), new BlockItem(RED_GLADIOLUS, new FabricItemSettings()));

		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "pincushion"), PINCUSHION);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "pincushion"), new BlockItem(PINCUSHION, new FabricItemSettings()));

		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "wax_heath"), WAX_HEATH);
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_wax_heath"), POTTED_WAX_HEATH);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "wax_heath"), new BlockItem(WAX_HEATH, new FabricItemSettings()));

		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "arctic_poppy"), ARCTIC_POPPY);
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_arctic_poppy"), POTTED_ARCTIC_POPPY);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "arctic_poppy"), new BlockItem(ARCTIC_POPPY, new FabricItemSettings()));

		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "pasqueflower"), PASQUEFLOWER);
		Registry.register(Registries.BLOCK, new Identifier("maybemorldgen", "potted_pasqueflower"), POTTED_PASQUEFLOWER);
		Registry.register(Registries.ITEM, new Identifier("maybemorldgen", "pasqueflower"), new BlockItem(PASQUEFLOWER, new FabricItemSettings()));

		BiomeModifications.addFeature(ctx -> ctx.getBiomeKey().equals(BiomeKeys.SNOWY_PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, FLOWER_TUNDRA);
	}

	@Override
	public void onTerraBlenderInitialized()
	{
		// Given we only add two biomes, we should keep our weight relatively low.
		//Regions.register(new TestRegion(new ResourceLocation(MOD_ID, "overworld"), 2));

		// Register our surface rules
		//SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, "maybemorldgen", TestSurfaceRuleData.makeRules());

		// biomes
		// wait until TerraBlender is done first lol
		// I think that's what this does, anyway
		// oOGH I ATE TOO MANY CHRISTMAS COOKIES
		ExampleMod.LOGGER.info("TerraBlender initialized");
		gen.run();
	}


}
