package com.github.khandroid.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


abstract public class FragmentDbFunctionality extends FragmentUniqueAttachedFunctionality implements DbFunctionality {
    private SQLiteOpenHelper dbHelper;  
    private boolean autoClose = true;
    
    public FragmentDbFunctionality(HostFragment fragment) {
        super(fragment);
    }


    public boolean getAutoClose() {
        return autoClose;
    }


    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }


    abstract protected SQLiteOpenHelper createDbOpenHelper(Context context);
    
    
    @Override
    public void onStop() {
        super.onStop();

        if (autoClose) {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
    }   
    
    
    public SQLiteDatabase getDbcRW() {
        if (dbHelper == null) {
            dbHelper = createDbOpenHelper(getFragment().getActivity());
        }

        return dbHelper.getWritableDatabase();
    }


    public SQLiteDatabase getDbcRO() {
        if (dbHelper == null) {
            dbHelper = createDbOpenHelper(getFragment().getActivity());
        }

        return dbHelper.getReadableDatabase();
    }
    

    public interface HostingAble extends FragmentAttachable.HostingAble {
        SQLiteDatabase getDbcRW();
        SQLiteDatabase getDbcRO();
    }
}
