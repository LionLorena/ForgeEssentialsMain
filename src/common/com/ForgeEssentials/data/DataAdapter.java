package com.ForgeEssentials.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;

public abstract class DataAdapter
{
	private DataDriver parent;
	protected Class forType;
	protected String loadingField;
	protected String[] savedFields;

	public DataAdapter(DataDriver driver, Class type)
	{
		this.parent = driver;
		this.forType = type;

		ArrayList<String> tempList = new ArrayList<String>();
		Annotation a;
		// Locate all members that are saveable.
		for (Field f : this.forType.getDeclaredFields())
		{
			if ((a = f.getAnnotation(SaveableField.class)) != null)
			{
				SaveableField sf = (SaveableField)a;
				if (sf.objectLoadingField())
				{
					this.loadingField = f.getName();
				}
				else
				{
					tempList.add(f.getName());
				}
			}
		}
		this.savedFields = tempList.toArray(new String[tempList.size()]);
	}
	
	protected DataDriver getDriver()
	{
		return this.parent;
	}
	
	protected SavedField[] createSavedFieldList(Object savedObject)
	{
		return null;
	}
	
	abstract public boolean saveData(Object savedObject);
	
	abstract public SavedField[] loadData(Object uniqueKey);
	
	abstract public SavedField[][] loadAll();
	
	abstract public boolean deleteData(Object uniqueObjectKey);
}
