package dav.mod.world.gen.tree;

import java.util.Random;

import dav.mod.init.BlockInit;
import dav.mod.objects.blocks.tree.BlockApplePlant;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class NaturalAppleTreeGen extends WorldGenAbstractTree{
	
	private static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
   	private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
   	private static final IBlockState APPLE = BlockInit.APPLE_PLANT.getDefaultState().withProperty(BlockApplePlant.AGE, Integer.valueOf(3));
	
	public NaturalAppleTreeGen() {
		super(false);
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int i = rand.nextInt(2) + 5;
		int X = position.getX();
		int Y = position.getY();
		int Z = position.getZ();
		boolean flag = true;
		
		if (Y >= 1 && Y + i + 1 <= 256) {
			for (int j = Y; j <= Y + 1 + i; ++j) {
				int k = 1;
				if (j == Y) k = 0;
				if (j >= Y + 1 + i - 2) k = 2;
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				for (int l = X - k; l <= X + k && flag; ++l) {
			
					for (int i1 = Z - k; i1 <= Z + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getHeight()) {
							if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
			
				BlockPos down = position.down();
				IBlockState state = worldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);
				if (isSoil && Y < worldIn.getHeight() - i - 1) {
					state.getBlock().onPlantGrow(state, worldIn, down, position);
					for (int i2 = Y - 3 + i; i2 <= Y + i; ++i2) {
						int k2 = i2 - (Y + i);
						int l2 = 1 - k2 / 2;
						for (int i3 = X - l2; i3 <= X + l2; ++i3) {
							int j1 = i3 - X;
							for (int k1 = Z - l2; k1 <= Z + l2; ++k1) {
								int l1 = k1 - Z;
								if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0) {
									BlockPos blockpos = new BlockPos(i3, i2, k1);
									IBlockState state2 = worldIn.getBlockState(blockpos);
									if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos)) {
										this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
									}
								}
							}
						}
					}
					
					for (int j2 = 0; j2 < i; ++j2) {
						BlockPos upN = position.up(j2);
						IBlockState state2 = worldIn.getBlockState(upN);
						if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN)) {
							this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG);
						}
					}
					
					int cont = 2;
            				int appleLayer = i - 4;
            				this.setBlockAndNotifyAdequately(worldIn, position.add(1, appleLayer, 1), APPLE);
            				this.setBlockAndNotifyAdequately(worldIn, position.add(-1, appleLayer, 2), APPLE);
            				for(int xPos = -2; xPos < 3; xPos++) {
    					for(int zPos = -2; zPos < 3; zPos++) {
    						if(rand.nextInt(4) == 0 && cont < 8) {
    							if(isAirOrLeaves(worldIn, position.add(xPos, appleLayer, zPos)) && isLeaves(worldIn, position.add(xPos, appleLayer + 1, zPos))) {
    								this.setBlockAndNotifyAdequately(worldIn, position.add(xPos, appleLayer, zPos), APPLE);
    								cont++;
    							}
    						}
    					}
    				}
					
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	private static boolean isAirOrLeaves(World worldIn, BlockPos pos) {
		return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).getBlock().isLeaves(worldIn.getBlockState(pos), worldIn, pos);
	}
	
	private static boolean isLeaves(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().isLeaves(worldIn.getBlockState(pos), worldIn, pos);
	}
}
