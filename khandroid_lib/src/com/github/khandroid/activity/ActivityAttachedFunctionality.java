package com.github.khandroid.activity;

import com.github.khandroid.misc.SuperNotCalledException;

import android.app.Activity;
import android.os.Bundle;


public class ActivityAttachedFunctionality implements ActivityAttachable {
	private boolean called1;
	private boolean called2;
	private boolean called3;
	private boolean called4;
	private boolean called5;
	private boolean called6;
	private boolean called7;
	private boolean called8;
	private boolean called9;
	
	private HostActivity activity;	
	
	
	public ActivityAttachedFunctionality(HostActivity activity) {
		super();
		this.activity = activity;
	}


	public void ActivityLifeCycleEvent(int type, Bundle b) {
		switch (type) {
			case HostActivity.EVENT_ON_CREATE:
				called1 = false;
				onCreate(b);
				if (!called1) {
					throw new SuperNotCalledException("Super not called for onCreate()");
				}
				break;
			case HostActivity.EVENT_ON_DESTROY:
				called2 = false;
				onDestroy();
				if (!called2) {
					throw new SuperNotCalledException("Super not called for onDestroy()");
				}
				break;
			case HostActivity.EVENT_ON_PAUSE:
				called3 = false;
				onPause();
				if (!called3) {
					throw new SuperNotCalledException("Super not called for onPause()");
				}
				break;
			case HostActivity.EVENT_ON_RESTART:
				called4 = false;
				onRestart();
				if (!called4) {
					throw new SuperNotCalledException("Super not called for onRestart()");
				}
				break;
			case HostActivity.EVENT_ON_RESTORE_INSTANCE_STATE:
				called5 = false;
				onRestoreInstanceState(b);
				if (!called5) {
					throw new SuperNotCalledException("Super not called for onRestoreInstanceState()");
				}
				break;
			case HostActivity.EVENT_ON_RESUME:
				called6 = false;
				onResume();
				if (!called6) {
					throw new SuperNotCalledException("Super not called for onResume()");
				}
				break;
			case HostActivity.EVENT_ON_SAVE_INSTANCE_STATE:
				called7 = false;
				onSaveInstanceState(b);
				if (!called7) {
					throw new SuperNotCalledException("Super not called for onSaveInstanceState()");
				}
				break;
			case HostActivity.EVENT_ON_START:
				called8 = false;
				onStart();
				if (!called8) {
					throw new SuperNotCalledException("Super not called for onStart()");
				}
				break;
			case HostActivity.EVENT_ON_STOP:
				called9 = false;
				onStop();
				if (!called9) {
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
		called1 = true;
	}

	
	protected void onDestroy() {
		called2 = true;		
	}


	protected void onPause() {
		called3 = true;		
	}


	protected void onRestart() {
		called4 = true;		
	}


	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		called5 = true;		
	}


	protected void onResume() {
		called6 = true;		
	}


	protected void onSaveInstanceState(Bundle outState) {
		called7 = true;		
	}


	protected void onStart() {
		called8 = true;		
	}


	protected void onStop() {
		called9 = true;		
	}
	
	
	public Activity getActivity() {
		return activity;
	}	
}
