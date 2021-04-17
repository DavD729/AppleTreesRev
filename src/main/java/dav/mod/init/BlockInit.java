package dav.mod.init;

import dav.mod.objects.blocks.tree.ApplePlantBlock;
import dav.mod.objects.blocks.tree.CustomSaplingBlock;
import dav.mod.objects.blocks.tree.CustomTree;
import dav.mod.world.gen.TreeWorldGen;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.gen.feature.Feature;

public class BlockInit {
	
	public static final Block APPLE_PLANT = new ApplePlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly(), Items.APPLE);
	public static final Block GAPPLE_PLANT = new ApplePlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly(), Items.GOLDEN_APPLE);
	
	public static final Block APPLE_SAPLING = new CustomSaplingBlock(new CustomTree(Feature.NORMAL_TREE.configure(TreeWorldGen.APPLE_TREE_CONFIG)), FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly());
	public static final Block GAPPLE_SAPLING = new CustomSaplingBlock(new CustomTree(Feature.NORMAL_TREE.configure(TreeWorldGen.GOLD_APPLE_TREE_CONFIG)), FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).ticksRandomly());
	
	public static void renderBlocks() {
		if(FabricLauncherBase.getLauncher().getEnvironmentType() == EnvType.CLIENT) {
			BlockRenderLayerMap.INSTANCE.putBlock(APPLE_SAPLING, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(GAPPLE_SAPLING, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(APPLE_PLANT, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(GAPPLE_PLANT, RenderLayer.getCutout());
		}
	}
}
