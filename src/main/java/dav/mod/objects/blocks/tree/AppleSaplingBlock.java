package dav.mod.objects.blocks.tree;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.trees.Tree;

public class AppleSaplingBlock extends SaplingBlock {

	public AppleSaplingBlock(Tree treeIn, Properties properties) {
		super(treeIn, properties.doesNotBlockMovement().tickRandomly().notSolid().sound(SoundType.PLANT));
	}
}
