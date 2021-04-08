package dav.mod.crafting;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConditionSerializer implements IConditionSerializer<ICondition>{
	
	private final ICondition Condition;
	private final ResourceLocation ID;
	
	public ConditionSerializer(ICondition Condition, ResourceLocation ID) {
		this.Condition = Condition;
		this.ID = ID;
	}
	
	@Override
	public void write(JsonObject json, ICondition value) {
	}

	@Override
	public ICondition read(JsonObject json) {
		return this.Condition;
	}

	@Override
	public ResourceLocation getID() {
		return ID;
	}

}
