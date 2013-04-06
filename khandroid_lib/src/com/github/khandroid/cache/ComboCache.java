package com.github.khandroid.cache;

import java.io.File;

import com.github.khandroid.misc.KhandroidLog;


public class ComboCache implements Cache {
	private MemoryCache memCache;
	private StorageCache filesCache;
	
	
	public ComboCache(
	                          int memCacheMaxTotalSize,
	                          int memCacheMaxFileSize,
	                          int memMaxEntries,
	                          File cacheDir,	                          
	                          int storageCacheMaxTotalSize,
	                          int storageCacheMaxFileSize,
	                          int storageCacheMaxEntries,
	                          long removeLifetimeMoreThanOnCreate
	                          ) {
		
		memCache = new MemoryCache(memCacheMaxTotalSize, memCacheMaxFileSize, memMaxEntries);
		filesCache = new StorageCache(cacheDir, storageCacheMaxTotalSize, storageCacheMaxFileSize, storageCacheMaxEntries, removeLifetimeMoreThanOnCreate);
		
	}
	
	@Override
	public byte[] get(Object key) {
		byte[] ret = (byte[]) memCache.get(key);
		if (ret == null) {
		    KhandroidLog.v(key + " NOT found in memory cache");
			ret = filesCache.get(key);
			if (ret != null) {
			    KhandroidLog.v(key + " found in files cache");
			    memCache.put(key, ret);
			} else {
			    KhandroidLog.v(key + " NOT found in files cache");
			}
		} else {
		    KhandroidLog.v(key + " found in memory cache");
		}
		
		return ret;
	}


	@Override
	public boolean put(Object key, Object entry) {
		memCache.put(key, entry);
		return filesCache.put(key, entry);
	}


	@Override
	public void clearAll() {
		memCache.clearAll();
		filesCache.clearAll();
	}


	@Override
	public void remove(Object key) {
		if (memCache.containsKey(key)) {
			memCache.remove(key);
		}

		if (filesCache.containsKey(key)) { 
			filesCache.remove(key);
		}
	}


	@Override
	public boolean containsKey(Object key) {
		return (memCache.containsKey(key) || filesCache.containsKey(key));
	}
}
