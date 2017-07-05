package com.app.quarter_revolution_tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "QRVote";
	private static final int DATABASE_VERSION = 6;
	private static String DATABASE_TABLE = "titles3";
	public MyDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {			
		//�s�W��ƪ�
		//��ƪ�W�ٻP���W�١A���n�Τ���W��
		//db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
		//           + "name text no null, price real no null, num real no null, d2 text no null)");
		
		//1+1+8+8 = 19
		//1 db name
		//1 left tickets
		//8 candidates
		//8 date
		//db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
		//           + "dbname text no null, tvote real no null, c1name text no null, c1vote real no null, c2name text no null, c2vote real no null, c3name text no null, c3vote real no null, c4name text no null, c4vote real no null, d1all text no null, d1y real no null, d1m real no null, d1d real no null, d2all text no null, d2y real no null, d2m real no null, d2d real no null, rv1 text no null, rv2 text no null, rv3 text no null, rv4 text no null, rv5 text no null, rv6 text no null, rv7 text no null, rv8 text no null, rv9 text no null, rv10 text no null, rv11 text no null, rv12 text no null, rv13 text no null, rv14 text no null, rv15 text no null, rv16 text no null)");
		db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
		           + "dbname text no null, tvote text no null, c1name text no null, c1vote text no null, c2name text no null, c2vote text no null, c3name text no null, c3vote text no null, c4name text no null, c4vote text no null, d1all text no null, d1y real no null, d1m real no null, d1d real no null, d2all text no null, d2y real no null, d2m real no null, d2d real no null, rv1 text no null, rv2 text no null, rv3 text no null, rv4 text no null, rv5 text no null, rv6 text no null, rv7 text no null, rv8 text no null, rv9 text no null, rv10 text no null, rv11 text no null, rv12 text no null, rv13 text no null, rv14 text no null, rv15 text no null, rv16 text no null)");
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE );
        onCreate(db);
	}
	
	//protected void onStop() {
	//	super.onStop();
	//	db.close(); // ������Ʈw
    //}
	
} //end of MyDBHelper extends SQLiteOpenHelper ()
