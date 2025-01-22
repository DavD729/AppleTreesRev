package net.dav.appletreesrev.init;

import net.dav.appletreesrev.objects.block.BlockApplePlant;
import net.dav.appletreesrev.objects.block.BlockAppleSapling;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<>();

	public static final Block SAPLING = new BlockAppleSapling("sapling");
	public static final Block PLANT = new BlockApplePlant("plant");
}
