package dav.mod.world.gen.placement;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class SurfacePlacement implements IPlacementConfig {
	
	public final int surfaceChance;
	public final int genChance;
	
	public SurfacePlacement(int chance, int gen) {
		this.surfaceChance = chance;
		this.genChance = gen;
	}
}
