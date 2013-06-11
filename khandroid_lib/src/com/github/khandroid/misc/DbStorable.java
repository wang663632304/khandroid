package com.github.khandroid.misc;

import android.database.sqlite.SQLiteDatabase;

public interface DbStorable {
	long save(SQLiteDatabase dbc);
	boolean loadByID(SQLiteDatabase dbc, long id);
}
