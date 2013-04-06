package com.github.khandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.khandroid.misc.SuperNotCalledException;


public class FragmentAttachedFunctionality implements FragmentAttachable {
    private boolean called1;
    private boolean called2;
    private boolean called3;
    private boolean called4;
    private boolean called5;
    private boolean called6;
    private boolean called7;
    private boolean called8;
    private boolean called9;
    private boolean called10;
    
    private HostFragment mFragment;  
    
    
    public FragmentAttachedFunctionality(HostFragment fragment) {
        super();
        this.mFragment = fragment;
    }

    
    @Override
    public void FragmentLifeCycleEvent(int type, Bundle b) {
        switch (type) {
            case HostFragment.EVENT_ON_ATTACH:
                called1 = false;
                onAttach();
                if (!called1) {
                    throw new SuperNotCalledException("Super not called for onCreate()");
                }
                break;            
            case HostFragment.EVENT_ON_CREATE:
                called2 = false;
                onCreate(b);
                if (!called2) {
                    throw new SuperNotCalledException("Super not called for onCreate()");
                }
                break;
            case HostFragment.EVENT_ON_ACTIVITY_CREATED:
                called3 = false;
                onActivityCreated(b);
                if (!called3) {
                    throw new SuperNotCalledException("Super not called for onActivityCreated()");
                } 
                break;
            case HostFragment.EVENT_ON_START:
                called4 = false;
                onStart();
                if (!called4) {
                    throw new SuperNotCalledException("Super not called for onStart()");
                } 
                break;
            case HostFragment.EVENT_ON_RESUME:
                called5 = false;
                onResume();
                if (!called5) {
                    throw new SuperNotCalledException("Super not called for onResume()");
                } 
                break;
            case HostFragment.EVENT_ON_PAUSE:
                called6 = false;
                onPause();
                if (!called6) {
                    throw new SuperNotCalledException("Super not called for onPause()");
                } 
                break;
            case HostFragment.EVENT_ON_STOP:
                called7 = false;
                onStop();
                if (!called7) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                } 
                break;
            case HostFragment.EVENT_ON_DESTROY:
                called8 = false;
                onDestroy();
                if (!called8) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                }    
                break;
            case HostFragment.EVENT_ON_DETACH:
                called9 = false;
                onDetach();
                if (!called9) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                }
                break;
            case HostFragment.EVENT_ON_SAVE_INSTANCE_STATE:
                called10 = false;
                onSaveInstanceState(b);
                if (!called10) {
                    throw new SuperNotCalledException("Super not called for onStop()");
                }
                break;
                
        }
        
        
        
    }
    
    
    public void onAttach() {
        called1 = true;
    }

    
    public void onCreate(Bundle savedInstanceState) {
        called2 = true;
    }
    

    public void onActivityCreated(Bundle savedInstanceState) {
        called3 = true;
    }
    
    
    public void onStart() {
        called4 = true;
    }
    
    
    public void onResume() {
        called5 = true;
    }
    
    
    public void onPause() {
        called6 = true;
    }
    
    
    public void onStop() {
        called7 = true;
    }
    

    public void onDestroy() {
        called8 = true;
    }

    
    public void onDetach () {
        called9 = true;
    }

    
    public void onSaveInstanceState(Bundle savedInstanceState) {
        called10 = true;
    }
    
    
    public Fragment getFragment() {
        return mFragment;
    }
}
