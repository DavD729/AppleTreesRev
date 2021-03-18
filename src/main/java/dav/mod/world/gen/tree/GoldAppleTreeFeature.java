package dav.mod.world.gen.tree;

import java.util.Random;
import java.util.Set;

import dav.mod.lists.BlockList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GoldAppleTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
	
	private static final IBlockState LOG = Blocks.OAK_LOG.getDefaultState();
	private static final IBlockState LEAF = Blocks.OAK_LEAVES.getDefaultState();
	private static final IBlockState APPLE = BlockList.goldapple_plant.getDefaultState();
	
	public GoldAppleTreeFeature() {
		super(false);
	}

	@Override
	protected boolean place(Set<BlockPos> changedBlocks, IWorld worldIn, Random rand, BlockPos pos) {
		int i = rand.nextInt(2) + 5;
		boolean flag = true;
		if (pos.getY() >= 1 && pos.getY() + i + 1 <= worldIn.getWorld().getHeight()) {
			for(int j = pos.getY(); j <= pos.getY() + 1 + i; ++j) {
				int k = 1;
				if (j == pos.getY()) {
					k = 0;
				}
				if (j >= pos.getY() + 1 + i - 2) {
					k = 2;
				}
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				for(int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
					for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getWorld().getHeight()) {
							if (!this.canGrowInto(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
				boolean isSoil = worldIn.getBlockState(pos.down()).canSustainPlant(worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.OAK_SAPLING);
				if (isSoil && pos.getY() < worldIn.getWorld().getHeight() - i - 1) {
					this.setDirtAt(worldIn, pos.down(), pos);
					for(int i2 = pos.getY() - 3 + i; i2 <= pos.getY() + i; ++i2) {
						int k2 = i2 - (pos.getY() + i);
						int l2 = 1 - k2 / 2;
						for(int i3 = pos.getX() - l2; i3 <= pos.getX() + l2; ++i3) {
							int j1 = i3 - pos.getX();
							for(int k1 = pos.getZ() - l2; k1 <= pos.getZ() + l2; ++k1) {
								int l1 = k1 - pos.getZ();
								if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0) {
									BlockPos blockpos = new BlockPos(i3, i2, k1);
									IBlockState iblockstate = worldIn.getBlockState(blockpos);
									if (iblockstate.isAir(worldIn, blockpos) || iblockstate.isIn(BlockTags.LEAVES)) {
										this.setBlockState(worldIn, blockpos, LEAF);
									}
								}
							}
						}
					}
            		
					for(int j2 = 0; j2 < i; ++j2) {
						IBlockState iblockstate1 = worldIn.getBlockState(pos.up(j2));
						if (iblockstate1.isAir(worldIn, pos.up(j2)) || iblockstate1.isIn(BlockTags.LEAVES)) {
							this.func_208520_a(changedBlocks, worldIn, pos.up(j2), LOG);
						}
					}
					
					int cont = 2;
            		int appleLayer = i - 4;
            		this.setBlockState(worldIn, pos.add(1, appleLayer, 1), APPLE);
            		this.setBlockState(worldIn, pos.add(-1, appleLayer, 2), APPLE);
            		for(int xPos = -2; xPos < 3; xPos++) {
    					for(int zPos = -2; zPos < 3; zPos++) {
    						if(rand.nextInt(4) == 0 && cont < 8) {
    							if(isAirOrLeaves(worldIn, pos.add(xPos, appleLayer, zPos)) && isLeaves(worldIn, pos.add(xPos, appleLayer + 1, zPos))) {
    								this.setBlockState(worldIn, pos.add(xPos, appleLayer, zPos), APPLE);
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
	
	private static boolean isAirOrLeaves(IWorld worldIn, BlockPos pos) {
		return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
	
	private static boolean isLeaves(IWorld worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
}
