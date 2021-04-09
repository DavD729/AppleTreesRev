package dav.mod.util.handlers;

import dav.mod.init.BlockInit;
import dav.mod.init.ItemInit;
import dav.mod.util.interfaces.IHasModel;
import dav.mod.world.gen.TreeWorldGen;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for(Item item : ItemInit.ITEMS) {
			if(item instanceof IHasModel) {
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : BlockInit.BLOCKS) {
			if(block instanceof IHasModel) {
				((IHasModel)block).registerModels();
			}
		}
	}
	
	@SubscribeEvent
	public static void onLootTableEvent(LootTableLoadEvent event) {
		if (event.getName().toString().equals("minecraft:chests/simple_dungeon")) {
			LootEntry entryA = new LootEntryItem(Item.getItemFromBlock(BlockInit.GAPPLE_SAPLING), 8, 10, new LootFunction[0], new LootCondition[0], "appletreesrev:sapling");
			LootEntry entryB = new LootEntryItem(Items.AIR, 92, 50, new LootFunction[0], new LootCondition[0], "appletreesrev:empty");
			LootPool Pool = new LootPool(new LootEntry[]{entryA, entryB}, new LootCondition[0], new RandomValueRange(3), new RandomValueRange(0,1), "appletreesrev_pool_inject");
			event.getTable().addPool(Pool);
			return;
		}
		
		if (event.getName().toString().equals("minecraft:chests/desert_pyramid")) {
			LootEntry entryA = new LootEntryItem(Item.getItemFromBlock(BlockInit.GAPPLE_SAPLING), 10, 15, new LootFunction[0], new LootCondition[0], "appletreesrev:sapling");
			LootEntry entryB = new LootEntryItem(Items.AIR, 90, 45, new LootFunction[0], new LootCondition[0], "appletreesrev:empty");
			LootPool Pool = new LootPool(new LootEntry[]{entryA, entryB}, new LootCondition[0], new RandomValueRange(3), new RandomValueRange(0,1), "appletreesrev_pool_inject");
			event.getTable().addPool(Pool);
			return;
		}
		
		if (event.getName().toString().equals("minecraft:chests/abandoned_mineshaft")) {
			LootEntry entryA = new LootEntryItem(Item.getItemFromBlock(BlockInit.GAPPLE_SAPLING), 12, 30, new LootFunction[0], new LootCondition[0], "appletreesrev:sapling");
			LootEntry entryB = new LootEntryItem(Items.AIR, 88, 65, new LootFunction[0], new LootCondition[0], "appletreesrev:empty");
			LootPool Pool = new LootPool(new LootEntry[]{entryA, entryB}, new LootCondition[0], new RandomValueRange(3), new RandomValueRange(0,1), "appletreesrev_pool_inject");
			event.getTable().addPool(Pool);
			return;
		}
		
		if (event.getName().toString().equals("minecraft:chests/jungle_temple")) {
			LootEntry entryA = new LootEntryItem(Item.getItemFromBlock(BlockInit.GAPPLE_SAPLING), 30, 25, new LootFunction[0], new LootCondition[0], "appletreesrev:sapling");
			LootEntry entryB = new LootEntryItem(Items.AIR, 70, 45, new LootFunction[0], new LootCondition[0], "appletreesrev:empty");
			LootPool Pool = new LootPool(new LootEntry[]{entryA, entryB}, new LootCondition[0], new RandomValueRange(3), new RandomValueRange(0,2), "appletreesrev_pool_inject");
			event.getTable().addPool(Pool);
			return;
		}
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event) {
		ConfigHandler.registerConfig(event);
		GameRegistry.registerWorldGenerator(new TreeWorldGen(), 0);
	}
	
	public static void InitRegistries(FMLInitializationEvent event) {
		RecipeHandler.registerAll();
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event) {
	}
}
