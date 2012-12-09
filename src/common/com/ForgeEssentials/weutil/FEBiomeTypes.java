package com.ForgeEssentials.weutil;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.BiomeGenBase;

import com.sk89q.worldedit.BiomeType;
import com.sk89q.worldedit.BiomeTypes;
import com.sk89q.worldedit.UnknownBiomeTypeException;

public class FEBiomeTypes implements BiomeTypes {
	@Override
	public List<BiomeType> all() {
		List<BiomeType> ret = new ArrayList<com.sk89q.worldedit.BiomeType>();

		for (BiomeGenBase biome : BiomeGenBase.biomeList) {
			if (biome != null) ret.add(new BiomeType(biome));
		}

		return ret;
	}

	@Override
	public BiomeType get(String arg0) throws UnknownBiomeTypeException {
		for (BiomeType biome : all()) {
			if (biome.getName().equalsIgnoreCase(arg0)) return biome;
		}

		throw new UnknownBiomeTypeException(arg0);
	}

	@Override
	public boolean has(String arg0) {
		for (BiomeType biome : all()) {
			if (biome.getName().equalsIgnoreCase(arg0)) return true;
		}

		return false;
	}
}