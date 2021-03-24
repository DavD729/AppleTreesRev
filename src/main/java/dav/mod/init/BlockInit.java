package dav.mod.init;

import dav.mod.objects.blocks.tree.ApplePlantBlock;
import dav.mod.objects.blocks.tree.CustomSaplingBlock;
import dav.mod.objects.blocks.tree.CustomTree;
import dav.mod.world.gen.TreeWorldGen;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;

public class BlockInit {
	
	public static final Block APPLE_PLANT = new ApplePlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly(), 0);
	public static final Block GAPPLE_PLANT = new ApplePlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly(), 1);
	
	public static final Block APPLE_SAPLING = new CustomSaplingBlock(new CustomTree(TreeWorldGen.APPLE_TREE), FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly());
	public static final Block GAPPLE_SAPLING = new CustomSaplingBlock(new CustomTree(TreeWorldGen.GAPPLE_TREE), FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly());
	
	public static void renderCutoutBlocks() {
		BlockRenderLayerMap.INSTANCE.putBlock(APPLE_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GAPPLE_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(APPLE_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GAPPLE_PLANT, RenderLayer.getCutout());
	}
}
