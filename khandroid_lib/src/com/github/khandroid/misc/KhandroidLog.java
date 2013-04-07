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


import android.util.Log;

/**
 * Wrapper around android.util.Log
 * 
 * You may want to change the log tag from the default "khandroid" by calling initLogTag(); (usually in your *Application.onCreate())
 * Log levels - VERBOSE, DEBUG, INFO, WARN, ERROR
 * To change log level use "adb shell setprop log.tag.[LOG_TAG] LEVEL where [LOG_TAG] is your tag set by initLogTag() or the default "khandroid"
 * 
 * @author ogre
 *
 */
public class KhandroidLog {
	private static final String PREFIX = "### khandroid: ";
	private static String LOG_TAG = "khandroid";
	private static boolean khandroidLibLoggingEnabled = false; 
	
	public static void initLogTag(String logTag) {
		LOG_TAG = logTag;
	}
	
	
	public static void setKhandroidLibLoggingEnabled(boolean state) {
		khandroidLibLoggingEnabled = state;
	}
	
	
	public static void v(String msg) {
		if (Log.isLoggable(LOG_TAG, Log.VERBOSE)) {
			_v(LOG_TAG, msg);
		}
	}


	public static void v(String msg, Throwable tr) {
		if (Log.isLoggable(LOG_TAG, Log.VERBOSE)) {
			_v(LOG_TAG, msg, tr);
		}
	}
	

	public static void d(String msg) {
		if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
			_d(LOG_TAG, msg);
		}
	}

	
	public static void d(String msg, Throwable tr) {
		if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
			_d(LOG_TAG, msg, tr);
		}
	}	


	public static void i(String msg) {
		if (Log.isLoggable(LOG_TAG, Log.INFO)) {
			_i(LOG_TAG, msg);
		}
	}


	public static void i(String msg, Throwable tr) {
		if (Log.isLoggable(LOG_TAG, Log.INFO)) {
			_i(LOG_TAG, msg, tr);
		}
	}
	

	public static void w(String msg) {
		if (Log.isLoggable(LOG_TAG, Log.WARN)) {
			_w(LOG_TAG, msg);
		}
	}


	public static void w(String msg, Throwable tr) {
		if (Log.isLoggable(LOG_TAG, Log.WARN)) {
			_w(LOG_TAG, msg, tr);
		}
	}
	

	public static void e(String msg) {
		if (Log.isLoggable(LOG_TAG, Log.ERROR)) {
			_e(LOG_TAG, msg);
		}
	}


	public static void e(String msg, Throwable tr) {
		if (Log.isLoggable(LOG_TAG, Log.ERROR)) {
			_e(LOG_TAG, msg, tr);
		}
	}

	
	public static void tv(String msg) {
		if (khandroidLibLoggingEnabled) {
			v(PREFIX + msg);
		}
	}


	public static void tv(String msg, Throwable tr) {
		if (khandroidLibLoggingEnabled) {
			v(PREFIX + msg, tr);
		}
	}
	

	public static void td(String msg) {
		if (khandroidLibLoggingEnabled) {
			d(PREFIX + msg);
		}
	}

	
	public static void td(String msg, Throwable tr) {
		if (khandroidLibLoggingEnabled) {
			d(PREFIX + msg, tr);
		}
	}	


	public static void ti(String msg) {
		if (khandroidLibLoggingEnabled) {
			i(PREFIX + msg);
		}
	}


	public static void ti(String msg, Throwable tr) {
		if (khandroidLibLoggingEnabled) {
			i(PREFIX + msg, tr);
		}
	}
	

	public static void tw(String msg) {
		if (khandroidLibLoggingEnabled) {
			w(PREFIX + msg);
		}
	}


	public static void tw(String msg, Throwable tr) {
		if (khandroidLibLoggingEnabled) {
			w(PREFIX + msg, tr);
		}

	}
	

	public static void te(String msg) {
		if (khandroidLibLoggingEnabled) {
			e(PREFIX + msg);
		}
	}


	public static void te(String msg, Throwable tr) {
		if (khandroidLibLoggingEnabled) {
			e(PREFIX + msg, tr);
		}
	}
	

	private static void _v(String tag, String msg) {
		Log.v(tag, msg);
	}


	private static void _d(String tag, String msg) {
		Log.d(tag, msg);
	}


	private static void _i(String tag, String msg) {
		Log.i(tag, msg);
	}


	private static void _w(String tag, String msg) {
		Log.w(tag, msg);
	}


	private static void _e(String tag, String msg) {
		Log.e(tag, msg);
	}
	

	private static void _v(String tag, String msg, Throwable tr) {
		Log.v(tag, msg, tr);
	}


	private static void _d(String tag, String msg, Throwable tr) {
		Log.d(tag, msg, tr);
	}


	private static void _i(String tag, String msg, Throwable tr) {
		Log.i(tag, msg, tr);
	}


	private static void _w(String tag, String msg, Throwable tr) {
		Log.w(tag, msg, tr);
	}


	private static void _e(String tag, String msg, Throwable tr) {
		Log.e(tag, msg, tr);
	}	
}