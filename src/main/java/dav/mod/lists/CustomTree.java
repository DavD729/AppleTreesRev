package dav.mod.lists;

import java.util.Random;

import net.minecraft.block.trees.AbstractTree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CustomTree extends AbstractTree {
	
	private AbstractTreeFeature<NoFeatureConfig> tree;
	
	public CustomTree(AbstractTreeFeature<NoFeatureConfig> tree) {
		this.tree = tree;
	}
	
	@Override
	protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
		return this.tree;
	}
}
