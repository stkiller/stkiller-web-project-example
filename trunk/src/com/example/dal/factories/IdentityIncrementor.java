package com.example.dal.factories;

import java.util.HashMap;
import java.util.Map;

import com.example.dal.valueobject.ValueObject;

public class IdentityIncrementor {
	private static Map<Class<?>, Long> idetities = new HashMap<Class<?>, Long>();
	
	public static void incrementIdentity(ValueObject valueObject){
		if(!idetities.containsKey(valueObject.getClass())){
			idetities.put(valueObject.getClass(), 0l);
		}
		valueObject.setId(idetities.get(valueObject.getClass()));
		idetities.put(valueObject.getClass(), idetities.get(valueObject.getClass())+1);
	}

	public static Map<Class<?>, Long> getIdetities() {
		return idetities;
	}
	
	
}
