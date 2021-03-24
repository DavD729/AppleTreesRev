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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

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
	protected TreeDecoratorType<?> func_230380_a_() {
		return TreeDecoratorType.BEEHIVE;
	}

	@Override
	public void func_225576_a_(ISeedReader WorldIn, Random Rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, MutableBoundingBox Box) {
		int i = !leavesPositions.isEmpty() ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY()) : Math.min(logPositions.get(0).getY() + 1 + Rand.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
		List<BlockPos> list = logPositions.stream().filter((BlockPos) -> {
			return BlockPos.getY() == i;
		}).collect(Collectors.toList());
		if (!list.isEmpty()) {
			
			BlockPos AppleLayerPos = list.get(Rand.nextInt(list.size()));
			BlockState AppleType = this.getDropType();
			int cont = 2;
			this.func_227423_a_(WorldIn, AppleLayerPos.add(1, 0, 1), this.getNaturalAge(AppleType, Rand), placedStates, Box);
			this.func_227423_a_(WorldIn, AppleLayerPos.add(-1, 0, 2), this.getNaturalAge(AppleType, Rand), placedStates, Box);
			for(int xPos = -2; xPos < 3; xPos++) {
				for(int zPos = -2; zPos < 3; zPos++) {
					if(Rand.nextInt(4) == 0 && cont < 8) {
						if(isAirOrLeaves(WorldIn, AppleLayerPos.add(xPos, 0, zPos)) && isLeaves(WorldIn, AppleLayerPos.add(xPos, 1, zPos))) {
							this.func_227423_a_(WorldIn, AppleLayerPos.add(xPos, 0, zPos), this.getNaturalAge(AppleType, Rand), placedStates, Box);
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
	
	protected static boolean isAirOrLeaves(ISeedReader WorldIn, BlockPos Pos) {
		return WorldIn.isAirBlock(Pos) || WorldIn.getBlockState(Pos).isIn(BlockTags.LEAVES);
	}
	
	protected static boolean isLeaves(ISeedReader WorldIn, BlockPos Pos) {
		return WorldIn.getBlockState(Pos).isIn(BlockTags.LEAVES);
	}
}
