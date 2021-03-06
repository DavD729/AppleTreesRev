package dav.mod.world.gen.decorator;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
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
			List<BlockPos> Spots = this.getLocations(WorldIn, AppleLayerPos);
			if(!Spots.isEmpty()) {
				int Cont = 0;
				
				for(int j = 0; j < 2 && j < Spots.size(); j++) {
					int Index = Rand.nextInt(Spots.size());
					this.func_227423_a_(WorldIn, Spots.get(Index), this.getNaturalAge(AppleType, Rand), placedStates, Box);
					Spots.remove(Index);
					Cont++;
				}
				
				for(int j = 0; j < Spots.size() && Cont < 8; j++) {
					if(Rand.nextInt(5) == 0) {
						this.func_227423_a_(WorldIn, Spots.get(j), this.getNaturalAge(AppleType, Rand), placedStates, Box);
						Cont++;
					}
				}
			}
		}
	}
	
	private List<BlockPos> getLocations(ISeedReader WorldIn, BlockPos Pos){
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
