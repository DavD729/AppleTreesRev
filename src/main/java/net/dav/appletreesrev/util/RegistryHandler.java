package net.dav.appletreesrev.util;

import net.dav.appletreesrev.init.BlockInit;
import net.dav.appletreesrev.init.ItemInit;
import net.dav.appletreesrev.world.gen.AppleTreeWorldGen;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		ItemInit.ITEMS.forEach((item) -> {
			if(item instanceof IHasModel) {
				((IHasModel)item).registerModel();
			}
		});
		
		BlockInit.BLOCKS.forEach((block) -> {
			if(block instanceof IHasModel) {
				((IHasModel)block).registerModel();
			}
		});
	}

	@SubscribeEvent
	public static void onLootTableEvent(LootTableLoadEvent event) {
		String name = event.getName().toString();
        switch (name) {
            case "minecraft:chests/simple_dungeon":
                RegistryHandler.modifyLootTable(event.getTable(), 8, 10, 92, 50, 1);
                return;
            case "minecraft:chests/desert_pyramid":
                RegistryHandler.modifyLootTable(event.getTable(), 10, 15, 90, 45, 1);
                return;
            case "minecraft:chests/abandoned_mineshaft":
                RegistryHandler.modifyLootTable(event.getTable(), 12, 30, 88, 65, 1);
                return;
            case "minecraft:chests/jungle_temple":
                RegistryHandler.modifyLootTable(event.getTable(), 30, 25, 70, 45, 2);
                break;
        }
    }

	private static void modifyLootTable(LootTable table, int weight, int quality, int airWeight, int airQuality, int limitRange) {
		LootEntry baseEntry = new LootEntryItem(Item.getItemFromBlock(BlockInit.SAPLING), weight, quality,
				new LootFunction[] { new SetMetadata(new LootCondition[0], new RandomValueRange(1, 1)) }, new LootCondition[0], "appletreesrev:sapling");
		LootEntry airEntry = new LootEntryItem(Items.AIR, airWeight, airQuality, new LootFunction[0], new LootCondition[0], "appletreesrev:empty");
		LootPool pool = new LootPool(new LootEntry[]{ baseEntry, airEntry }, new LootCondition[0], new RandomValueRange(3), new RandomValueRange(0,limitRange), "appletreesrev_pool_inject");
		table.addPool(pool);
	}

	public static void preInitRegistries(FMLPreInitializationEvent event) {
		ConfigHandler.registerConfig(event);
		GameRegistry.registerWorldGenerator(new AppleTreeWorldGen(), 0);
	}

	public static void InitRegistries(FMLInitializationEvent event) {
		RecipeHandler.registerAll();
	}
}
