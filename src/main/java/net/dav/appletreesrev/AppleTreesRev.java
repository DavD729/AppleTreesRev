package net.dav.appletreesrev;

import net.dav.appletreesrev.proxy.CommonProxy;
import net.dav.appletreesrev.util.Reference;
import net.dav.appletreesrev.util.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.RANGE)
public class AppleTreesRev {
	@Mod.Instance
	public static AppleTreesRev instance;

	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;

	private static Logger logger;
	public static File config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		RegistryHandler.preInitRegistries(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		RegistryHandler.InitRegistries(event);
	}

	public static Logger getLogger() {
		return AppleTreesRev.logger;
	}
}
