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

import java.util.ArrayList;

import com.github.khandroid.core.ActivityAttachable.HostingAble;


import android.app.Activity;
import android.os.Bundle;


abstract public class HostActivity extends Activity implements ActivityAttachable.HostingAble {
    public static final int EVENT_ON_CREATE = 0;
    public static final int EVENT_ON_DESTROY = 1;
    public static final int EVENT_ON_PAUSE = 2;
    public static final int EVENT_ON_RESTART = 3;
    public static final int EVENT_ON_RESTORE_INSTANCE_STATE = 4;
    public static final int EVENT_ON_RESUME = 5;
    public static final int EVENT_ON_SAVE_INSTANCE_STATE = 6;
    public static final int EVENT_ON_START = 7;
    public static final int EVENT_ON_STOP = 8;

    private ArrayList<ActivityAttachable> mAttachments = new ArrayList<ActivityAttachable>();


    protected void attach(ActivityAttachable a) {
        if (a instanceof ActivityUniqueAttachedFunctionality) {
            if (isUniqueForMe((ActivityUniqueAttachedFunctionality) a)) {
                mAttachments.add(a);
            } else {
                throw new IllegalArgumentException("Attempt to attach second ActivityAttachable of type "
                        + a.getClass());
            }
        } else {
            mAttachments.add(a);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_CREATE, savedInstanceState);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_DESTROY, null);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_PAUSE, null);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_RESTART, null);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_RESTORE_INSTANCE_STATE, savedInstanceState);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_RESUME, null);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_SAVE_INSTANCE_STATE, outState);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_START, null);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        for (ActivityAttachable a : mAttachments) {
            a.ActivityLifeCycleEvent(EVENT_ON_STOP, null);
        }
    }


    private boolean isUniqueForMe(ActivityUniqueAttachedFunctionality a) {
        boolean ret = true;

        for (ActivityAttachable a2 : mAttachments) {
            Class<? extends ActivityUniqueAttachedFunctionality> aClass = a.getClass();
            Class<? extends ActivityAttachable> a2Class = a2.getClass();

            if (aClass.equals(a2Class)) {
                ret = false;
            } else if (aClass.isAssignableFrom(a2Class) || a2Class.isAssignableFrom(aClass)) {
                ret = false;
            }
        }

        return ret;
    }
}
