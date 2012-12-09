package com.ForgeEssentials.weutil;

import java.io.File;

import com.ForgeEssentials.core.ForgeEssentials;
import com.sk89q.worldedit.util.PropertiesConfiguration;

public class WEConfig extends PropertiesConfiguration{

	public WEConfig() {
		super(new File(ForgeEssentials.FEDIR, "worldedit.cfg"));
		// TODO Auto-generated constructor stub
	}
	@Override
	public File getWorkingDirectory(){
		return ForgeEssentials.FEDIR;
		
	}

}
