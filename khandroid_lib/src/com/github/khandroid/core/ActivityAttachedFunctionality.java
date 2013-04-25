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


package com.github.khandroid.core;

import com.github.khandroid.misc.SuperNotCalledException;

import android.app.Activity;
import android.os.Bundle;


public class ActivityAttachedFunctionality implements ActivityAttachable {
	private boolean mCalled1;
	private boolean mCalled2;
	private boolean mCalled3;
	private boolean mCalled4;
	private boolean mCalled5;
	private boolean mCalled6;
	private boolean mCalled7;
	private boolean mCalled8;
	private boolean mCalled9;
	
	private HostActivity mActivity;	
	
	
	public ActivityAttachedFunctionality(HostActivity activity) {
		super();
		this.mActivity = activity;
	}


	public void ActivityLifeCycleEvent(int type, Bundle b) {
		switch (type) {
			case HostActivity.EVENT_ON_CREATE:
				mCalled1 = false;
				onCreate(b);
				if (!mCalled1) {
					throw new SuperNotCalledException("Super not called for onCreate()");
				}
				break;
			case HostActivity.EVENT_ON_DESTROY:
				mCalled2 = false;
				onDestroy();
				if (!mCalled2) {
					throw new SuperNotCalledException("Super not called for onDestroy()");
				}
				break;
			case HostActivity.EVENT_ON_PAUSE:
				mCalled3 = false;
				onPause();
				if (!mCalled3) {
					throw new SuperNotCalledException("Super not called for onPause()");
				}
				break;
			case HostActivity.EVENT_ON_RESTART:
				mCalled4 = false;
				onRestart();
				if (!mCalled4) {
					throw new SuperNotCalledException("Super not called for onRestart()");
				}
				break;
			case HostActivity.EVENT_ON_RESTORE_INSTANCE_STATE:
				mCalled5 = false;
				onRestoreInstanceState(b);
				if (!mCalled5) {
					throw new SuperNotCalledException("Super not called for onRestoreInstanceState()");
				}
				break;
			case HostActivity.EVENT_ON_RESUME:
				mCalled6 = false;
				onResume();
				if (!mCalled6) {
					throw new SuperNotCalledException("Super not called for onResume()");
				}
				break;
			case HostActivity.EVENT_ON_SAVE_INSTANCE_STATE:
				mCalled7 = false;
				onSaveInstanceState(b);
				if (!mCalled7) {
					throw new SuperNotCalledException("Super not called for onSaveInstanceState()");
				}
				break;
			case HostActivity.EVENT_ON_START:
				mCalled8 = false;
				onStart();
				if (!mCalled8) {
					throw new SuperNotCalledException("Super not called for onStart()");
				}
				break;
			case HostActivity.EVENT_ON_STOP:
				mCalled9 = false;
				onStop();
				if (!mCalled9) {
					throw new SuperNotCalledException("Super not called for onStop()");
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid value for type: " + type);
		}
		
		
	}
	
	
	/**
	 * It is public in order to be called directly by the user because it is impossible to be called by the 
	 * activity (because user gets first chance to execute code in onCreate(), i.e. to attach the functionality. All in all 
	 * that means that you need to call onCreate on the attached functionality manually if you has code in there.
	 * @param savedInstanceState
	 */
	public void onCreate(Bundle savedInstanceState) {
		mCalled1 = true;
	}

	
	protected void onDestroy() {
		mCalled2 = true;		
	}


	protected void onPause() {
		mCalled3 = true;		
	}


	protected void onRestart() {
		mCalled4 = true;		
	}


	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		mCalled5 = true;		
	}


	protected void onResume() {
		mCalled6 = true;		
	}


	protected void onSaveInstanceState(Bundle outState) {
		mCalled7 = true;		
	}


	protected void onStart() {
		mCalled8 = true;		
	}


	protected void onStop() {
		mCalled9 = true;		
	}
	
	
	public Activity getActivity() {
		return mActivity;
	}	
}
