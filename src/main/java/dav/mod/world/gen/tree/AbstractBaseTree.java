package dav.mod.world.gen.tree;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class AbstractBaseTree extends AbstractTreeFeature<NoFeatureConfig> {
	
	private static final BlockState LOG = Blocks.OAK_LOG.getDefaultState();
	private static final BlockState LEAF = Blocks.OAK_LEAVES.getDefaultState();
	
	public AbstractBaseTree() {
		super(NoFeatureConfig::deserialize, false);
	}
	
	@Override
	protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader WorldIn, Random Rand, BlockPos Pos, MutableBoundingBox Box) {
		int Height = Rand.nextInt(3) + 5;
		int X = Pos.getX();
		int Y = Pos.getY();
		int Z = Pos.getZ();
		boolean Flag = true;
		
		if (Y >= 1 && Y + Height + 1 <= WorldIn.getMaxHeight()) {
			
			for(int j = Y; j <= Y + 1 + Height; ++j) {
				
				int k = 1;
				if (j == Y) k = 0;
				if (j >= Y + 1 + Height - 2) k = 2;
				
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				for(int l = X - k; l <= X + k && Flag; ++l) {
					for(int i1 = Z - k; i1 <= Z + k && Flag; ++i1) {
						if (j >= 0 && j < WorldIn.getMaxHeight()) {
							if (!func_214587_a(WorldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
								Flag = false;
							}
						} else {
							Flag = false;
						}
					}
				}
			}
			if (!Flag) {
				return false;
			} else if ((isSoil(WorldIn, Pos.down(), getSapling())) && Y < WorldIn.getMaxHeight() - Height - 1) {
				this.setDirtAt(WorldIn, Pos.down(), Pos);
				
				for(int l1 = Y - 3 + Height; l1 <= Y + Height; ++l1) {
					int j2 = l1 - (Y + Height);
					int k2 = 1 - j2 / 2;
					for(int l2 = X - k2; l2 <= X + k2; ++l2) {
						int i3 = l2 - X;
						for(int j1 = Z - k2; j1 <= Z + k2; ++j1) {
							int k1 = j1 - Z;
							if (Math.abs(i3) != k2 || Math.abs(k1) != k2 || Rand.nextInt(2) != 0 && j2 != 0) {
								BlockPos blockpos = new BlockPos(l2, l1, j1);
								if (isAirOrLeaves(WorldIn, blockpos)) {
									this.setLogState(changedBlocks, WorldIn, blockpos, LEAF, Box);
								}
							}
						}
					}
				}
				
				for(int i2 = 0; i2 < Height; ++i2) {
					if (isAirOrLeaves(WorldIn, Pos.up(i2))) {
						this.setLogState(changedBlocks, WorldIn, Pos.up(i2), LOG, Box);
					}
				}
				
				this.generateFruit(changedBlocks, WorldIn, Rand, Pos.add(0, Height - 4, 0), Box);
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	protected abstract void generateFruit(Set<BlockPos> changedBlocks, IWorldGenerationReader WorldIn, Random Rand, BlockPos Pos, MutableBoundingBox Box);

}
