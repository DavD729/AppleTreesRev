package dav.mod.world.gen.tree;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import dav.mod.init.BlockInit;
import dav.mod.objects.blocks.tree.BlockApplePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FruitTreeGen extends AbstractBaseTree{
	
    private IBlockState Fruit;
    private boolean isNatural;
	
	public FruitTreeGen(int type, boolean isNatural) {
		super();
		this.Fruit = this.getFruitType(type);
		this.isNatural = isNatural;
	}

	@Override
	protected void generateFruit(World WorldIn, Random Rand, BlockPos Pos) {
		//Get Valid Spots for Apple Plants
		List<BlockPos> Spots = getValidLocations(WorldIn, Pos);
		
		//Checks if is at least 1 Spot
		if(!Spots.isEmpty()) {
			int Cont = 0;
			
			//Trees will have At least 2 Apples, Now are Completely Random
			for(int i = 0; i < 2 && i < Spots.size(); i++) {
				int Index = Rand.nextInt(Spots.size());
				this.setBlockAndNotifyAdequately(WorldIn, Spots.get(Index), this.getNatAge(Fruit, Rand));
				Spots.remove(Index);
				Cont++;
			}
			
			//Generate an apple if there are enough Luck, Max. 8 Apples per Tree
			for(int i = 0; i < Spots.size() && Cont < 8; i++) {
				if(Rand.nextInt(5) == 0) {
					this.setBlockAndNotifyAdequately(WorldIn, Spots.get(i), this.getNatAge(Fruit, Rand));
					Cont++;
				}
			}
		}
	}
	
	//Returns a List where can be Placed an Apple
	private List<BlockPos> getValidLocations(World WorldIn, BlockPos Pos){
		List<BlockPos> Locations = Lists.newArrayList();
		for(int xPos = -2; xPos < 3; xPos++) {
			for(int zPos = -2; zPos < 3; zPos++) {
				if(isAirOrLeaves(WorldIn, Pos.add(xPos, 0, zPos)) && isLeaves(WorldIn, Pos.add(xPos, 1, zPos))) {
					Locations.add(Pos.add(xPos, 0, zPos));
				}
			}
		}
		return Locations;
	}
	
	//Returns the apple type of the Tree
	private IBlockState getFruitType(int type) {
		if(type == 0) return BlockInit.APPLE_PLANT.getDefaultState();
		return BlockInit.GAPPLE_PLANT.getDefaultState();
	}
	
	//Returns the proper State for the apple plant
	private IBlockState getNatAge(IBlockState State, Random Rand) {
		if(this.isNatural) {
			return State.withProperty(BlockApplePlant.AGE, Integer.valueOf(2 + Rand.nextInt(4)));
		}
		return State.withProperty(BlockApplePlant.AGE, Integer.valueOf(0));
	}
}
