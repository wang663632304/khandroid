package com.github.khandroid.db;

import android.database.sqlite.SQLiteDatabase;

public interface IDBStorable {
	long save(SQLiteDatabase dbc);
	boolean loadByID(SQLiteDatabase dbc, long id);
}
