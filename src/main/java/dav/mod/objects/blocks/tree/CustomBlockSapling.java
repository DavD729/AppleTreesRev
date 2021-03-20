package dav.mod.objects.blocks.tree;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.trees.Tree;

public class CustomBlockSapling extends SaplingBlock{
	
	public CustomBlockSapling(Tree tree, Properties prop) {
		super(tree, prop.doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT));
	}
}
