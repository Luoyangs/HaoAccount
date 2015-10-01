package com.haoxue.haoaccount.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 说明:通用数据库
 * 作者:Luoyangs
 * 时间:2015-10-1
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	private String tabcreateString;
	
	public SQLiteHelper(Context context, String tableName, String tabcreateString){
		super(context, tableName, null, 1);
		this.tabcreateString = tabcreateString;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(tabcreateString);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//update
	}

}
