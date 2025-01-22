package net.dav.appletreesrev.world.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public abstract class AbstractBaseTree extends WorldGenAbstractTree {
	protected static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
	protected static final IBlockState LEAVE = Blocks.LEAVES.getDefaultState()
			.withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK)
			.withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);
	
	public AbstractBaseTree() {
		super(true);
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {
		int height = rand.nextInt(2) + 5;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		boolean flag = true;
		
		if (y >= 1 && y + height + 1 <= 256) {
			for (int j = y; j <= y + 1 + height; ++j) {
				
				int k = 1;
				if (j == y) k = 0;
				if (j >= y + 1 + height - 2) k = 2;
				BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
				
				for (int l = x - k; l <= x + k && flag; ++l) {
					for (int i1 = z - k; i1 <= z + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getHeight()) {
							if (!this.isReplaceable(worldIn, mutableBlockPos.setPos(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}
			if (!flag) {
				return false;
			} else {
				
				BlockPos down = pos.down();
				IBlockState state = worldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);
				
				if (isSoil && y < worldIn.getHeight() - height - 1) {
					state.getBlock().onPlantGrow(state, worldIn, down, pos);
					for (int i2 = y - 3 + height; i2 <= y + height; ++i2) {
						int k2 = i2 - (y + height);
						int l2 = 1 - k2 / 2;
						for (int i3 = x - l2; i3 <= x + l2; ++i3) {
							
							int j1 = i3 - x;
							for (int k1 = z - l2; k1 <= z + l2; ++k1) {
								
								int l1 = k1 - z;
								if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0) {
									
									BlockPos blockpos = new BlockPos(i3, i2, k1);
									IBlockState state2 = worldIn.getBlockState(blockpos);
									
									if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos)) {
										this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAVE);
									}
								}
							}
						}
					}
					
					for (int j2 = 0; j2 < height; ++j2) {
						BlockPos upN = pos.up(j2);
						IBlockState state2 = worldIn.getBlockState(upN);
						
						if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN)) {
							this.setBlockAndNotifyAdequately(worldIn, pos.up(j2), LOG);
						}
					}
					
					this.generateFruit(worldIn, rand, pos.add(0, height - 4, 0));
					
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	protected abstract void generateFruit(World WorldIn, Random Rand, BlockPos Pos);
	
	protected static boolean isAirOrLeaves(World worldIn, BlockPos pos) {
		return worldIn.isAirBlock(pos) || AbstractBaseTree.isLeaves(worldIn, pos);
	}
	
	protected static boolean isLeaves(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getMaterial() == Material.LEAVES;
	}
}
