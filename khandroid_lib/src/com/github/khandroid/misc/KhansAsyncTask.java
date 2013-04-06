package com.github.khandroid.misc;

import android.app.Activity;
import android.os.AsyncTask;


abstract public class KhansAsyncTask<Params, Progress, ResultBg> extends AsyncTask<Params, Progress, ResultBg> {
	protected Activity activity;


	public KhansAsyncTask(Activity activity) {
		attach(activity);
	}



	public void detach() {
		activity = null;
	}


	public void attach(Activity activity) {
		this.activity = activity;
	}
	
	
	public static class Result { 
		private final int status;
		private final Object data;
		
		public Result(int status, Object result) {
			super();
			this.status = status;
			this.data = result;
		}

		public int getStatus() {
			return status;
		}

		public Object getData() {
			return data;
		}
	}



	
	
}
