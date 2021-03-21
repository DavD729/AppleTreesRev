package dav.mod.init;

import dav.mod.Main;
import dav.mod.objects.blocks.tree.ApplePlantBlock;
import dav.mod.objects.blocks.tree.AppleSaplingBlock;
import dav.mod.objects.blocks.tree.CustomTree;
import dav.mod.world.gen.TreeWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraft.world.gen.feature.Feature;

public class BlockInit {
	
	public static Block APPLE_PLANT = new ApplePlantBlock(Block.Properties.create(Material.PLANTS), Items.APPLE).setRegistryName(Main.RegistryEvents.getPath("apple_plant"));
	public static Block GAPPLE_PLANT = new ApplePlantBlock(Block.Properties.create(Material.PLANTS), Items.GOLDEN_APPLE).setRegistryName(Main.RegistryEvents.getPath("gapple_plant"));
	
	public static Block APPLE_SAPLING = new AppleSaplingBlock(new CustomTree(Feature.NORMAL_TREE.withConfiguration(TreeWorldGen.APPLE_TREE_CONFIG)), Block.Properties.create(Material.PLANTS)).setRegistryName(Main.RegistryEvents.getPath("apple_sapling"));
	public static Block GAPPLE_SAPLING = new AppleSaplingBlock(new CustomTree(Feature.NORMAL_TREE.withConfiguration(TreeWorldGen.GOLD_APPLE_TREE_CONFIG)), Block.Properties.create(Material.PLANTS)).setRegistryName(Main.RegistryEvents.getPath("gapple_sapling"));
}
