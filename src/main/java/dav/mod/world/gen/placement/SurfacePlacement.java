package dav.mod.world.gen.placement;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class SurfacePlacement implements IPlacementConfig {
	
	public static final Codec<SurfacePlacement> CODEC = Codec.intRange(1, 1000).fieldOf("genChance").xmap(SurfacePlacement::new, (SurfacePlacement) -> {
		return SurfacePlacement.genChance;
	}).codec();
			
	public final int genChance;
	
	public SurfacePlacement(int genChance) {
		this.genChance = genChance;
	}
}
