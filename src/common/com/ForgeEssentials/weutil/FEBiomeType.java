package com.ForgeEssentials.weutil;

import com.sk89q.worldedit.BiomeType;

import net.minecraft.src.BiomeGenBase;

public class FEBiomeType implements BiomeType {
	protected BiomeGenBase biome;

	public FEBiomeType(BiomeGenBase biome) {
		this.biome = biome;
	}

	@Override
	public String getName() {
		return biome.biomeName;
	}
}