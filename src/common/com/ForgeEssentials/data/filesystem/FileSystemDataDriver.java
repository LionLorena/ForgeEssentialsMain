package com.ForgeEssentials.data.filesystem;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.ForgeEssentials.core.ForgeEssentials;
import com.ForgeEssentials.core.PlayerInfo;
import com.ForgeEssentials.data.DataDriver;
import com.ForgeEssentials.permission.Zone;

/**
 * Storage driver for filesystem (flat-file) persistence.
 * 
 * @author MysteriousAges
 *
 */
public class FileSystemDataDriver extends DataDriver
{
	public static final String driverType = "FileSystem";
	
	private String baseFilePath;
	public static String newline = "\r\n";

	public FileSystemDataDriver()
	{
		super();
		
		DataDriver.instance = this;
	}
	
	@Override
	public boolean parseConfigs(Configuration config, String worldName)
	{
		Property prop;
		
		prop = config.get("Data.FileSystem", "useFEDataDir", false);
		prop.comment = "Set to true to use the '.minecraft/ForgeEssentials/saves' directory instead of a world. Server owners may wish to set this to true.";
		
		boolean useFEDir = prop.getBoolean(false);
		
		if (useFEDir)
		{
			this.baseFilePath = ForgeEssentials.FEDIR.toString() + "saves/" + worldName + "/";
		}
		else
		{
			try
			{
				Class c = Class.forName("net.minecraft.src.IntegratedServer");
				// We are running from the client. Use the Client save directory.
				this.baseFilePath = "./saves/" + worldName + "/FEData/";
			}
			catch (Exception e)
			{
				// Dedicated server. Use the base path + world name.
				this.baseFilePath = "./" + worldName +"/FEData/";
			}
		}
		
		config.save();
		
		// Nothing to fail on.
		return true;
	}
	
	public String getBaseBath()
	{
		return this.baseFilePath;
	}

	/**
	 * This function binds all DataAdapters
	 * @param obj 
	 */
	protected void registerAdapters()
	{
		this.map.put(PlayerInfo.class, new PlayerInfoDataAdapter());
		this.map.put(Zone.class, new ZoneDataAdapter());
		// Add additional flat-file storage classes here.
	}
}
