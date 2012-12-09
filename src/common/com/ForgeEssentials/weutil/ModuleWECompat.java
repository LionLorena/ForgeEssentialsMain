package com.ForgeEssentials.weutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;

import com.ForgeEssentials.core.IFEModule;
import com.ForgeEssentials.permission.ForgeEssentialsPermissionRegistrationEvent;
import com.sk89q.worldedit.LocalEntity;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.ServerInterface;
import com.sk89q.worldedit.WorldEdit;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.IChatListener;

public class ModuleWECompat implements IFEModule, IChatListener{

	private WorldEdit we;
	private WEConfig config;
	protected ServerInterface serverInterface;
	public static ModuleWECompat instance;

	protected List<String> whitelist = new ArrayList<String>();
	private Map<EntityPlayer, LocalPlayer> players = new WeakHashMap<EntityPlayer, LocalPlayer>();
	private Map<World, LocalWorld> worlds = new WeakHashMap<World, LocalWorld>();
	private Map<Entity, LocalEntity> entities = new WeakHashMap<Entity, LocalEntity>();
	
	@Override
	public void preLoad(FMLPreInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(FMLInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postLoad(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStarting(FMLServerStartingEvent e) {
		config = new WEConfig();
		config.load();
		we = new WorldEdit(new FEServerInterface(), config);
	}

	@Override
	public void serverStarted(FMLServerStartedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Packet3Chat serverChat(NetHandler handler, Packet3Chat message) {
		if (message.message.startsWith("/wec ")) {
			we.handleCommand(getPlayer(handler.getPlayer()), message.message.split(" "));
			return new Packet3Chat("");
		}

		return message;
	}

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message) {
		return message;
	}
	@ForgeSubscribe
	public void registerPermissions(ForgeEssentialsPermissionRegistrationEvent e){
		
	}
	protected LocalPlayer getPlayer(EntityPlayer player) {
		if (players.containsKey(player)) {
			return players.get(player);
		} else {
			LocalPlayer ret = new FELocalPlayer(player);
			players.put(player, ret);
			return ret;
		}
	}

	protected LocalWorld getWorld(World world) {
		if (worlds.containsKey(world)) {
			return worlds.get(world);
		} else {
			LocalWorld ret = new FELocalWorld(world);
			worlds.put(world, ret);
			return ret;
		}
	}

	protected LocalEntity getEntity(Entity entity) {
		if (entities.containsKey(entity)) {
			return entities.get(entity);
		} else {
			LocalEntity ret = new FELocalEntity(entity);
			entities.put(entity, ret);
			return ret;
		}
	}

}
