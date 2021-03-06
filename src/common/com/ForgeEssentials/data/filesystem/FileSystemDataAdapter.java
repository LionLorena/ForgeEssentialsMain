package com.ForgeEssentials.data.filesystem;

import com.ForgeEssentials.data.IDataAdapter;
import com.ForgeEssentials.util.AreaSelector.Point;
import com.ForgeEssentials.util.AreaSelector.WorldPoint;

/**
 * This class provides some handy conversions between ForgeEssentials types and
 * primitives that are easy to write to disk.
 * They are here rather than in upper classes to eliminate code duplication.
 * 
 * @author MysteriousAges
 *
 * @param <T> Class that extending Persisters save data for.
 */
public abstract class FileSystemDataAdapter<SavedType, KeyType> implements IDataAdapter<SavedType, KeyType>
{	
	// Convenience function to convert Points into an int[3]
	protected int[] pointToIntArray(Point p)
	{
		int[] arr = new int[3];
		arr[0] = p.x;
		arr[1] = p.y;
		arr[2] = p.z;
		return arr;
	}
	
	// Convenience function to convert WorldPoints into ant int[4]
	protected int[] worldPointToIntArray(WorldPoint p)
	{
		int[] arr = new int[4];
		arr[0] = p.x;
		arr[1] = p.y;
		arr[2] = p.z;
		arr[3] = p.dim;
		return arr;
	}
}
