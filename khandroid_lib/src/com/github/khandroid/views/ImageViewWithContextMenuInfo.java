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


package com.github.khandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;

public class ImageViewWithContextMenuInfo extends ImageView  {
	public ImageViewWithContextMenuInfo(Context context) {
		super(context);
	}


	@Override
	protected ContextMenuInfo getContextMenuInfo() {
		return new CommonViewContextMenuInfo(this);
	}


	public ImageViewWithContextMenuInfo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public ImageViewWithContextMenuInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	public static class CommonViewContextMenuInfo implements ContextMenu.ContextMenuInfo {
		public CommonViewContextMenuInfo(View targetView) {
			this.targetView = (ImageView) targetView;
		}

		public ImageView targetView;
	}	
}

