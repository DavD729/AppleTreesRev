package dav.mod.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import dav.mod.world.gen.tree.FruitTreeGen;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TreeWorldGen implements IWorldGenerator{
	
	private final WorldGenerator APPLE = new FruitTreeGen(0, true);
	private ArrayList<Biome> plainsList = new ArrayList<Biome>(Arrays.asList(Biomes.PLAINS, Biomes.MUTATED_PLAINS));
	private ArrayList<Biome> ForestList = new ArrayList<Biome>(Arrays.asList(Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.ROOFED_FOREST));
	
	@Override
	public void generate(Random Rand, int chunkX, int chunkZ, World World, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(World.provider.getDimension() == 0) {
			runGen(APPLE, World, Rand, chunkX, chunkZ, -1, 0);
		}
	}
	
	private void runGen(WorldGenerator Gen, World World, Random Rand, int chunkX, int chunkZ, int minHeight, int maxHeight) {
		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < 16; i++) {
			BlockPos Pos = new BlockPos(chunkX * 16 + 10 + Rand.nextInt(15), minHeight + Rand.nextInt(heightDiff), chunkZ * 16 + 10 + Rand.nextInt(15));
			if(minHeight < 0) Pos = World.getHeight(Pos);
			
			Biome biome = World.provider.getBiomeForCoords(Pos);
			int Chance = this.getSpawnChance(biome);
			if(Chance < 0) continue;
			if(Rand.nextInt(Chance) == 0) Gen.generate(World, Rand, Pos);
		}
	}
	
	private int getSpawnChance(Biome biome) {
		if(plainsList.contains(biome)) {
			return 600;
		} else if (biome == Biomes.MUTATED_FOREST) {
			return 70;
		} else if (ForestList.contains(biome)) {
			return 300;
		} else if (biome == Biomes.EXTREME_HILLS_WITH_TREES) {
			return 350;
		}
		return -1;
	}
}
