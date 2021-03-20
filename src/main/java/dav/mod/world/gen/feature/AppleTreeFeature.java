package dav.mod.world.gen.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import dav.mod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class AppleTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig> {

	private static final BlockState LOG = Blocks.OAK_LOG.getDefaultState();
	private static final BlockState LEAVES = Blocks.OAK_LEAVES.getDefaultState();
	private static final BlockState PLANT = BlockInit.APPLE_PLANT.getDefaultState();

	public AppleTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
		super(configFactory, false);
	}

	public boolean generate(Set<BlockPos> logPositions, ModifiableTestableWorld world, Random random, BlockPos pos, BlockBox blockBox) {
		int i = random.nextInt(2) + 5;
		boolean bl = true;
		if (pos.getY() >= 1 && pos.getY() + i + 1 <= 256) {
			int n;
			int q;
			int r;
			for(n = pos.getY(); n <= pos.getY() + 1 + i; ++n) {
				int k = 1;
				if (n == pos.getY()) k = 0;
				if (n >= pos.getY() + 1 + i - 2) k = 2;
				BlockPos.Mutable mutable = new BlockPos.Mutable();
				for(q = pos.getX() - k; q <= pos.getX() + k && bl; ++q) {
					for(r = pos.getZ() - k; r <= pos.getZ() + k && bl; ++r) {
						if (n >= 0 && n < 256) {
							if (!canTreeReplace(world, mutable.set(q, n, r))) {
								bl = false;
							}
						} else {
							bl = false;
						}
					}
				}
			}
			if (!bl) {
				return false;
			} else if (isDirtOrGrass(world, pos.down()) && pos.getY() < 256 - i - 1) {
				this.setToDirt(world, pos.down());
				for(n = pos.getY() - 3 + i; n <= pos.getY() + i; ++n) {
					int o = n - (pos.getY() + i);
					int p = 1 - o / 2;
					for(q = pos.getX() - p; q <= pos.getX() + p; ++q) {
						r = q - pos.getX();
						for(int s = pos.getZ() - p; s <= pos.getZ() + p; ++s) {
							int t = s - pos.getZ();
							if (Math.abs(r) != p || Math.abs(t) != p || random.nextInt(2) != 0 && o != 0) {
								BlockPos blockPos = new BlockPos(q, n, s);
								if (isAirOrLeaves(world, blockPos)) {
									this.setBlockState(logPositions, world, blockPos, LEAVES, blockBox);
								}
							}
						}
					}
				}
				
				for(n = 0; n < i; ++n) {
					if (isAirOrLeaves(world, pos.up(n))) {
						this.setBlockState(logPositions, world, pos.up(n), LOG, blockBox);
					}
				}
				
				int cont = 2;
        		int appleLayer = i - 4;
        		this.setBlockState(world, pos.add(1, appleLayer, 1), PLANT);
        		this.setBlockState(world, pos.add(-1, appleLayer, 2), PLANT);
        		for(int xPos = -2; xPos < 3; xPos++) {
					for(int zPos = -2; zPos < 3; zPos++) {
						if(random.nextInt(4) == 0 && cont < 8) {
							if(isAirOrLeaves(world, pos.add(xPos, appleLayer, zPos)) && isLeaves(world, pos.add(xPos, appleLayer + 1, zPos))) {
								this.setBlockState(world, pos.add(xPos, appleLayer, zPos), PLANT);
								cont++;
							}
						}
					}
				}
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
