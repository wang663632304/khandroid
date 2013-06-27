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


package com.github.khandroid.misc;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
	
	
    public static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            KhandroidLog.e(e.getMessage());
            return null;
        }

        return bitmap;
    }
}
