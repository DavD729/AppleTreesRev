package dav.mod.world.gen.feature;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public abstract class AbstractBaseTree extends AbstractTreeFeature<DefaultFeatureConfig> {
	
	private static final BlockState LOG = Blocks.OAK_LOG.getDefaultState();
	private static final BlockState LEAVES = Blocks.OAK_LEAVES.getDefaultState();
	
	public AbstractBaseTree() {
		super(DefaultFeatureConfig::deserialize, false);
	}
	
	@Override
	public boolean generate(Set<BlockPos> logPositions, ModifiableTestableWorld World, Random Rand, BlockPos Pos, BlockBox Box) {
		int Height = Rand.nextInt(3) + 5;
		int X = Pos.getX();
		int Y = Pos.getY();
		int Z = Pos.getZ();
		boolean Flag = true;
		
		if (Y >= 1 && Y + Height + 1 <= 256) {
			
			int n;
			int q;
			int r;
			
			for(n = Y; n <= Y + 1 + Height; ++n) {
				int k = 1;
				if (n == Y) k = 0;
				if (n >= Y + 1 + Height - 2) k = 2;
				
				BlockPos.Mutable mutable = new BlockPos.Mutable();
				
				for(q = X - k; q <= X + k && Flag; ++q) {
					for(r = Z - k; r <= Z + k && Flag; ++r) {
						if (n >= 0 && n < 256) {
							if (!canTreeReplace(World, mutable.set(q, n, r))) {
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
			} else if (isDirtOrGrass(World, Pos.down()) && Y < 256 - Height - 1) {
				
				this.setToDirt(World, Pos.down());
				
				for(n = Y - 3 + Height; n <= Y + Height; ++n) {
					
					int o = n - (Y + Height);
					int p = 1 - o / 2;
					
					for(q = X - p; q <= X + p; ++q) {
						r = q - X;
						
						for(int s = Z - p; s <= Z + p; ++s) {
							int t = s - Z;
							
							if (Math.abs(r) != p || Math.abs(t) != p || Rand.nextInt(2) != 0 && o != 0) {
								BlockPos blockPos = new BlockPos(q, n, s);
								if (isAirOrLeaves(World, blockPos)) {
									this.setBlockState(logPositions, World, blockPos, LEAVES, Box);
								}
							}
						}
					}
				}
				
				for(n = 0; n < Height; ++n) {
					if (isAirOrLeaves(World, Pos.up(n))) {
						this.setBlockState(logPositions, World, Pos.up(n), LOG, Box);
					}
				}
				
				this.generateFruit(logPositions, World, Rand, Pos.add(0, Height - 4, 0), Box);
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	protected abstract void generateFruit(Set<BlockPos> logPositions, ModifiableTestableWorld World, Random Rand, BlockPos Pos, BlockBox Box);
}
