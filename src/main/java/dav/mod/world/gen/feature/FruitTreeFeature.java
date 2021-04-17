package dav.mod.world.gen.feature;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;

import dav.mod.init.BlockInit;
import dav.mod.objects.block.tree.BlockApplePlant;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;

public class FruitTreeFeature extends AbstractBaseTree{
	
	private BlockState Fruit;
	private boolean isNatural;
	
	public FruitTreeFeature(int Type, boolean isNatural) {
		super();
		this.Fruit = this.getFruitType(Type);
		this.isNatural = isNatural;
	}
	
	@Override
	protected void generateFruit(Set<BlockPos> logPositions, ModifiableTestableWorld World, Random Rand, BlockPos Pos, BlockBox Box) {
		//Get Valid Spots for Apple Plants
		List<BlockPos> Spots = getValidLocations(World, Pos);

		//Checks if is at least 1 Spot
		if(!Spots.isEmpty()) {
			int Cont = 0;
							
			//Trees will have At least 2 Apples, Now are Completely Random
			for(int i = 0; i < 2 && i < Spots.size(); i++) {
				int Index = Rand.nextInt(Spots.size());
				this.setBlockState(World, Spots.get(Index), this.getNatAge(Fruit, Rand));
				Spots.remove(Index);
				Cont++;
			}
					
			//Generate an apple if there are enough Luck, Max. 8 Apples per Tree
			for(int i = 0; i < Spots.size() && Cont < 8; i++) {
				if(Rand.nextInt(5) == 0) {
					this.setBlockState(World, Spots.get(i), this.getNatAge(Fruit, Rand));
					Cont++;
				}
			}
		}
	}
	
	//Returns a List where can be Placed an Apple
	private List<BlockPos> getValidLocations(ModifiableTestableWorld WorldIn, BlockPos Pos){
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
	private BlockState getFruitType(int type) {
		if(type == 0) return BlockInit.APPLE_PLANT.getDefaultState();
		return BlockInit.GAPPLE_PLANT.getDefaultState();
	}
	
	//Returns the proper State for the apple plant
	private BlockState getNatAge(BlockState State, Random Rand) {
		if(this.isNatural) {
			return State.with(BlockApplePlant.AGE, Integer.valueOf(2 + Rand.nextInt(4)));
		}
		return State;
	}
}
