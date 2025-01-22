package net.dav.appletreesrev.compat.hwyla;

import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.dav.appletreesrev.objects.block.BlockApplePlant;

@WailaPlugin
public class HwylaCompatHandler implements IWailaPlugin {
    @Override
    public void register(IWailaRegistrar iWailaRegistry) {
        iWailaRegistry.registerBodyProvider(new ApplePlantWailaProvider(), BlockApplePlant.class);
    }
}
