package dav.mod.world.gen.tree;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class AbstractBaseTree extends AbstractTreeFeature<NoFeatureConfig> {
	
	private static final IBlockState LOG = Blocks.OAK_LOG.getDefaultState();
	private static final IBlockState LEAF = Blocks.OAK_LEAVES.getDefaultState();
	
	public AbstractBaseTree() {
		super(false);
	}
	
	@Override
	protected boolean place(Set<BlockPos> changedBlocks, IWorld WorldIn, Random Rand, BlockPos Pos) {
		int Height = Rand.nextInt(3) + 5;
		int X = Pos.getX();
		int Y = Pos.getY();
		int Z = Pos.getZ();
		boolean Flag = true;
		if(Y >= 1 && Y + Height + 1 <= 256) {
			for(int j = Y; j <= Y + 1 + Height; j++) {
				
				int k = 1;
				if(j == Y) k = 0;
				if(j >= Y + 1 + Height - 2) k = 2;
				
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				for(int l = X - k; l <= X + k && Flag; ++l) {
					for(int i1 = Z - k; i1 <= Z + k && Flag; ++i1) {
						if (j >= 0 && j < WorldIn.getWorld().getHeight()) {
							if (!this.canGrowInto(WorldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
								Flag = false;
							}
						} else {
							Flag = false;
						}
					}
				}
			}
			if(!Flag) {
				return false;
			} else {
				boolean isSoil = WorldIn.getBlockState(Pos.down()).canSustainPlant(WorldIn, Pos.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.OAK_SAPLING);
				if (isSoil && Y < WorldIn.getWorld().getHeight() - Height - 1) {
					
					this.setDirtAt(WorldIn, Pos.down(), Pos);
					for(int i2 = Y - 3 + Height; i2 <= Y + Height; ++i2) {
						
						int k2 = i2 - (Y + Height);
						int l2 = 1 - k2 / 2;
						for(int i3 = X - l2; i3 <= X + l2; ++i3) {
							
							int j1 = i3 - X;
							for(int k1 = Z - l2; k1 <= Z + l2; ++k1) {
								
								int l1 = k1 - Z;
								if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || Rand.nextInt(2) != 0 && k2 != 0) {
									
									BlockPos blockpos = new BlockPos(i3, i2, k1);
									IBlockState iblockstate = WorldIn.getBlockState(blockpos);
									if (iblockstate.isAir(WorldIn, blockpos) || iblockstate.isIn(BlockTags.LEAVES)) {
										this.setBlockState(WorldIn, blockpos, LEAF);
									}
								}
							}
						}
					}
            		
					for(int j2 = 0; j2 < Height; ++j2) {
						
						IBlockState iblockstate1 = WorldIn.getBlockState(Pos.up(j2));
						if (iblockstate1.isAir(WorldIn, Pos.up(j2)) || iblockstate1.isIn(BlockTags.LEAVES)) {
							this.func_208520_a(changedBlocks, WorldIn, Pos.up(j2), LOG);
						}
					}
					
					this.generateFruit(changedBlocks, WorldIn, Rand, Pos.add(0, Height - 4, 0));
					
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	protected abstract void generateFruit(Set<BlockPos> changedBlocks, IWorld WorldIn, Random Rand, BlockPos Pos);
	
	protected static boolean isAirOrLeaves(IWorld WorldIn, BlockPos Pos) {
		return WorldIn.isAirBlock(Pos) || WorldIn.getBlockState(Pos).isIn(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(IWorld WorldIn, BlockPos Pos) {
		return WorldIn.getBlockState(Pos).isIn(BlockTags.LEAVES);
	}
}
