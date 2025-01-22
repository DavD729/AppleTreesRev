package net.dav.appletreesrev.compat.hwyla;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.cbcore.LangUtil;
import net.dav.appletreesrev.objects.block.BlockApplePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class ApplePlantWailaProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IBlockState state = accessor.getBlockState();
        if (state.getBlock() instanceof BlockApplePlant) {
            BlockApplePlant blockIn = (BlockApplePlant) state.getBlock();
            float age = ((float) state.getValue(BlockApplePlant.AGE) / blockIn.getMaxAge()) * 100.0F;
            if (!blockIn.isMaxAge(state)) {
                tooltip.add(LangUtil.translateG("hud.msg.growth") + " : " + LangUtil.translateG("hud.msg.growth.value", (int) age));
            } else {
                tooltip.add(LangUtil.translateG("hud.msg.growth") + " : " + LangUtil.translateG("hud.msg.mature"));
            }
        }
        return tooltip;
    }
}
