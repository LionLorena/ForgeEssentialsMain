package com.ForgeEssentials.core;

import java.io.File;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import com.ForgeEssentials.client.core.network.HandlerClient;
import com.ForgeEssentials.core.commands.CommandFECredits;
import com.ForgeEssentials.core.commands.CommandFEUpdate;
import com.ForgeEssentials.core.commands.CommandFEVersion;
import com.ForgeEssentials.core.network.HandlerServer;
import com.ForgeEssentials.data.DataDriver;
import com.ForgeEssentials.data.DataStorageManager;
import com.ForgeEssentials.util.DataStorage;
import com.ForgeEssentials.util.Localization;
import com.ForgeEssentials.util.TeleportCenter;
import com.ForgeEssentials.util.Version;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

/**
 * Main mod class
 */

@NetworkMod(clientSideRequired = false, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "ForgeEssentials" }, packetHandler = HandlerClient.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "ForgeEssentials" }, packetHandler = HandlerServer.class))
@Mod(modid = "ForgeEssentials", name = "Forge Essentials", version = "0.1.0")
public class ForgeEssentials
{
	@SidedProxy(clientSide = "com.ForgeEssentials.client.core.ProxyClient", serverSide = "com.ForgeEssentials.core.ProxyCommon")
	public static ProxyCommon proxy;

	@Instance(value = "ForgeEssentials")
	public static ForgeEssentials instance;

	public static CoreConfig config;
	public ModuleLauncher mdlaunch;
	public Localization localization;
	public static boolean verCheck = true;
	public static boolean preload;

	public static String modlistLocation;
	public static String fedirloc = "./ForgeEssentials/";

	public static final File FEDIR = new File(fedirloc);
	
	private DataDriver dataStore = null;

	@PreInit
	public void preInit(FMLPreInitializationEvent e)
	{
		
		config = new CoreConfig();

		if (verCheck)
		{
			Version.checkVersion();
		}

		mdlaunch = new ModuleLauncher();
		mdlaunch.preLoad(e);

		localization = new Localization();
	}

	@Init
	public void load(FMLInitializationEvent e)
	{
		proxy.load(e);
		mdlaunch.load(e);
		localization.load();
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}

	@PostInit
	public void postLoad(FMLPostInitializationEvent e)
	{
		mdlaunch.postLoad(e);
	}
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent e)
	{
		mdlaunch.serverStarting(e);
		ModListFile.makeModList();
		
		// Add hooks for initializing new data backing API
		DataStorageManager.setupDriver(config.config, e);
		
		//Central TP system
		TickRegistry.registerScheduledTickHandler(new TeleportCenter(), Side.SERVER);
		
		DataStorage.load();
		e.registerServerCommand(new CommandFEVersion());
		e.registerServerCommand(new CommandFEUpdate());
		e.registerServerCommand(new CommandFECredits());
	}
	
	@ServerStarted
	public void serverStarted(FMLServerStartedEvent e)
	{
		mdlaunch.serverStarted(e);
	}
	
	@ServerStopping
	public void serverStopping(FMLServerStoppingEvent e)
	{
		mdlaunch.serverStopping(e);
		DataStorage.save();
	}
	
	@ForgeSubscribe
	public void chuckSave(WorldEvent.Save event)
	{
		DataStorage.save();
	}

	public DataDriver getDataDriver()
	{
		return dataStore;
	}
	
	public static DataDriver getInstanceDataDriver()
	{
		DataDriver d = null;
		if (ForgeEssentials.instance != null && instance.dataStore != null)
		{
			d = instance.dataStore;
		}
		return d;
	}

	public void setDataStore(DataDriver driver)
	{
		// Only set this once to prevent strangeness.
		if (this.dataStore == null)
		{
			this.dataStore = driver;
		}
	}
}
