package dav.mod.init;

import java.util.Random;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class CustomTree extends SaplingGenerator {
	
	private AbstractTreeFeature<DefaultFeatureConfig> tree;
	
	public CustomTree(AbstractTreeFeature<DefaultFeatureConfig> tree) {
		this.tree = tree;
	}
	
	@Override
	protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
		return this.tree;
	}
}
