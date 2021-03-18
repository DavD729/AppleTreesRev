package dav.mod.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import dav.mod.world.gen.tree.NaturalAppleTreeGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TreeWorldGen implements IWorldGenerator{
	
	private final WorldGenerator APPLE = new NaturalAppleTreeGen();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		switch(world.provider.getDimension()) {
		case 1: break;
		case 0: runGenerator(APPLE, world, random, chunkX, chunkZ, 600.0D, -1, 0, BiomePlains.class);
			runGenerator(APPLE, world, random, chunkX, chunkZ, 350.0D, -1, 0, BiomeForest.class);
			break;
		case -1: break;
		}
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, double chancesToSpawn, int minHeight, int maxHeight, Class<?>... classes) {
		
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < 20; i++) {
			BlockPos pos = new BlockPos(chunkX * 16 + 10 + random.nextInt(15), minHeight + random.nextInt(heightDiff), chunkZ * 16 + 10 + random.nextInt(15));
			if(minHeight < 0) pos = world.getHeight(pos);
			Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
			if(classesList.contains(biome) || classes.length == 0) {
				if(random.nextInt((int)chancesToSpawn) == 0) generator.generate(world, random, pos);
			}
		}
	}
}
