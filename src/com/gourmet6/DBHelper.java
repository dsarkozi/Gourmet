package com.gourmet6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper class to manage database creation and version management. 
 * 
 * @author Group 6
 * @version 04.05.2013
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "gourmet4.sqlite";
	private static String DB_DIR = "/data/data/com.gourmet6/databases/";
	private static String DB_PATH = DB_DIR + DB_NAME;
	public static final int DB_VERSION = 1;
	
	private final Context ourContext;
	
	private boolean createDatabase = false;

	/**
	 * Constructor
	 * @param context
	 */
	public DBHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
		this.ourContext = context;
		DB_PATH = ourContext.getDatabasePath(DB_NAME).getAbsolutePath();
	}
	
	/**
	 * Initializes the DB if it does not exist.
	 */
	public void initializeBD()
	{
		getWritableDatabase();
		if (createDatabase)
		{
			try
			{
				copyDB();
			} catch (IOException e)
			{
				throw new Error("Error copying the DB on initialization");
			}
		}
	}
	
	/**
	 * Loads the DB by copying it from the assets folder.
	 */
	public void copyDB() throws IOException
	{
		close();
		
		InputStream input = ourContext.getAssets().open(DB_NAME);
		OutputStream output = new FileOutputStream(DB_PATH);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0)
		{
			output.write(buffer, 0, length);
		}
		output.flush();
		output.close();
		input.close();
		
		getWritableDatabase().close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createDatabase = true;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// do nothing
	}
	
	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
	}
	
	@Override
	public synchronized void close() 
	{
		super.close();
	}
}
