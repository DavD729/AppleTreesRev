package dav.mod.objects.blocks.tree;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

public class CustomSaplingBlock extends SaplingBlock {

	public CustomSaplingBlock(SaplingGenerator generator, Settings settings) {
		super(generator, settings.noCollision());
	}
}
