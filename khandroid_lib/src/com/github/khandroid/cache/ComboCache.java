/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.khandroid.cache;

import java.io.File;

import com.github.khandroid.misc.KhandroidLog;


public class ComboCache implements Cache {
	private MemoryCache mMemCache;
	private StorageCache mFilesCache;
	
	
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
		
		mMemCache = new MemoryCache(memCacheMaxTotalSize, memCacheMaxFileSize, memMaxEntries);
		mFilesCache = new StorageCache(cacheDir, storageCacheMaxTotalSize, storageCacheMaxFileSize, storageCacheMaxEntries, removeLifetimeMoreThanOnCreate);
		
	}
	
	@Override
	public byte[] get(Object key) {
		byte[] ret = (byte[]) mMemCache.get(key);
		if (ret == null) {
		    KhandroidLog.v(key + " NOT found in memory cache");
			ret = mFilesCache.get(key);
			if (ret != null) {
			    KhandroidLog.v(key + " found in files cache");
			    mMemCache.put(key, ret);
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
		mMemCache.put(key, entry);
		return mFilesCache.put(key, entry);
	}


	@Override
	public void clearAll() {
		mMemCache.clearAll();
		mFilesCache.clearAll();
	}


	@Override
	public void remove(Object key) {
		if (mMemCache.containsKey(key)) {
			mMemCache.remove(key);
		}

		if (mFilesCache.containsKey(key)) { 
			mFilesCache.remove(key);
		}
	}


	@Override
	public boolean containsKey(Object key) {
		return (mMemCache.containsKey(key) || mFilesCache.containsKey(key));
	}
}
