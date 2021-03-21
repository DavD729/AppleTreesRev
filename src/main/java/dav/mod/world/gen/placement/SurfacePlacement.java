package dav.mod.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class SurfacePlacement implements IPlacementConfig {
	
	public final int surfaceChance;
	public final int genChance;
	
	public SurfacePlacement(int surfaceChance, int genChance) {
		this.surfaceChance = surfaceChance;
		this.genChance = genChance;
	}

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("surfacechance"), ops.createInt(this.surfaceChance), ops.createString("genchance"), ops.createInt(genChance))));
	}
	
	public static SurfacePlacement deserialize(Dynamic<?> Map) {
		int s = Map.get("surfacechance").asInt(0);
	    int g = Map.get("genchance").asInt(0);
	    return new SurfacePlacement(s, g);
	}
}
