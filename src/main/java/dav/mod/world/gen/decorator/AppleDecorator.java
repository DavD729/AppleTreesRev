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
		super(-1.0F);
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
	public void generate(IWorld world, Random rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> Set, BlockBox Box) {
		int i = !leavesPositions.isEmpty() ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY()) : Math.min(logPositions.get(0).getY() + 1 + rand.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
        List<BlockPos> list = logPositions.stream().filter((BlockPos) -> {
           return BlockPos.getY() == i;
        }).collect(Collectors.toList());
        if(!list.isEmpty()) {
        	BlockPos AppleLayerPos = list.get(rand.nextInt(list.size()));
        	BlockState AppleType = this.getDrop();
			int cont = 2;
			this.setBlockStateAndEncompassPosition(world, AppleLayerPos.add(1, 0, 1), AppleType, Set, Box);
			this.setBlockStateAndEncompassPosition(world, AppleLayerPos.add(-1, 0, 2), AppleType, Set, Box);
        	for(int xPos = -2; xPos < 3; xPos++) {
				for(int zPos = -2; zPos < 3; zPos++) {
					if(rand.nextInt(4) == 0 && cont < 8) {
						if(isAirOrLeaves(world, AppleLayerPos.add(xPos, 0, zPos)) && isLeaves(world, AppleLayerPos.add(xPos, 1, zPos))) {
							this.setBlockStateAndEncompassPosition(world, AppleLayerPos.add(xPos, 0, zPos), AppleType, Set, Box);
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
		return world.getBlockState(pos).isAir() || world.getBlockState(pos).matches(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).matches(BlockTags.LEAVES);
	}
}
