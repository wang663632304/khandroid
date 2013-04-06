package com.github.khandroid.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


abstract public class ActivityDbFunctionality extends ActivityUniqueAttachedFunctionality implements DbFunctionality {
	private SQLiteOpenHelper dbHelper;	
	private boolean autoClose = true;
	
	public ActivityDbFunctionality(HostActivity a) {
		super(a);
	}


	public boolean getAutoClose() {
        return autoClose;
    }


    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }


    abstract protected SQLiteOpenHelper createDbOpenHelper(Context context);
	
	
	@Override
	protected void onStop() {
		super.onStop();

		if (autoClose) {
    		if (dbHelper != null) {
    			dbHelper.close();
    		}
		}
	}	
	
	
	public SQLiteDatabase getDbcRW() {
		if (dbHelper == null) {
			dbHelper = createDbOpenHelper(getActivity());
		}

		return dbHelper.getWritableDatabase();
	}


	public SQLiteDatabase getDbcRO() {
		if (dbHelper == null) {
			dbHelper = createDbOpenHelper(getActivity());
		}

		return dbHelper.getReadableDatabase();
	}
	

	public interface HostingAble extends ActivityAttachable.HostingAble {
		SQLiteDatabase getDbcRW();
		SQLiteDatabase getDbcRO();
	}
}
