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


package com.github.khandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.khandroid.misc.SuperNotCalledException;


public class FragmentAttachedFunctionality implements FragmentAttachable {
    private boolean mCalled1;
    private boolean mCalled2;
    private boolean mCalled3;
    private boolean mCalled4;
    private boolean mCalled5;
    private boolean mCalled6;
    private boolean mCalled7;
    private boolean mCalled8;
    private boolean mCalled9;
    private boolean mCalled10;
    
    private HostFragment mFragment;  
    
    
    public FragmentAttachedFunctionality(HostFragment fragment) {
        super();
        this.mFragment = fragment;
    }

    
    @Override
    public void FragmentLifeCycleEvent(int type, Bundle b) {
        switch (type) {
            case HostFragment.EVENT_ON_ATTACH:
                mCalled1 = false;
                onAttach();
                if (!mCalled1) {
                    throw new SuperNotCalledException("Super not called for onCreate()");
                }
                break;            
            case HostFragment.EVENT_ON_CREATE:
                mCalled2 = false;
                onCreate(b);
                if (!mCalled2) {
                    throw new SuperNotCalledException("Super not called for onCreate()");
                }
                break;
            case HostFragment.EVENT_ON_ACTIVITY_CREATED:
                mCalled3 = false;
                onActivityCreated(b);
                if (!mCalled3) {
                    throw new SuperNotCalledException("Super not called for onActivityCreated()");
                } 
                break;
            case HostFragment.EVENT_ON_START:
                mCalled4 = false;
                onStart();
                if (!mCalled4) {
                    throw new SuperNotCalledException("Super not called for onStart()");
                } 
                break;
            case HostFragment.EVENT_ON_RESUME:
                mCalled5 = false;
                onResume();
                if (!mCalled5) {
                    throw new SuperNotCalledException("Super not called for onResume()");
                } 
                break;
            case HostFragment.EVENT_ON_PAUSE:
                mCalled6 = false;
                onPause();
                if (!mCalled6) {
                    throw new SuperNotCalledException("Super not called for onPause()");
                } 
                break;
            case HostFragment.EVENT_ON_STOP:
                mCalled7 = false;
                onStop();
                if (!mCalled7) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                } 
                break;
            case HostFragment.EVENT_ON_DESTROY:
                mCalled8 = false;
                onDestroy();
                if (!mCalled8) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                }    
                break;
            case HostFragment.EVENT_ON_DETACH:
                mCalled9 = false;
                onDetach();
                if (!mCalled9) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                }
                break;
            case HostFragment.EVENT_ON_SAVE_INSTANCE_STATE:
                mCalled10 = false;
                onSaveInstanceState(b);
                if (!mCalled10) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                }
                break;
                
        }
        
        
        
    }
    
    
    public void onAttach() {
        mCalled1 = true;
    }

    
    public void onCreate(Bundle savedInstanceState) {
        mCalled2 = true;
    }
    

    public void onActivityCreated(Bundle savedInstanceState) {
        mCalled3 = true;
    }
    
    
    public void onStart() {
        mCalled4 = true;
    }
    
    
    public void onResume() {
        mCalled5 = true;
    }
    
    
    public void onPause() {
        mCalled6 = true;
    }
    
    
    public void onStop() {
        mCalled7 = true;
    }
    

    public void onDestroy() {
        mCalled8 = true;
    }

    
    public void onDetach () {
        mCalled9 = true;
    }

    
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mCalled10 = true;
    }
    
    
    public Fragment getFragment() {
        return mFragment;
    }
}
