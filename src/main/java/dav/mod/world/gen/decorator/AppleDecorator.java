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
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.decorator.BeehiveTreeDecorator;

public class AppleDecorator extends BeehiveTreeDecorator{
	
	protected final int drop;
	protected final boolean isNatural;
	
	public AppleDecorator(int drop, boolean isNatural) {
		super(0.0F);
		this.drop = drop;
		this.isNatural = isNatural;
	}
	
	public <T> AppleDecorator(Dynamic<T> dynamic) {
		this(dynamic.get("drop").asInt(0), dynamic.get("isnatural").asBoolean(false));
	}

	@Override
	public <T> T serialize(DynamicOps<T> ops) {
		return (new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
				ops.createString("type"), 
				ops.createString(Registry.TREE_DECORATOR_TYPE.getId(this.type).toString()), 
				ops.createString("drop"),
				ops.createInt(this.drop),
				ops.createString("isnatural"),
				ops.createBoolean(false))))).getValue(); 
	}

	@Override
	public void generate(IWorld World, Random Rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> Set, BlockBox Box) {
		int i = !leavesPositions.isEmpty() ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY()) : Math.min(logPositions.get(0).getY() + 1 + Rand.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
        List<BlockPos> list = logPositions.stream().filter((BlockPos) -> {
           return BlockPos.getY() == i;
        }).collect(Collectors.toList());
        if(!list.isEmpty()) {
        	
        	BlockPos AppleLayerPos = list.get(Rand.nextInt(list.size()));
			BlockState AppleType = this.getDrop();
			List<BlockPos> Spots = this.getLocations(World, AppleLayerPos);
			
			if(!Spots.isEmpty()) {
				int Cont = 0;
				
				for(int j = 0; j < 2 && j < Spots.size(); j++) {
					int Index = Rand.nextInt(Spots.size());
					this.setBlockStateAndEncompassPosition(World, Spots.get(Index), this.getNaturalAge(AppleType, Rand), Set, Box);
					Spots.remove(Index);
					Cont++;
				}
				
				for(int j = 0; j < Spots.size() && Cont < 8; j++) {
					if(Rand.nextInt(5) == 0) {
						this.setBlockStateAndEncompassPosition(World, Spots.get(j), this.getNaturalAge(AppleType, Rand), Set, Box);
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
	
	protected BlockState getDrop() {
		switch(this.drop) {
		case 1: return BlockInit.GAPPLE_PLANT.getDefaultState();
		default: return BlockInit.APPLE_PLANT.getDefaultState();
		}
	}
	
	protected static boolean isAirOrLeaves(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).isAir() || world.getBlockState(pos).matches(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).matches(BlockTags.LEAVES);
	}
}
