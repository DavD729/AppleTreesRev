package dav.mod.init;

import dav.mod.Main;
import dav.mod.objects.blocks.tree.ApplePlantBlock;
import dav.mod.objects.blocks.tree.AppleSaplingBlock;
import dav.mod.objects.blocks.tree.CustomTree;
import dav.mod.world.gen.TreeWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {
	
	public static Block APPLE_PLANT = new ApplePlantBlock(Block.Properties.create(Material.PLANTS), 0).setRegistryName(Main.RegistryEvents.getPath("apple_plant"));
	public static Block GAPPLE_PLANT = new ApplePlantBlock(Block.Properties.create(Material.PLANTS), 1).setRegistryName(Main.RegistryEvents.getPath("gapple_plant"));
	
	public static Block APPLE_SAPLING = new AppleSaplingBlock(new CustomTree(TreeWorldGen.APPLE), Block.Properties.create(Material.PLANTS)).setRegistryName(Main.RegistryEvents.getPath("apple_sapling"));
	public static Block GAPPLE_SAPLING = new AppleSaplingBlock(new CustomTree(TreeWorldGen.GAPPLE), Block.Properties.create(Material.PLANTS)).setRegistryName(Main.RegistryEvents.getPath("gapple_sapling"));
}
