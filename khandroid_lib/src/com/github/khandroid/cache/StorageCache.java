package com.github.khandroid.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.github.khandroid.misc.KhandroidLog;


public class StorageCache implements Cache {
	public static final String PREFIX = "cache_";
	
	private File cacheDir;
	private int maxTotalSize;
	private int maxFileSize; 
	private int size;
	
	LruCache<String, StorageCache.FileInfo> filesCache;
	
	
	/**
	 * 
	 * @param cacheDir Directory for cache files
	 * @param maxTotalSize Maximal size of the cache in bytes
	 * @param maxFileSize Maximal size of a file in bytes
	 * @param maxFiles Maximum number of files in cache
	 * @param removeLifetimeMoreThanOnCreate Files with lifetime more that specified in seconds here will be deleted on cache creation
	 */
	public StorageCache(File cacheDir, int maxTotalSize, int maxFileSize, int maxFiles, long removeLifetimeMoreThanOnCreate) {
		if (!cacheDir.exists()) {
			if (cacheDir.mkdirs()) {
			    this.cacheDir = cacheDir;
			} else {
				throw new IllegalArgumentException("Cannot create path: " + cacheDir.getAbsolutePath());
			}
		} else {
			if (cacheDir.isDirectory()) { 
				if (cacheDir.canRead() && cacheDir.canWrite()) {
					this.cacheDir = cacheDir;
				} else {
					throw new IllegalArgumentException("Insufficient privileges for: " + cacheDir.getAbsolutePath());
				}
			} else {
				throw new IllegalArgumentException("Not a directory: " + cacheDir.getAbsolutePath());
			}
		}
		
		filesCache = new LruCache<String, StorageCache.FileInfo>(maxFiles) {
			protected void entryRemoved(boolean evicted, String filename, FileInfo oldValue, FileInfo newValue) {
				if (evicted) {
					//if entry is evicted to make room we have to delete the file
					deleteFile(oldValue);
					fileEvicted(oldValue);
				}
			}
		};
		
		this.maxTotalSize = maxTotalSize;
		this.maxFileSize = maxFileSize;
		
		File[] files = cacheDir.listFiles();
		if (files != null) { 
			for(int i = 0; i < files.length; i++) {
			    if (files[i].isFile()) {
    				if ((files[i].lastModified() + removeLifetimeMoreThanOnCreate * 1000) > (new Date()).getTime()) {
    					StorageCache.FileInfo tmp = new  StorageCache.FileInfo(files[i].getName(), 
    					                                                       files[i].length() 
    					                                                       );
    					
    					if (size + files[i].length() < maxTotalSize) {
    					    KhandroidLog.v("Loaded in file cache: " + files[i].getName());
    						filesCache.put(extractOriginalFilename(files[i].getName()), tmp);
    						size += files[i].length();
    					}
    				} else {
    					files[i].delete();
    				}
			    }
			}
		}
	}
	
	
	private String composeCacheFilename(String filename) {
		return PREFIX + filename;
	}
	
	
	private String extractOriginalFilename(String cacheFilename) {
		String ret;
		
		if (cacheFilename.indexOf(PREFIX) != -1) {
			ret = cacheFilename.substring(PREFIX.length());
		} else {
			ret = null;
		}
		
		return ret;
	}
	
	
	@Override
	public byte[] get(Object key) {
		return get(((String) key));
	}
	
	
	public byte[] get(String filename) {
		byte[] ret = null;
		
		if (filesCache.containsKey(filename)) {
			FileInfo fi = filesCache.get(filename);
			BufferedInputStream input;
			
			String path = cacheDir + "/" + fi.getStorageFilename();
			try {
			    File file = new File(path);
			    if (file.exists()) {
    				input = new BufferedInputStream(new FileInputStream(file));
    				ret = IOUtils.toByteArray(input);
    				input.close();
			    } else {
			        KhandroidLog.e("Cannot find file " + path);
			    }
			} catch (FileNotFoundException e) {
				filesCache.remove(filename);
				KhandroidLog.e("Cannot find file: " + path);
			} catch (IOException e) {
				KhandroidLog.e(e.getMessage());				
			}
		} 
		
		
		return ret;		
	}
	
	
	@Override
	public boolean put(Object key, Object entry) {
		boolean ret = false;
		
		String filename = (String) key;
		byte[] fileData = (byte[]) entry;
		
		if (!filesCache.containsKey(filename)) {
			if (fileData.length <= maxFileSize) {
				if ((size + fileData.length) > maxTotalSize) {
					if (freeEnoughSpace(fileData.length)) {
						actualPut(filename, fileData);
						ret = true;
					}
				} else {
					actualPut(filename, fileData);
					ret = true;
				}
			}
		} else {
		    // Overwriting
		    filesCache.remove(filename);
		    actualPut(filename, fileData);
		}
		
		return ret;
	}
	
	
	private void actualPut(String filename, byte[] fileData) {
	    String cacheFilename = composeCacheFilename(filename);
		String path = cacheDir + "/" + cacheFilename;
		
		try {
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(path)));				
			output.write(fileData);
			output.close();
			size += fileData.length;
			StorageCache.FileInfo tmp = new StorageCache.FileInfo(cacheFilename, fileData.length);
			filesCache.put(filename, tmp);
		} catch (IOException e) {
			KhandroidLog.e(e.getMessage());				
		}		
	}
	
	
	private boolean freeEnoughSpace(long enough) {
		boolean ret = false;
		
		if (enough <= 0) {
			throw new IllegalArgumentException("enough <= 0");
		}
		
		
		FileInfo evicted = evictOldest();
		if (evicted != null) {
			if (evicted.getFileSize() >= enough) {  
				ret = true;
			} else {
				freeEnoughSpace(enough - evicted.getFileSize());
			}
		}
		
		return ret;
	}
	
	
	private FileInfo evictOldest() {
		FileInfo ret;
		
		Map.Entry<String, FileInfo> toEvict = filesCache.entrySet().iterator().next();
		if (toEvict != null) {
			remove(toEvict.getKey());
			fileEvicted(toEvict.getValue());
			ret = toEvict.getValue();
		} else {
			ret = null;
		}
		
		return ret;
	}
	
	
	protected void fileEvicted(FileInfo fi) {
		
	}
	
	
	@Override
	public void clearAll() {
		for (String key : filesCache.keySet()) {
		    remove(key);
		}
	}
	
	
	public static class FileInfo {
		private final String storageFilename;
		private final long fileSize;
		
		public FileInfo(String storageFilename, long fileSize) {
			this.storageFilename = storageFilename;
			this.fileSize = fileSize;
		}

		
		public String getStorageFilename() {
			return storageFilename;
		}


		public long getFileSize() {
			return fileSize;
		}
	}


	@Override
	public void remove(Object key) {
		remove((String) key);
	}
	
	
	public void remove(String filename) {
		if (filesCache.containsKey(filename)) {
			FileInfo fi = filesCache.get(filename);	
			filesCache.remove(filename);
			deleteFile(fi);
		} else {
			throw new IllegalArgumentException("No such file (key): " + filename);
		}		
	}

	
	private void deleteFile(FileInfo fi) {
		
		String path = cacheDir + "/" + fi.getStorageFilename();
		File file = new File(path);
		file.delete();
		
		size -= fi.getFileSize();
	}
	

	@Override
	public boolean containsKey(Object key) {
		return filesCache.containsKey(key);
	}
}
