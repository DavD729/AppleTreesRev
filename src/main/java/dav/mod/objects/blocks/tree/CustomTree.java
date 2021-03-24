package dav.mod.objects.blocks.tree;

import java.util.Random;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CustomTree extends Tree{
	
	private ConfiguredFeature<BaseTreeFeatureConfig, ?> Tree;
	
	public CustomTree(ConfiguredFeature<BaseTreeFeatureConfig, ?> Type) {
		this.Tree = Type;
	}
	
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
		return this.Tree;
	}
}
