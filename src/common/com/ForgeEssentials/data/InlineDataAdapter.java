package com.ForgeEssentials.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class InlineDataAdapter
{
	private DataDriver parent;
	protected Class forType;
	protected String[] savedFields;

	public InlineDataAdapter(DataDriver driver, Class type)
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
					tempList.add(f.getName());
				}
			}
		}
		this.savedFields = tempList.toArray(new String[tempList.size()]);
	}
	
	protected DataDriver getParent()
	{
		return this.parent;
	}
}
