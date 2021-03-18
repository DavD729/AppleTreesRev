package dav.mod.objects.blocks.tree;

import dav.mod.lists.CustomTree;
import net.minecraft.block.BlockSapling;

public class CustomBlockSapling extends BlockSapling {
	
	public CustomBlockSapling(CustomTree tree, Properties prop) {
		super(tree, prop.doesNotBlockMovement().needsRandomTick());
	}
}
