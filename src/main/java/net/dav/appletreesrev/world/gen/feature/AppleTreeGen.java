package net.dav.appletreesrev.world.gen.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.dav.appletreesrev.objects.block.BlockApplePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AppleTreeGen extends AbstractBaseTree {
    private final IBlockState applePlant;
    private final boolean isNatural;
	
	public AppleTreeGen(int meta, boolean isNatural) {
		super();
		this.applePlant = BlockApplePlant.getPlantByMeta(meta);
		this.isNatural = isNatural;
	}

	@Override
	protected void generateFruit(World worldIn, Random rand, BlockPos pos) {
		List<BlockPos> locations = getValidLocations(worldIn, pos);
		if(!locations.isEmpty()) {
			Collections.shuffle(locations, rand);

			int cont = 0;
			for(int i = 0; i < locations.size() && cont < 7; i++) {
				if(i < 2 || rand.nextInt(5) == 0) {
					this.setBlockAndNotifyAdequately(worldIn, locations.get(i), this.getNatAge(applePlant, rand));
					cont++;
				}
			}
		}
	}

	private List<BlockPos> getValidLocations(World worldIn, BlockPos pos) {
		List<BlockPos> locations = new ArrayList<>(25);
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		for(int x = -2; x <= 2; x++) {
			for(int z = -2; z <= 2; z++) {
				mutablePos.setPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
				if(isAirOrLeaves(worldIn, mutablePos) && isLeaves(worldIn, mutablePos.up())) {
					locations.add(mutablePos.toImmutable());
				}
			}
		}

		return locations;
	}

	private IBlockState getNatAge(IBlockState state, Random rand) {
		return this.isNatural ? state.withProperty(BlockApplePlant.AGE, 2 + rand.nextInt(4)) : state;
	}
}
