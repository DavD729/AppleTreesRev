package dav.mod.world.gen.decorator;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dav.mod.init.BlockInit;
import dav.mod.objects.blocks.tree.ApplePlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.tree.BeehiveTreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

public class AppleDecorator extends BeehiveTreeDecorator{
	
	public static final Codec<AppleDecorator> CODEC = RecordCodecBuilder.create((instance) ->{
		return instance.group(Codec.INT.fieldOf("Type").forGetter((AppleDecorator) -> {
			return AppleDecorator.Type;
		}), Codec.BOOL.fieldOf("isNatural").forGetter((AppleDecorator) -> {
			return AppleDecorator.isNatural;
		})).apply(instance, AppleDecorator::new);
	});
	
	protected final int Type;
	protected final boolean isNatural;
	
	public AppleDecorator(int Type, boolean isNatural) {
		super(0.0F);
		this.Type = Type;
		this.isNatural = isNatural;
	}
	
	@Override
	protected TreeDecoratorType<?> getType() {
		return TreeDecoratorType.BEEHIVE;
	}
	
	@Override
	public void generate(StructureWorldAccess world, Random Rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box) {
		int i = !leavesPositions.isEmpty() ? Math.max(((BlockPos)leavesPositions.get(0)).getY() - 1, ((BlockPos)logPositions.get(0)).getY()) : Math.min(((BlockPos)logPositions.get(0)).getY() + 1 + Rand.nextInt(3), ((BlockPos)logPositions.get(logPositions.size() - 1)).getY());
        List<BlockPos> list = (List<BlockPos>)logPositions.stream().filter((pos) -> {
           return pos.getY() == i;
        }).collect(Collectors.toList());
        if (!list.isEmpty()) {
        	BlockPos AppleLayerPos = list.get(Rand.nextInt(list.size()));
        	BlockState AppleType = this.getDropType();
        	int cont = 2;
        	this.setBlockStateAndEncompassPosition(world, AppleLayerPos.add(1, 0, 1), this.getNaturalAge(AppleType, Rand), placedStates, box);
        	this.setBlockStateAndEncompassPosition(world, AppleLayerPos.add(-1, 0, 2), this.getNaturalAge(AppleType, Rand), placedStates, box);
        	for(int xPos = -2; xPos < 3; xPos++) {
				for(int zPos = -2; zPos < 3; zPos++) {
					if(Rand.nextInt(4) == 0 && cont < 8) {
						if(isAirOrLeaves(world, AppleLayerPos.add(xPos, 0, zPos)) && isLeaves(world, AppleLayerPos.add(xPos, 1, zPos))) {
							this.setBlockStateAndEncompassPosition(world, AppleLayerPos.add(xPos, 0, zPos), this.getNaturalAge(AppleType, Rand), placedStates, box);
							cont++;
						}
					}
				}
			}
        }
	}
	
	private BlockState getNaturalAge(BlockState State, Random Rand) {
		if(!this.isNatural) {
			return State;
		}
		return State.with(ApplePlantBlock.AGE, Integer.valueOf(2 + Rand.nextInt(3)));
	}
	
	protected BlockState getDropType() {
		switch(this.Type) {
		case 1: return BlockInit.GAPPLE_PLANT.getDefaultState();
		default: return BlockInit.APPLE_PLANT.getDefaultState();
		}
	}
	
	protected static boolean isAirOrLeaves(StructureWorldAccess world, BlockPos pos) {
		return world.getBlockState(pos).isAir() || world.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(StructureWorldAccess world, BlockPos pos) {
		return world.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
}