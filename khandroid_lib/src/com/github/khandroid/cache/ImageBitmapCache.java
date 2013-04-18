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


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class ImageBitmapCache {
    private final LruCache<String, Bitmap> mMemCache;
    private final StorageCache mFilesCache;
    private final int mMaxFileSize;
    
    
    private ImageBitmapCache(
                       int maxFileSize,
                       int memMaxEntries,
                       File cacheDir,                              
                       int storageCacheMaxTotalSize,
                       int storageCacheMaxEntries,
                       long removeLifetimeMoreThanOnCreate                       
                       ) {

        this.mMaxFileSize = maxFileSize;
        
        mMemCache = new LruCache<String, Bitmap>(memMaxEntries);
        mFilesCache = new StorageCache(cacheDir, storageCacheMaxTotalSize, maxFileSize, storageCacheMaxEntries, removeLifetimeMoreThanOnCreate);
    }
    
    
    public Bitmap get(String key) {
        Bitmap ret;
        
        ret = mMemCache.get((String)key);
        if (ret == null) {
            byte[] imageFileData = mFilesCache.get(key);
            if (imageFileData != null) {
                ret = BitmapFactory.decodeByteArray(imageFileData, 0, imageFileData.length);
                mMemCache.put(key, ret);
            }
        }
        
        return ret;
    }
    

    public LruCache<String, Bitmap> getMemCache() {
        return mMemCache;
    }


    public StorageCache getFilesCache() {
        return mFilesCache;
    }


    public boolean put(String filename, byte[] imageFileData) {
        boolean ret = false;
        
        byte[] fileData = (byte[]) imageFileData;
        
        if (fileData.length <= mMaxFileSize) {
            Bitmap tmp = BitmapFactory.decodeByteArray(imageFileData, 0, imageFileData.length);
            mMemCache.put(filename, tmp);
            
            ret = mFilesCache.put(filename, imageFileData);
        }
        
        
        return ret;
    }
    
    
    public void clearAll() {
        mMemCache.clear();
        mFilesCache.clearAll();
    }

    public void remove(Object key) {
        if (mMemCache.containsKey(key)) {
            mMemCache.remove((String) key);
        }

        if (mFilesCache.containsKey(key)) { 
            mFilesCache.remove(key);
        }
    }

    public boolean containsKey(Object key) {
        return (mMemCache.containsKey(key) || mFilesCache.containsKey(key));
    }

    
    public static class Builder {
        private int maxFileSize = 102400; // 100 KiB
        
        private int memMaxEntries = 200;
        private File cacheDir;
        private int storageCacheMaxTotalSize = 20971520; // 20 MiB
        private int storageCacheMaxEntries = 400;
        private int removeLifetimeMoreThanOnCreate = 60 * 60 * 24 * 7; // 7 days 
        
        
        public Builder(File cacheDir) {
            super();
            this.cacheDir = cacheDir;
        }

        
        public Builder setStorageCacheMaxFileSize(int maxFileSize) {
            this.maxFileSize = maxFileSize;
            
            return this;
        }        
        
       
        public Builder setMemMaxEntries(int memMaxEntries) {
            this.memMaxEntries = memMaxEntries;
            
            return this;
        }
        
        
        public Builder setStorageCacheMaxTotalSize(int storageCacheMaxTotalSize) {
            this.storageCacheMaxTotalSize = storageCacheMaxTotalSize;
            
            return this;
        }
        
        
        public Builder setStorageCacheMaxEntries(int storageCacheMaxEntries) {
            this.storageCacheMaxEntries = storageCacheMaxEntries;
            
            return this;
        }
        
        
        public Builder setStorageCacheLifetime(int storageCacheLifetime) {
            this.removeLifetimeMoreThanOnCreate = storageCacheLifetime;
            
            return this;
        }
        
        
        public ImageBitmapCache build() {
            return new ImageBitmapCache(maxFileSize,
                                  memMaxEntries,
                                  cacheDir,                              
                                  storageCacheMaxTotalSize,
                                  storageCacheMaxEntries,
                                  removeLifetimeMoreThanOnCreate                       
                                  );
        }
    }
}
