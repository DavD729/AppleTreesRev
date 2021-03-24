package dav.mod.objects.blocks.tree;

import java.util.Random;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class CustomTree extends SaplingGenerator{
	
	private ConfiguredFeature<TreeFeatureConfig, ?> Tree;
	
	public CustomTree(ConfiguredFeature<TreeFeatureConfig, ?> Tree) {
		this.Tree = Tree;
	}

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return this.Tree;
	}
}
