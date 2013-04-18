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


package com.github.khandroid.activity.functionalities;

import com.github.khandroid.activity.ActivityAttachable;
import com.github.khandroid.activity.ActivityUniqueAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.activity.ActivityAttachable.HostingAble;
import com.github.khandroid.functionality.DbFunctionality;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


abstract public class ActivityDbFunctionality extends ActivityUniqueAttachedFunctionality implements DbFunctionality {
	private SQLiteOpenHelper mDbHelper;	
	private boolean mAutoClose = true;
	
	public ActivityDbFunctionality(HostActivity a) {
		super(a);
	}


	public boolean getAutoClose() {
        return mAutoClose;
    }


    public void setAutoClose(boolean autoClose) {
        this.mAutoClose = autoClose;
    }


    abstract protected SQLiteOpenHelper createDbOpenHelper(Context context);
	
	
	@Override
	protected void onStop() {
		super.onStop();

		if (mAutoClose) {
    		if (mDbHelper != null) {
    			mDbHelper.close();
    		}
		}
	}	
	
	
	public SQLiteDatabase getDbcRW() {
		if (mDbHelper == null) {
			mDbHelper = createDbOpenHelper(getActivity());
		}

		return mDbHelper.getWritableDatabase();
	}


	public SQLiteDatabase getDbcRO() {
		if (mDbHelper == null) {
			mDbHelper = createDbOpenHelper(getActivity());
		}

		return mDbHelper.getReadableDatabase();
	}
	

	public interface HostingAble extends ActivityAttachable.HostingAble {
		SQLiteDatabase getDbcRW();
		SQLiteDatabase getDbcRO();
	}
}
