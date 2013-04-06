package com.github.khandroid.activity;

import android.os.Bundle;


public interface ActivityAttachable {
	void ActivityLifeCycleEvent(int type, Bundle b);

	public interface HostingAble {
		
	}
}
