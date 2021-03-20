package dav.mod.objects.block.tree;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomSaplingBlock extends SaplingBlock{

	public CustomSaplingBlock(SaplingGenerator generator, Settings settings) {
		super(generator, settings.noCollision());
	}
	
	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
		ItemEntity itementity = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stack);
		world.spawnEntity(itementity.dropItem(stack.getItem()));
	}
}
