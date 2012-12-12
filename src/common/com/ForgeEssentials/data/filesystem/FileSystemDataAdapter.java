package com.ForgeEssentials.data.filesystem;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.ForgeEssentials.data.*;

/**
 * This class provides some handy conversions between ForgeEssentials types and
 * primitives that are easy to write to disk.
 * They are here rather than in upper classes to eliminate code duplication.
 * 
 * @author MysteriousAges
 *
 * @param <T> Class that extending Persisters save data for.
 */
public class FileSystemDataAdapter extends DataAdapter
{

	public FileSystemDataAdapter(DataDriver driver, Class type)
	{
		super(driver, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean saveData(Object object)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SavedField[] loadData(Object uniqueKey)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SavedField[][] loadAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteData(Object uniqueKey)
	{
		// TODO Auto-generated method stub
		return false;
	}	

}
