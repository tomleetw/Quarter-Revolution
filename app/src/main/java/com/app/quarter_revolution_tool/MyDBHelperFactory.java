package com.app.quarter_revolution_tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelperFactory {
	static MyDBHelper _MyDBHelper;
	public static MyDBHelper GetDBHelper(Context context)
	{
		if(_MyDBHelper==null)
		{
			_MyDBHelper= new MyDBHelper(context);
		}
		return _MyDBHelper;
	}
}
