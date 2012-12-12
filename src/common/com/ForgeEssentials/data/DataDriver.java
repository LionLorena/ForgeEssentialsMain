package com.ForgeEssentials.data;

import java.util.*;

import net.minecraftforge.common.Configuration;

import com.ForgeEssentials.util.OutputHandler;


public abstract class DataDriver
{	
	// Stores bindings between logic classes and their data classes.
	protected HashMap<Class, DataAdapter> map;
	// Stores bindings for classes saved inline and their adapters.
	protected HashMap<Class, InlineDataAdapter> inlineMap;

	public DataDriver()
	{
		
		this.map = new HashMap<Class, DataAdapter>();
		this.inlineMap = new HashMap<Class, InlineDataAdapter>();
	}

	public abstract boolean parseConfigs(Configuration config, String worldName);

	public Class getDataDriverType()
	{
		return this.getClass();
	}

	public boolean registerClass(Class type)
	{
		boolean flag = false;
		SaveableObject a;
		if ((a = (SaveableObject)type.getAnnotation(SaveableObject.class)) != null)
		{
			try
			{
				if (a.SaveInline())
				{
					this.registerInlineAdapterForType(type);
				}
				else
				{
					this.registerAdapterForType(type);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	protected abstract void registerAdapterForType(Class type);
	
	protected abstract void registerInlineAdapterForType(Class type);

	public boolean hasMapping(Object o)
	{
		return this.map.containsKey(o.getClass()) || this.inlineMap.containsKey(o.getClass());
	}	

	public boolean saveObject(Object o)
	{
		boolean flag = false;
		if (this.hasMapping(o))
		{
			DataAdapter da = this.map.get(o.getClass());
			
			if (da != null)
			{
				flag = da.saveData(o);
			}
			else
			{
				OutputHandler.SOP("DataDriver " + " does not have an instance for " + o.getClass());
			}
		}
		return flag;
	}

	public SavedField[] loadObject(Class type, Object loadingKey)
	{
		SavedField[] data = null;
		if (this.hasMapping(type))
		{
			DataAdapter da = this.map.get(type);
				
			if (da != null)
			{
				data = da.loadData(loadingKey);
			}
			else
			{
				OutputHandler.SOP("DataDriver does not have an instance for " + type.getName());
			}
		}
		return data;
	}
	
	public SavedField[][] loadAllObjects(Class type)
	{
		// TODO: This.
		return null;
	}
	
	public boolean deleteObject(Class type, Object loadingKey)
	{
		// TODO: This.
		return false;
	}
}
