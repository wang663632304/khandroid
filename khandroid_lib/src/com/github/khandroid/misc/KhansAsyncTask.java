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
