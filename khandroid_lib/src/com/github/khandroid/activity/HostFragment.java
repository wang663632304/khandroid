package com.github.khandroid.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class HostFragment extends Fragment {
    public static final int EVENT_ON_ATTACH = 0;
    public static final int EVENT_ON_CREATE = 1;
    public static final int EVENT_ON_ACTIVITY_CREATED = 2;
    public static final int EVENT_ON_START = 3;
    public static final int EVENT_ON_RESUME = 4;
    public static final int EVENT_ON_PAUSE = 5;
    public static final int EVENT_ON_STOP = 6;
    public static final int EVENT_ON_DESTROY = 7;
    public static final int EVENT_ON_DETACH = 8;
    public static final int EVENT_ON_SAVE_INSTANCE_STATE = 9;
    
    
    private ArrayList<FragmentAttachable> attachments = new ArrayList<FragmentAttachable>();
    
    
    protected void attach(FragmentAttachable a) {
        if (a instanceof FragmentUniqueAttachedFunctionality) {
            if (isUniqueForMe((FragmentUniqueAttachedFunctionality) a)) {
                attachments.add(a);
            } else {
                throw new IllegalArgumentException("Attempt to attach second FragmentAttachable of type "
                        + a.getClass());
            }
        } else {
            attachments.add(a);
        }
    }

    
    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_ATTACH, null);
        }
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_CREATE, savedInstanceState);
        }
    }
    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_ACTIVITY_CREATED, savedInstanceState);
        }
    }
    
    
    @Override
    public void onStart() {
        super.onStart();

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_START, null);
        }
    }
    
    
    @Override
    public void onResume() {
        super.onResume();

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_RESUME, null);
        }
    }
    
    
    @Override
    public void onPause() {
        super.onPause();

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_PAUSE, null);
        }
    }
    
    
    @Override
    public void onStop() {
        super.onStop();

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_STOP, null);
        }  
    }
    
    
    @Override
    public void onDestroy() {
        super.onDestroy();

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_DESTROY, null);
        }  
    }
    
    
    @Override
    public void onDetach() {
        super.onDetach();

        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_DETACH, null);
        }  
    }
    
    
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (FragmentAttachable a : attachments) {
            a.FragmentLifeCycleEvent(EVENT_ON_SAVE_INSTANCE_STATE, outState);
        }
    }

    
    private boolean isUniqueForMe(FragmentUniqueAttachedFunctionality a) {
        boolean ret = true;

        for (FragmentAttachable a2 : attachments) {
            Class<? extends FragmentUniqueAttachedFunctionality> aClass = a.getClass();
            Class<? extends FragmentAttachable> a2Class = a2.getClass();

            if (aClass.equals(a2Class)) {
                ret = false;
            } else if (aClass.isAssignableFrom(a2Class) || a2Class.isAssignableFrom(aClass)) {
                ret = false;
            }
        }

        return ret;
    }
}

