package dav.mod.objects.blocks.tree;

import java.util.Random;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CustomTree extends SaplingGenerator{
	
	private ConfiguredFeature<BranchedTreeFeatureConfig, ?> Tree;
	
	public CustomTree(ConfiguredFeature<BranchedTreeFeatureConfig, ?> Tree) {
		this.Tree = Tree;
	}
	
	@Override
	protected ConfiguredFeature<BranchedTreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return this.Tree;
	}
}
