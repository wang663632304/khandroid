package com.github.khandroid.cache;

import java.io.File;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class ImageBitmapCache {
    private final LruCache<String, Bitmap> memCache;
    private final StorageCache filesCache;
    private final int maxFileSize;
    
    
    private ImageBitmapCache(
                       int maxFileSize,
                       int memMaxEntries,
                       File cacheDir,                              
                       int storageCacheMaxTotalSize,
                       int storageCacheMaxEntries,
                       long removeLifetimeMoreThanOnCreate                       
                       ) {

        this.maxFileSize = maxFileSize;
        
        memCache = new LruCache<String, Bitmap>(memMaxEntries);
        filesCache = new StorageCache(cacheDir, storageCacheMaxTotalSize, maxFileSize, storageCacheMaxEntries, removeLifetimeMoreThanOnCreate);
    }
    
    
    public Bitmap get(String key) {
        Bitmap ret;
        
        ret = memCache.get((String)key);
        if (ret == null) {
            byte[] imageFileData = filesCache.get(key);
            if (imageFileData != null) {
                ret = BitmapFactory.decodeByteArray(imageFileData, 0, imageFileData.length);
                memCache.put(key, ret);
            }
        }
        
        return ret;
    }
    

    public LruCache<String, Bitmap> getMemCache() {
        return memCache;
    }


    public StorageCache getFilesCache() {
        return filesCache;
    }


    public boolean put(String filename, byte[] imageFileData) {
        boolean ret = false;
        
        byte[] fileData = (byte[]) imageFileData;
        
        if (fileData.length <= maxFileSize) {
            Bitmap tmp = BitmapFactory.decodeByteArray(imageFileData, 0, imageFileData.length);
            memCache.put(filename, tmp);
            
            ret = filesCache.put(filename, imageFileData);
        }
        
        
        return ret;
    }
    
    
    public void clearAll() {
        memCache.clear();
        filesCache.clearAll();
    }

    public void remove(Object key) {
        if (memCache.containsKey(key)) {
            memCache.remove((String) key);
        }

        if (filesCache.containsKey(key)) { 
            filesCache.remove(key);
        }
    }

    public boolean containsKey(Object key) {
        return (memCache.containsKey(key) || filesCache.containsKey(key));
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
