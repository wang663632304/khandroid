package com.github.khandroid.cache;

import java.util.Map;

import com.github.khandroid.misc.KhandroidLog;


public class MemoryCache implements Cache {
	private int maxTotalSize;
	private int maxFileSize; 
	private int size;	
	

	LruCache<String, byte[]> memCache;
	
	
	public MemoryCache(int maxTotalSize, int maxFileSize, int maxEntries) {
		this.maxTotalSize = maxTotalSize;
		this.maxFileSize = maxFileSize;
		
		memCache = new LruCache<String, byte[]>(maxEntries) {
			@Override
            protected void entryRemoved(boolean evicted, String key, byte[] oldValue, byte[] newValue) {
				size -= oldValue.length;
				KhandroidLog.v("Entry removed: " + key);
				MemoryCache.this.entryEvicted(oldValue);
			}
		};
	}
	
	
	protected void entryEvicted(Object entry) {
		
	}
	
	
	@Override
	public Object get(Object key) {
		return memCache.get((String) key);
	}


	@Override
	public boolean put(Object key, Object entry) {
		boolean ret = false;
		
		if (!memCache.containsKey(key)) {
			if (((byte[])entry).length <= maxFileSize ) {
				if ((size + ((byte[])entry).length) > maxTotalSize) {
				    KhandroidLog.v("Memory cache reached maxTotalSize. Will try to free room.");
					if (freeEnoughSpace(((byte[])entry).length)) {
					    KhandroidLog.v("Room freed.");
						actualPut((String)key, (byte[])entry);
						ret = true;
					} else {
					    KhandroidLog.v("Room was not freed.");
					}
				} else {
					actualPut((String)key, (byte[])entry);
					ret = true;
				}
			} else {
			    KhandroidLog.v("Entry is bigger than maxFileSize: " + key);
			}
		} else {
		    actualPut((String)key, (byte[])entry);
		}
		
		return ret;
	}
	
	
	private void actualPut(String key, byte[] entry) {
	    KhandroidLog.v("Memory cache: putting " + key);
		memCache.put((String)key, (byte[]) entry);
		size += ((byte[]) entry).length;
	}

	
	private boolean freeEnoughSpace(long enough) {
		boolean ret = false;
		
		if (enough <= 0) {
			throw new IllegalArgumentException("enough <= 0");
		}
		
		
		byte[] evicted = evictOldest();
		if (evicted != null) {
			if (evicted.length >= enough) {  
				ret = true;
			} else {
				freeEnoughSpace(enough - evicted.length);
			}
		}
		
		return ret;
	}
	
	
	private byte[] evictOldest() {
		byte[] ret;
		
		Map.Entry<String, byte[]> toEvict = memCache.entrySet().iterator().next();
		if (toEvict != null) {
			remove(toEvict.getKey());
			entryEvicted(toEvict.getValue());
			ret = toEvict.getValue();
		} else {
			ret = null;
		}
		
		return ret;
	}
		

	@Override
	public void clearAll() {
		size = 0;
		memCache.evictAll();
	}


	@Override
	public void remove(Object key) {
		memCache.remove((String) key);
	}


	@Override
	public boolean containsKey(Object key) {
		return memCache.containsKey(key);
	}
}
