package dav.mod.init;

import dav.mod.objects.block.tree.BlockApplePlant;
import dav.mod.objects.block.tree.CustomSaplingBlock;
import dav.mod.world.gen.feature.AppleTreeFeature;
import dav.mod.world.gen.feature.GoldenAppleTreeFeature;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class BlockInit {
	
	public static final Block APPLE_PLANT = new BlockApplePlant(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly(), Items.APPLE);
	public static final Block GAPPLE_PLANT = new BlockApplePlant(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly(), Items.GOLDEN_APPLE);
	public static final Block APPLE_SAPLING = new CustomSaplingBlock(new CustomTree(new AppleTreeFeature(DefaultFeatureConfig::deserialize)), FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly());
	public static final Block GAPPLE_SAPLING = new CustomSaplingBlock(new CustomTree(new GoldenAppleTreeFeature(DefaultFeatureConfig::deserialize)), FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly());
}
