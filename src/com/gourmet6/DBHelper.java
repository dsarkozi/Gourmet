package com.gourmet6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Lena
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_PATH = "/data/data/com.gourmet6/databases/";
	private static final String DB_NAME = "gourmet3_sqlite";
	private static final String DB_FULL_PATH = DB_PATH + DB_NAME;
	public static final int DB_VERSION = 1;
	
	private SQLiteDatabase ourDB;
	private final Context ourContext;

	/**
	 * @param context
	 */
	public DBHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
		this.ourContext = context;
		if(checkDB()) {
			// the DB already exists
			return;
		} else
		{
			this.getReadableDatabase();
			try
			{
				copyDB();
			} catch (IOException e)
			{
				throw new Error("Error copying database at initialisation");
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// do nothing
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// do nothing
	}

	@Override
	public synchronized void close()
	{

		if (ourDB != null)
		{
			ourDB.close();
		}
		super.close();

	}
	
	/*
	 * Creates an empty DB on the system and rewrites it with our own.
	 */
	public void createDB() throws IOException
	{
		if (!checkDB())
		{
			this.getReadableDatabase();
			try
			{
				copyDB();
			}
			catch (IOException e)
			{
				throw new Error("Error copying database");
			}
		}
	}
	
	/*
	 * Checks if a DB has yet been created.
	 * @return true / false
	 */
	private boolean checkDB()
    {
		SQLiteDatabase check = null;
        try
        {
            check = SQLiteDatabase.openDatabase(DB_FULL_PATH, null, SQLiteDatabase.OPEN_READONLY);
 
        } catch(SQLiteException e)
        {
            //database does't exist yet.
        }
        if(check != null)
        {
            check.close();
        }
        return check != null ? true : false;
    }

	/*
	 * Copies the DB from the local assets folder to the just created empty DB in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 */
	private void copyDB() throws IOException
	{
		// Open the local DB as the input stream
		InputStream myInput = ourContext.getAssets().open(DB_NAME);

		// Open the empty DB as the output stream
		OutputStream myOutput = new FileOutputStream(DB_FULL_PATH);

		// Transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0)
		{
			myOutput.write(buffer, 0, length);
		}
		
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
