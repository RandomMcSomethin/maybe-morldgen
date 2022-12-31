package net.fabricmc.example.helper;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ModHelper {
    // preset predicates
    public static class Predicates {
        public static class Block {
            public static boolean never(BlockState state, BlockView view, BlockPos pos) {
                return false;
            }
            public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) { return false; }

            public static boolean always(BlockState state, BlockView view, BlockPos pos) {
                return true;
            }

        }
    }

    // help with registry stuff
    public static class Registry {
        public static class ModRegistryEntry<T> {
            T object;
            String identifier;
            public ModRegistryEntry(T object, String identifier) {
                this.object = object;
                this.identifier = identifier;
            }

            public T getObject() {
                return object;
            }

            public String getIdentifier() {
                return identifier;
            }
        }

        // for registering a new block
        public static Block registerBlock(Block block, String identifier) {
            ModRegistryEntry<Block> bl = new ModRegistryEntry<Block>(block, identifier);
            ExampleMod.blocksToRegister.add(bl);
            return block;
        }


        // for registering a new item
        public static Item registerItem(Item item, String identifier) {
            ModRegistryEntry<Item> i = new ModRegistryEntry<Item>(item, identifier);
            ExampleMod.itemsToRegister.add(i);
            return item;
        }

        // for registering a new blockitem
        public static Block registerBlockWithItem(Block block, String identifier) {
            registerBlock(block, identifier);
            registerItem(new BlockItem(block, new FabricItemSettings()), identifier);
            return block;
        }
        public static Block registerBlockWithItem(Block block, String identifier, FabricItemSettings settings) {
            registerBlock(block, identifier);
            registerItem(new BlockItem(block, settings), identifier);
            return block;
        }
        public static Block registerBlockWithItem(Block block, String identifier, Item item, String itemIdentifier) {
            registerBlock(block, identifier);
            registerItem(item, itemIdentifier);
            return block;
        }
    }
}
