package dav.mod.world.gen.tree;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public abstract class AbstractBaseTree extends WorldGenAbstractTree{
	
	private static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
	private static final IBlockState LEAVE = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
	
	public AbstractBaseTree() {
		super(false);
	}
	
	@Override
	public boolean generate(World WorldIn, Random Rand, BlockPos Pos) {
		int Height = Rand.nextInt(3) + 5;	
		int X = Pos.getX();
		int Y = Pos.getY();
		int Z = Pos.getZ();
		boolean flag = true;
		
		if (Y >= 1 && Y + Height + 1 <= 256) {
			for (int j = Y; j <= Y + 1 + Height; ++j) {
				
				int k = 1;
				if (j == Y) k = 0;
				if (j >= Y + 1 + Height - 2) k = 2;
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				
				for (int l = X - k; l <= X + k && flag; ++l) {
					for (int i1 = Z - k; i1 <= Z + k && flag; ++i1) {
						if (j >= 0 && j < WorldIn.getHeight()) {
							if (!this.isReplaceable(WorldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
				
				BlockPos down = Pos.down();
				IBlockState state = WorldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, WorldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);
				
				if (isSoil && Y < WorldIn.getHeight() - Height - 1) {
					state.getBlock().onPlantGrow(state, WorldIn, down, Pos);
					for (int i2 = Y - 3 + Height; i2 <= Y + Height; ++i2) {
						int k2 = i2 - (Y + Height);
						int l2 = 1 - k2 / 2;
						for (int i3 = X - l2; i3 <= X + l2; ++i3) {
							
							int j1 = i3 - X;
							for (int k1 = Z - l2; k1 <= Z + l2; ++k1) {
								
								int l1 = k1 - Z;
								if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || Rand.nextInt(2) != 0 && k2 != 0) {
									
									BlockPos blockpos = new BlockPos(i3, i2, k1);
									IBlockState state2 = WorldIn.getBlockState(blockpos);
									
									if (state2.getBlock().isAir(state2, WorldIn, blockpos) || state2.getBlock().isAir(state2, WorldIn, blockpos)) {
										this.setBlockAndNotifyAdequately(WorldIn, blockpos, LEAVE);
									}
								}
							}
						}
					}
					
					for (int j2 = 0; j2 < Height; ++j2) {
						BlockPos upN = Pos.up(j2);
						IBlockState state2 = WorldIn.getBlockState(upN);
						
						if (state2.getBlock().isAir(state2, WorldIn, upN) || state2.getBlock().isLeaves(state2, WorldIn, upN)) {
							this.setBlockAndNotifyAdequately(WorldIn, Pos.up(j2), LOG);
						}
					}
					
					this.generateFruit(WorldIn, Rand, Pos.add(0, Height - 4, 0));
					
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
	
	protected static boolean isAirOrLeaves(World WorldIn, BlockPos Pos) {
		IBlockState State = WorldIn.getBlockState(Pos);
		return WorldIn.isAirBlock(Pos) || State.getBlock().isLeaves(State, WorldIn, Pos);
	}
	
	protected static boolean isLeaves(World WorldIn, BlockPos Pos) {
		IBlockState State = WorldIn.getBlockState(Pos);
		return State.getBlock().isLeaves(State, WorldIn, Pos);
	}
}
