package dav.mod.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemInit {
	
	public static Item APPLE_SAPLING = new BlockItem(BlockInit.APPLE_SAPLING, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BlockInit.APPLE_SAPLING.getRegistryName());
	public static Item GAPPLE_SAPLING = new BlockItem(BlockInit.GAPPLE_SAPLING, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BlockInit.GAPPLE_SAPLING.getRegistryName());
}
