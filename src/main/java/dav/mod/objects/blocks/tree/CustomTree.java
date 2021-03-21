package dav.mod.objects.blocks.tree;

import java.util.Random;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class CustomTree extends Tree{
	
	private ConfiguredFeature<TreeFeatureConfig, ?> Tree;
	
	public CustomTree(ConfiguredFeature<TreeFeatureConfig, ?> Tree) {
		this.Tree = Tree;
	}
	
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean flag) {
		return this.Tree;
	}
}
