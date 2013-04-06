package com.github.khandroid.misc;

import android.graphics.Bitmap;

public class ImageUtils {
	public static Bitmap  BitmapResizeToMaxXorY(Bitmap srcBitmap, int maxSizeX, int maxSizeY) {
		Bitmap ret = null;
		
		if (srcBitmap != null && maxSizeX > 0 && maxSizeY > 0) {
			Float origSizeX  = new Float(srcBitmap.getWidth());
			Float origSizeY = new Float(srcBitmap.getHeight());
			
			if (origSizeX > maxSizeX || origSizeY > maxSizeY) {
				float origRatio =  origSizeX / origSizeY;
				float destRatio = maxSizeX / maxSizeY;

				float targetSizeX;
				float targetSizeY;
				
				if (origRatio >= destRatio) {
					if (maxSizeX >= origSizeX) {
						targetSizeX = origSizeX;
					} else {
						targetSizeX = maxSizeX;
					}

					targetSizeY = targetSizeX / origRatio;
				} else {
					if (maxSizeY >= origSizeY) {
						targetSizeY = origSizeY;
					} else {
						targetSizeY = maxSizeY;
					}

					targetSizeX = targetSizeY * origRatio;
				}

				ret = Bitmap.createScaledBitmap(srcBitmap, (int) targetSizeX, (int) targetSizeY, false);
			} else {
				// original image is smaller than destination sizes so we are returning original
				ret = srcBitmap;
			}			
		}
		
		return ret;
	}
}
