/**
 * 
 */
package com.gourmet6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * A class which handles the database, i.e. gives methods to 
 * perform requests, add, update and delete data from the DB.
 * 
 * @author Group 6
 * @version 04.05.2013
 *
 */
public class DBHandler {
	
	protected SQLiteDatabase db;
	protected DBHelper dbHelper;
	
	private boolean read = false;
	private boolean write = false;
	
	/* Name constants */
	public static final String TABLE_RESTAURANT = "restaurant";
    public static final String RES = "resName";
    public static final String CHAIN = "chainName";
    public static final String DESCRIPTION = "description";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String STREET = "street";
    public static final String ZIP = "zip";
    public static final String TOWN = "town";
    public static final String TEL = "tel";
    public static final String RATING = "rating";
    public static final String PRICE_CAT = "priceCat";
    public static final String AVAIL = "avail";
	
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
