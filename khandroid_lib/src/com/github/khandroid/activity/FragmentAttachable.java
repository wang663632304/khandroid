package com.github.khandroid.activity;

import android.os.Bundle;


public interface FragmentAttachable {
    void FragmentLifeCycleEvent(int type, Bundle b);

    public interface HostingAble {
        
    }
}
