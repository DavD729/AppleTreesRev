package dav.mod.world.gen.decorator;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import dav.mod.init.BlockInit;
import dav.mod.objects.blocks.tree.ApplePlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class AppleDecorator extends BeehiveTreeDecorator{
	
	private final int drop;
	private final boolean isNatural;
	
	public AppleDecorator(int drop, boolean isNatural) {
		super(0.0F);
		this.drop = drop;
		this.isNatural = isNatural;
	}
	
	public <T> AppleDecorator(Dynamic<T> Map) {
		this(Map.get("drop").asInt(0), Map.get("isnatural").asBoolean(false));
	}

	@Override
	public <T> T serialize(DynamicOps<T> DynamicMap) {
		return (new Dynamic<>(DynamicMap, DynamicMap.createMap(ImmutableMap.of(
			DynamicMap.createString("type"), 
			DynamicMap.createString(Registry.TREE_DECORATOR_TYPE.getKey(this.field_227422_a_).toString()), 
			DynamicMap.createString("drop"),
			DynamicMap.createInt(this.drop),
			DynamicMap.createString("isnatural"),
			DynamicMap.createBoolean(this.isNatural))))).getValue();
	}

	@Override
	public void func_225576_a_(IWorld WorldIn, Random Rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> Set, MutableBoundingBox Box) {
		int i = !leavesPositions.isEmpty() ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY()) : Math.min(logPositions.get(0).getY() + 1 + Rand.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
		List<BlockPos> list = logPositions.stream().filter((BlockPos) -> {
			return BlockPos.getY() == i;
		}).collect(Collectors.toList());
		if(!list.isEmpty()) {
			BlockPos AppleLayerPos = list.get(Rand.nextInt(list.size()));
			BlockState AppleType = this.getDrop();
			List<BlockPos> Spots = this.getLocations(WorldIn, AppleLayerPos);
			if(!Spots.isEmpty()) {
				int Cont = 0;
				
				for(int j = 0; j < 2 && j < Spots.size(); j++) {
					int Index = Rand.nextInt(Spots.size());
					this.func_227423_a_(WorldIn, Spots.get(Index), this.getNaturalAge(AppleType, Rand), Set, Box);
					Spots.remove(Index);
					Cont++;
				}
				
				for(int j = 0; j < Spots.size() && Cont < 8; j++) {
					if(Rand.nextInt(5) == 0) {
						this.func_227423_a_(WorldIn, Spots.get(j), this.getNaturalAge(AppleType, Rand), Set, Box);
						Cont++;
					}
				}
			}
		}
	}
	
	private List<BlockPos> getLocations(IWorld WorldIn, BlockPos Pos){
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
	
	private BlockState getNaturalAge(BlockState State, Random Rand) {
		if(!this.isNatural) {
			return State;
		}
		return State.with(ApplePlantBlock.AGE, Integer.valueOf(2 + Rand.nextInt(4)));
	}
	
	private BlockState getDrop() {
		switch(this.drop) {
		case 1: return BlockInit.GAPPLE_PLANT.getDefaultState();
		default: return BlockInit.APPLE_PLANT.getDefaultState();
		}
	}
	
	protected static boolean isAirOrLeaves(IWorld WorldIn, BlockPos Pos) {
		return WorldIn.isAirBlock(Pos) || WorldIn.getBlockState(Pos).isIn(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(IWorld WorldIn, BlockPos Pos) {
		return WorldIn.getBlockState(Pos).isIn(BlockTags.LEAVES);
	}
}
