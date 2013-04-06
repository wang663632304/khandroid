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

