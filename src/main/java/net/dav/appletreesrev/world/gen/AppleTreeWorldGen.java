package net.dav.appletreesrev.world.gen;

import net.dav.appletreesrev.world.gen.feature.AppleTreeGen;
import net.dav.appletreesrev.world.gen.feature.BigAppleTreeGen;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.*;

public class AppleTreeWorldGen implements IWorldGenerator {
    private final WorldGenerator APPLE_TREE = new AppleTreeGen(0, true);
    private final WorldGenerator BIG_APPLE_TREE = new BigAppleTreeGen(0, true);

    private final Map<Biome, Integer> biomeChances = new HashMap<>();

    public AppleTreeWorldGen() {
        biomeChances.put(Biomes.PLAINS, 600);
        biomeChances.put(Biomes.MUTATED_PLAINS, 600);
        biomeChances.put(Biomes.FOREST, 300);
        biomeChances.put(Biomes.FOREST_HILLS, 300);
        biomeChances.put(Biomes.ROOFED_FOREST, 300);
        biomeChances.put(Biomes.MUTATED_FOREST, 70);
        biomeChances.put(Biomes.EXTREME_HILLS_WITH_TREES, 350);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            WorldGenerator worldGen = random.nextInt(12) == 0 ? BIG_APPLE_TREE : APPLE_TREE ;
            runGen(worldGen, world, random, chunkX, chunkZ);
        }
    }

    private void runGen(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ) {
        int middleX = chunkX * 16 + 8;
        int middleZ = chunkZ * 16 + 8;
        int treesGenerated = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for(int x = middleX - 4; x <= middleX + 3 && treesGenerated < 2; x++) {
            for(int z = middleZ - 4; z <= middleZ + 3 && treesGenerated < 2; z++) {
                mutablePos.setPos(world.getHeight(mutablePos.setPos(x, 0, z)));

                Biome biome = world.getBiome(mutablePos);
                Integer chance = biomeChances.get(biome);
                if (chance == null) continue;
                if (rand.nextInt(chance) == 0) {
                    if(gen.generate(world, rand, mutablePos.toImmutable())) treesGenerated++;
                }
            }
        }
    }
}
