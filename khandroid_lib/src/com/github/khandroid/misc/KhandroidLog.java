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
 * Provide convenience methods for using "global" log tag, i.e. one that will be used across the application.
 * You may want to change the log tag from the default "khandroid" by calling initLogTag(); (usually in your
 * *Application.onCreate())
 * Log levels - VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT
 * To change log level use
 * "adb shell setprop log.tag.[LOG_TAG] LEVEL where [LOG_TAG] is your tag set by initLogTag() or the default "khandroid"
 * 
 * @author ogre
 * 
 */
public class KhandroidLog {
    private static String LOG_TAG = "khandroid";
    private static boolean mLoggingEnabled = false;


    public static void initLogTag(String logTag) {
        LOG_TAG = logTag;
    }


    public static void enableLogging() {
        mLoggingEnabled = true;
    }


    public static void disableLogging() {
        mLoggingEnabled = false;
    }


    public static void v(String msg) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.VERBOSE)) {
            Log.v(LOG_TAG, msg);
        }
    }


    public static void v(String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.VERBOSE)) {
            Log.v(LOG_TAG, msg, tr);
        }
    }


    public static void v(String tag, String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, msg, tr);
        }
    }


    public static void d(String msg) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, msg);
        }
    }


    public static void d(String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, msg, tr);
        }
    }


    public static void d(String tag, String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, msg, tr);
        }
    }


    public static void i(String msg) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.INFO)) {
            Log.i(LOG_TAG, msg);
        }
    }


    public static void i(String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.INFO)) {
            Log.i(LOG_TAG, msg, tr);
        }
    }


    public static void i(String tag, String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(tag, Log.INFO)) {
            Log.i(tag, msg, tr);
        }
    }


    public static void w(String msg) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.WARN)) {
            Log.w(LOG_TAG, msg);
        }
    }


    public static void w(String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.WARN)) {
            Log.w(LOG_TAG, msg, tr);
        }
    }


    public static void w(String tag, String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(tag, Log.WARN)) {
            Log.w(tag, msg, tr);
        }
    }


    public static void e(String msg) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.ERROR)) {
            Log.e(LOG_TAG, msg);
        }
    }


    public static void e(String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.ERROR)) {
            Log.e(LOG_TAG, msg, tr);
        }
    }


    public static void e(String tag, String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(tag, Log.ERROR)) {
            Log.e(tag, msg, tr);
        }
    }


    public static void wtf(String msg) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.ASSERT)) {
            Log.wtf(LOG_TAG, msg);
        }
    }


    public static void wtf(String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(LOG_TAG, Log.ASSERT)) {
            Log.wtf(LOG_TAG, msg, tr);
        }
    }


    public static void wtf(String tag, String msg, Throwable tr) {
        if (mLoggingEnabled && Log.isLoggable(tag, Log.ASSERT)) {
            Log.e(tag, msg, tr);
        }
    }
}