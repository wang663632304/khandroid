package com.github.khandroid.cache;


interface Cache {
	public Object get(Object key);
	public boolean put(Object key, Object entry);
	public void clearAll();
	public void remove(Object key);
	public boolean containsKey(Object key);
}
