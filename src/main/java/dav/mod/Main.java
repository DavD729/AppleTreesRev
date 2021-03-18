package dav.mod;

import java.io.File;

import dav.mod.proxy.CommonProxy;
import dav.mod.util.Reference;
import dav.mod.util.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
public static File config;
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		RegistryHandler.preInitRegistries(event);
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event) {
		RegistryHandler.InitRegistries(event);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
	}
}
