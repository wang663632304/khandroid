package com.github.khandroid.activity;

import android.database.sqlite.SQLiteDatabase;


public interface DbFunctionality {
    public boolean getAutoClose();
    public void setAutoClose(boolean autoClose);
    public SQLiteDatabase getDbcRW();
    public SQLiteDatabase getDbcRO();
}
