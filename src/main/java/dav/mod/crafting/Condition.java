package dav.mod.crafting;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

public class Condition implements ICondition{
	
	private final ResourceLocation ID;
	private final boolean Condition;
	
	public Condition(ResourceLocation ID, boolean Condition) {
		this.ID = ID;
		this.Condition = Condition;
	}
	
	@Override
	public ResourceLocation getID() {
		return this.ID;
	}

	@Override
	public boolean test() {
		return this.Condition;
	}
}
