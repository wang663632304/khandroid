package com.github.khandroid.misc;

import android.database.sqlite.SQLiteDatabase;

public interface DbStorable {
	long save(SQLiteDatabase dbc);
	DbStorable loadByID(SQLiteDatabase dbc, long id);
}
