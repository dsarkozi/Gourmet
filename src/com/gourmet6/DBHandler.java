/**
 * 
 */
package com.gourmet6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Lena
 *
 */
public class DBHandler {
	
	protected SQLiteDatabase db;
	protected DBHelper dbHelper;
	
	private boolean read = false;
	private boolean write = false;
	
	public DBHandler(Context context)
	{
		this.dbHelper = new DBHelper(context);
		this.dbHelper.initializeBD();
	}
	
	public void openRead()
	{
		if (!read)
		{
			db = dbHelper.getReadableDatabase();
			
		}
		read = true;
	}
	
	public void openWrite()
	{
		if(!write)
		{
			db = dbHelper.getWritableDatabase();
		}
		write = true; read = true;
	}
	
	public void close()
	{
		db.close();
		read = false; write = false;
	}
}
