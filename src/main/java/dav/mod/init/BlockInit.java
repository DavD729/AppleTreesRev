package dav.mod.init;

import java.util.ArrayList;
import java.util.List;

import dav.mod.objects.blocks.tree.AppleBlockSapling;
import dav.mod.objects.blocks.tree.BlockApplePlant;
import net.minecraft.block.Block;

public class BlockInit {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block APPLE_SAPLING = new AppleBlockSapling("sapling_apple");
	public static final Block GAPPLE_SAPLING = new AppleBlockSapling("sapling_gapple");
	
	public static final Block APPLE_PLANT = new BlockApplePlant("apple_plant", 0);
	public static final Block GAPPLE_PLANT = new BlockApplePlant("gapple_plant", 1);
}
