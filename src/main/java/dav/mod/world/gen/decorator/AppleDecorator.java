package dav.mod.world.gen.decorator;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
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
	
	protected final int drop;
	protected final boolean isNatural;
	
	public AppleDecorator(int drop, boolean isNatural) {
		super(-1.0F);
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
	public void func_225576_a_(IWorld world, Random rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> Set, MutableBoundingBox blockBox) {
		int i = !leavesPositions.isEmpty() ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY()) : Math.min(logPositions.get(0).getY() + 1 + rand.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
        List<BlockPos> list = logPositions.stream().filter((BlockPos) -> {
        	return BlockPos.getY() == i;
        }).collect(Collectors.toList());
        if(!list.isEmpty()) {
        	BlockPos AppleLayerPos = list.get(rand.nextInt(list.size()));
        	BlockState AppleType = this.getDrop();
			int cont = 2;
			this.func_227423_a_(world, AppleLayerPos.add(1, 0, 1), AppleType, Set, blockBox);
			this.func_227423_a_(world, AppleLayerPos.add(-1, 0, 2), AppleType, Set, blockBox);
        	for(int xPos = -2; xPos < 3; xPos++) {
				for(int zPos = -2; zPos < 3; zPos++) {
					if(rand.nextInt(4) == 0 && cont < 8) {
						if(isAirOrLeaves(world, AppleLayerPos.add(xPos, 0, zPos)) && isLeaves(world, AppleLayerPos.add(xPos, 1, zPos))) {
							this.func_227423_a_(world, AppleLayerPos.add(xPos, 0, zPos), AppleType, Set, blockBox);
							cont++;
						}
					}
				}
			}	
        }
	}
	
	protected BlockState getDrop() {
		BlockState State;
		switch(this.drop) {
		case 1: State = BlockInit.GAPPLE_PLANT.getDefaultState();
				break;
		default: State = BlockInit.APPLE_PLANT.getDefaultState();
		}
		return this.isNatural ? State.with(ApplePlantBlock.AGE, Integer.valueOf(3)) : State;
	}
	
	protected static boolean isAirOrLeaves(IWorld world, BlockPos pos) {
		return world.isAirBlock(pos) || world.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
}
