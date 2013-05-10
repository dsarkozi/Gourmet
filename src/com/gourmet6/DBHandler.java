package com.gourmet6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * A class which handles the database, i.e. gives methods to 
 * perform requests, add, update and delete data from the DB.
 * 
 * @author Group 6
 * @version 04.05.2013
 */
public class DBHandler {
	
	/**
	 * A helper class to manage database creation and version management. 
	 */
	private static class DBHelper extends SQLiteOpenHelper {

		private static final String DB_NAME = "gourmet12.sqlite"; // as predicted !
		private static final String DB_DIR = "/data/data/com.gourmet6/databases/";
		private static String DB_PATH = DB_DIR + DB_NAME;
		public static final int DB_VERSION = 1;
		private final Context ourContext;
		private boolean createDatabase = false;

		/**
		 * Constructor
		 * @param context of the activity
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
			this.getWritableDatabase();
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
			this.close();
			
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
			
			this.getWritableDatabase().close();
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			this.createDatabase = true;
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			// do nothing
		}
		
		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@Override
		public void onConfigure (SQLiteDatabase db)
		{
			db.setForeignKeyConstraintsEnabled(true);
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
	
	/* 
	 *  Name constants
	 */
	
	/* Tables */
	private static final String TABLE_ALLERGEN = "allergen";
	private static final String TABLE_CLIENT = "client";
	private static final String TABLE_CUISINE = "cuisine";
	private static final String TABLE_DISH = "dish";
	private static final String TABLE_ORDER_DETAIL = "order_detail";
	private static final String TABLE_ORDER_OVERVIEW = "order_overview";
	private static final String TABLE_RESERVATION = "reservation";
	private static final String TABLE_RESTAURANT = "restaurant";
	private static final String TABLE_TIMETABLE = "timetable";
	
	/* Column names */
	private static final String ALLERGEN = "alleName";
	private static final String AVAIL = "avail";
	private static final String CHAIN = "chainName";
	private static final String CLIENT = "cliName";
	private static final String DATETIME = "datetime";
	private static final String DAY_END = "dayEnd";
	private static final String DAY_START = "dayStart";
	private static final String DESCRIPTION = "description";
    private static final String DISH = "dishName";
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String MAIL = "mail";
    private static final String ORDER_NR = "orderNr";
    private static final String PASSWORD = "password";
    private static final String PEOPLE = "people";
    private static final String PRICE = "price";
    private static final String PICTURES = "pictures";
    private static final String PRICE_CAT = "priceCat";
    private static final String QUANTITY = "quantity";
    private static final String RATING = "rating";
    private static final String RES = "resName";
    private static final String INVENTORY = "inventory";
    private static final String SEATS = "seats";
    private static final String STREET = "street";
    private static final String SUBTYPE = "subtype";
    private static final String TEL = "tel";
    private static final String TIME_OPEN = "timeOpen";
    private static final String TIME_CLOSE = "timeClose";
    private static final String TOWN = "town";
    private static final String TYPE = "type";
    private static final String VOTES = "votes";
    private static final String WEB = "web";
    private static final String ZIP = "zip";
  
	protected SQLiteDatabase db;
	protected DBHelper dbHelper;
	
	private boolean read = false;
	private boolean write = false;
	

    /*************************
     * 
     * General access methods.
     * 
     *************************/
    /**
     * Constructor
     * @param context of the activity it is called in
     */
	public DBHandler(Context context)
	{
		this.dbHelper = new DBHelper(context);
		this.dbHelper.initializeBD();
	}
	
	/**
	 * Opens the DB for reading only.
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	private void openRead() throws SQLiteException
	{
		if (!this.read)
		{
			this.db = this.dbHelper.getReadableDatabase();
			
		}
		this.read = true;
	}
	
	/**
	 * Opens the DB for reading and writing.
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	private void openWrite() throws SQLiteException
	{
		if(!this.write)
		{
			this.db = this.dbHelper.getWritableDatabase();
		}
		this.write = true; this.read = true;
	}
	
	/**
	 * Closes the DB.
	 */
	private void close()
	{
		this.dbHelper.close();
		this.read = false; this.write = false;
	}
	
	
	/************
	 * 
	 * Restaurant
	 * 
	 ************/
	
	/**
	 * Returns an ArrayList of the distinct towns known by the DB.
	 * @return a String ArrayList containing the names of all the known towns
	 * in alphabetical order.
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public String[] getTowns() throws SQLiteException
	{
		this.openRead();
		Cursor c;
		String[] towns;
		try
		{
			c = this.db.query(true, TABLE_RESTAURANT, new String[] {TOWN}, null, null, null, null, TOWN, null);
			int count = c.getCount();
			towns = new String[count];
			int i = 0;
			while (c.moveToNext())
			{
				towns[i] = c.getString(0);
				i++;
			}
		}
		finally
		{
			this.close();
		}
		return towns;
	}
	
	/**
	 * Gets the locations of the towns in the DB.
	 * @return an ArrayList of Locations containing a latitude, longitude and town name
	 * @throws SQLiteException
	 */
	public ArrayList<Location> getTownsLocation() throws SQLiteException
	{
		String provider = LocationManager.PASSIVE_PROVIDER;

		String[] towns = this.getTowns();
		ArrayList<Location> locations = new ArrayList<Location>(towns.length);
		this.openRead();
		try
		{
			for (String town : towns)
			{
				Location lo = new Location(provider);
				lo.setLatitude(this.getTownLat(town));
				lo.setLongitude(this.getTownLong(town));
				Bundle bundle = new Bundle(); bundle.putString("town", town);
				lo.setExtras(bundle);

				locations.add(lo);
			}
		}
		finally
		{
			this.close();
		}
		return locations;
	}
	
	
	/**
	 * Returns an ArrayList containing all the distinct town names appearing in the DB table restaurant,
	 * sorted in alphabetically.
	 * @param town a town in which to search restaurants
	 * @return a String ArrayList containing all the town names; if town is null, returns all the towns.
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public ArrayList<String> getAllResNames(String town) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		ArrayList<String> restaurants;
		try
		{
			if (town != null)
			{
				c = this.db.query(TABLE_RESTAURANT, new String[] {RES}, TOWN+"=?", new String[] {town}, null, null, RES);
			}
			else
			{
				c = this.db.query(TABLE_RESTAURANT, new String[] {RES}, null, null, null, null, RES);
			}
			int length = c.getCount();
			restaurants = new ArrayList<String>(length);
			while (c.moveToNext())
			{
				restaurants.add(c.getString(c.getColumnIndex(RES)));
			}
		}
		finally
		{
			this.close();
		}
		return restaurants;
	}
	
	/**
	 * Gets the locations of the restaurants in the DB.
	 * @return an ArrayList of Locations containing a latitude, longitude and restaurant name
	 * @throws SQLiteException
	 */
	public ArrayList<Location> getAllResNamesLocation(String town) throws SQLiteException
	{
		String provider = LocationManager.PASSIVE_PROVIDER;

		ArrayList<String> restaurants = this.getAllResNames(town);
		ArrayList<Location> locations = new ArrayList<Location>(restaurants.size());
		this.openRead();
		try
		{
			for (String res : restaurants)
			{
				Location lo = new Location(provider);
				lo.setLatitude(this.getResLat(res));
				lo.setLongitude(this.getResLong(res));
				Bundle bundle = new Bundle(); bundle.putString("restaurant", res);
				lo.setExtras(bundle);

				locations.add(lo);
			}
		}
		finally
		{
			this.close();
		}
		return locations;
		
	}
	
	/**
	 * Returns a restaurant object based on his name in the DB.
	 * @param name the name of the restaurant
	 * @return the Restaurant corresponding to name, without his dishes
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public Restaurant getRestaurant(String name) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		Restaurant retour;
		try
		{
		// information held by the restaurant table
			c = this.db.query(TABLE_RESTAURANT, new String[]{CHAIN,DESCRIPTION,LAT,LONG,STREET,ZIP,
					TOWN,TEL,MAIL,WEB,RATING,VOTES,SEATS,AVAIL,PICTURES}, RES+"=?", new String[] {name}, null, null, null);
			if (c.getCount() > 1)
			{
				Log.e("DBHandler","Error : two or more retaurants seem to have the same name.");
			}
			c.moveToFirst();
			String chain = c.getString(c.getColumnIndex(CHAIN));
			String description = c.getString(c.getColumnIndex(DESCRIPTION));
			double latitude = c.getDouble(c.getColumnIndex(LAT));
			double longitude = c.getDouble(c.getColumnIndex(LONG));
			String address = c.getString(c.getColumnIndex(STREET));
			int zip = c.getInt(c.getColumnIndex(ZIP));
			String town = c.getString(c.getColumnIndex(TOWN));
			String tel = c.getString(c.getColumnIndex(TEL));
			String mail = c.getString(c.getColumnIndex(MAIL));
			String web = c.getString(c.getColumnIndex(WEB));
			double rating = c.getDouble(c.getColumnIndex(RATING));
			int votes = c.getInt(c.getColumnIndex(VOTES));
			int seats = c.getInt(c.getColumnIndex(SEATS));
			int availableSeats = c.getInt(c.getColumnIndex(AVAIL));
			int pictures = c.getInt(c.getColumnIndex(PICTURES));
			
			// information on the price category
			double priceCat = getResPriceCat(name);
			
			// creates the Restaurant object to be returned
			retour = new Restaurant(name, chain, address, town, tel, web, mail, description, rating, votes,
					zip, seats, availableSeats, latitude, longitude, priceCat, pictures);
			
			// cuisine
			c = this.db.query(TABLE_CUISINE, new String [] {TYPE}, RES+"=?", new String[] {name}, null, null, TYPE);
			ArrayList<String> cuisine = new ArrayList<String>(c.getCount());
			while(c.moveToNext())
			{
				cuisine.add(c.getString(c.getColumnIndex(TYPE)));
			}
			retour.setCuisines(cuisine);
			
			// timetable
			c = this.db.query(TABLE_TIMETABLE, new String[] {DAY_START,DAY_END,TIME_OPEN,TIME_CLOSE}, RES+"=?",
					new String[] {name}, null, null, "CASE "+DAY_START+" WHEN 'lundi' THEN 1 WHEN 'mardi' THEN 12 WHEN 'mercredi' THEN 3 "+
					"WHEN 'jeudi' THEN 4 WHEN 'vendredi' THEN 5 WHEN  'samedi' THEN 6 WHEN 'dimanche' THEN 7 END, "+TIME_OPEN);
			ArrayList<TimeTable> timetable = new ArrayList<TimeTable>(c.getCount());
			while(c.moveToNext())
			{
				String dayStart = c.getString(c.getColumnIndex(DAY_START));
				String dayEnd = c.getString(c.getColumnIndex(DAY_END));
				String timeOpen = c.getString(c.getColumnIndex(TIME_OPEN));
				String timeClose = c.getString(c.getColumnIndex(TIME_CLOSE));
				TimeTable tt = new TimeTable(dayStart, dayEnd, timeOpen, timeClose);
				timetable.add(tt);
			}
			retour.setSemaine(timetable);
		}
		finally
		{
			this.close();
		}
		return retour;
	}
	
	/**
	 * Updates the DB after a new rating.
	 * @param resName the name of the restaurant, must not be null
	 * @param rating the new rating, must be between 0 and 5
	 * @param votes the new number of votes, must be > 0
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments is illegal
	 */
	public long rateRestaurant (String resName, float rating, int votes) throws SQLiteException, SQLException
	{
		// throws an exception if rating is not valid or if resName is null
		if ((rating > 5) || (rating < 0))
		{
			throw new SQLException("Error : wrong rating : "+rating);
		} 
		else if (votes<=0)
		{
			throw new SQLException("Error : wrong number of votes : "+votes);
		}
		else if (resName == null)
		{
			throw new SQLException("Error : no restaurant name given for request");
		}
		
		long nrRows = -1;
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(2);
		insertValues.put(RATING, rating);
		insertValues.put(VOTES, votes);
		try
		{
			this.db.beginTransaction();
			nrRows = this.db.update(TABLE_RESTAURANT, insertValues, RES+"=?", new String[] {resName});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			this.db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}
	
	/**
	 * Updates the number of available seats for a restaurant in the DB.
	 * @param resName the name of the restaurant, must not be null
	 * @param newAvail the new number of available seats, must be >= 0
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments is illegal
	 */
	public long updateAvail (String resName, short newAvail) throws SQLiteException, SQLException
	{
		// throws an exception if newAvail is not valid or if resName is null
		if (newAvail < 0)
		{
			throw new SQLException("Error : wrong rating : "+newAvail);
		} 
		else if (resName == null)
		{
			throw new SQLException("Error : no restaurant name given for request");
		}
		
		long nrRows = -1;
		this.openWrite();

		ContentValues insertValues = new ContentValues(1);
		insertValues.put(AVAIL, newAvail);
		try
		{
			this.db.beginTransaction();
			nrRows = this.db.update(TABLE_RESTAURANT, insertValues, RES+"=?", new String[] {resName});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			this.db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}

	
	/*******
	 * 
	 * Dish
	 * 
	 *******/
	
	/**
	 * Returns an ArrayList of the dishes served in a restaurant, sorted by type (starter, main course, dessert, drinks)
	 * and then alphabetically.
	 * @param resName the name of the restaurant
	 * @return the ArrayList of all the dishes served in the restaurant resName
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public ArrayList<Dish> getDishes(String resName) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		ArrayList<Dish> dishes;
		try
		{
			c = db.query(TABLE_DISH, new String[] {DISH,RES,TYPE,SUBTYPE,DESCRIPTION,INVENTORY,PRICE},
					RES+"=?", new String[] {resName}, null, null, " CASE "+TYPE+" WHEN 'Entr�es' THEN 1 WHEN " +
					"'Plats' THEN 2 WHEN 'Desserts' THEN 3 WHEN 'Boissons' THEN 4 END, "+SUBTYPE);
			
			dishes = new ArrayList<Dish>(c.getCount());
			while (c.moveToNext())
			{
				String dishName = c.getString(c.getColumnIndex(DISH));
				String type = c.getString(c.getColumnIndex(TYPE));
				String subtype = c.getString(c.getColumnIndex(SUBTYPE));
				String description = c.getString(c.getColumnIndex(DESCRIPTION));
				int inventory = c.getInt(c.getColumnIndex(INVENTORY));
				float price = c.getFloat(c.getColumnIndex(PRICE));
				
				// the dish's allergens
				ArrayList<String> allergens = this.searchForAllergens(resName, dishName);
				
				// new dish object
				Dish dish = new Dish(dishName, type, subtype, price, inventory, description, allergens);
				dishes.add(dish);
			}
		}
		finally
		{
			this.close();
		}
		return dishes;
	}
	
	/**
	 * Updates a dish's inventory in the DB.
	 * @param resName the restaurant's name
	 * @param dishName the dish's name
	 * @param newInvent the dish's new inventory
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments is invalid
	 */
	public long setDishInventory(String resName, String dishName, int newInvent) throws SQLiteException, SQLException
	{
		if (resName == null)
		{
			throw new SQLException("No restaurant name given.");
		}
		else if (dishName == null)
		{
			throw new SQLException("No dish name given.");
		}
		if (newInvent < 0)
		{
			throw new SQLException("Attempt to set a negative dish inventory.");
		}
		
		long nrRows = -1;
		this.openWrite();
		
		ContentValues updateValues = new ContentValues(1);
		updateValues.put(INVENTORY, newInvent);
		try
		{
			this.db.beginTransaction();
			nrRows = this.db.update(TABLE_DISH, updateValues, RES+"=? AND "+DISH+"=?", new String[] {resName, dishName});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}
	
	
	/*********
	 * 
	 * Client
	 * 
	 *********/

	/**
	 * Adds a client to the DB.
	 * @param mail, must not be null
	 * @param name, must not be null
	 * @param password, must not be null
	 * @param tel the client's phone, optional
	 * @return  if -1, then error; otherwise the rowId of the newly inserted data
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments id invalid or if the insert fails
	 * @throws SQLiteConstraintException if constraints on the DB are not respected
	 */
	public Client addClient (String mail, String name, String password, String tel) throws SQLiteException, SQLException, SQLiteConstraintException
	{
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(4);
		insertValues.put(MAIL, mail);
		insertValues.put(CLIENT, name);
		insertValues.put(PASSWORD, password);
		insertValues.put(TEL, tel);
		try
		{
			this.db.beginTransaction();
			try
			{
				this.db.insertOrThrow(TABLE_CLIENT, null, insertValues);
				this.db.setTransactionSuccessful();
			}
			finally
			{
				this.db.endTransaction();
			}
		}
		finally
		{
			this.close();
		}
		return new Client(mail, name, tel);
	}
	
	/**
	 * Gets a client based on his mail in the DB.
	 * @param mail the client's mail
	 * @return a Client containing all the information found in the DB
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public Client getClient(String mail) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		Client retour;
		try
		{
			// table client
			c = this.db.query(TABLE_CLIENT, new String[]{CLIENT, TEL}, MAIL+"=?", new String[] {mail}, null, null, null);
			if (c.getCount() > 1)
			{
				Log.e("DBHandler","Error : two or more clients seem to have the same mail.");
			}
			c.moveToFirst();
			String name = c.getString(c.getColumnIndex(CLIENT));
			String tel = c.getString(c.getColumnIndex(TEL));
			retour = new Client(mail, name, tel);
		}
		finally
		{
			this.close();
		}
		return retour;
	}
	
	/**
	 * Checks whether the client has entered the right password.
	 * @param clientMail the client's identifier (mail address)
	 * @param password the password the client has entered
	 * @return true or false, depending on whether the password is the same as the one found in the DB
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public boolean checkPassword (String clientMail, String password) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		try
		{
			c = this.db.query(TABLE_CLIENT, new String[] {PASSWORD}, MAIL+"=?", new String[] {clientMail}, null, null, null);
			int count = c.getCount();
			if (count == 1)
			{
				c.moveToFirst();
				String dbPassword = c.getString(c.getColumnIndex(PASSWORD));
				return (password.equals(dbPassword));
				
			}
			else if (count > 1)
			{
				Log.e("DBHandler", "Error : two or more clients seem to have the same mail.");
			} 
			else if (count == 0)
			{
				// no such client in the DB
			} 
			else 
			{
				Log.e("DBHandler", "Error : the access to the DB must have failed.");
			}
		}
		finally
		{
			this.close();
		}
		return false;
	}
	
	/**
	 * Changes the mail of a client in the DB.
	 * @param oldMail the client's old mail (used to identify him), must not be null
	 * @param newMail the client's new mail, must not be null
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments is invalid
	 * @throws SQLiteConstraintException if constraints on the DB are not respected
	 */
	public long changeMail (String oldMail, String newMail) throws SQLiteException, SQLException, SQLiteConstraintException
	{
		// throws an exception if the mandatory information is not given
		if (oldMail == null)
		{
			throw new SQLException("No old mail given.");
		}
		else if (newMail == null)
		{
			throw new SQLException("No new mail given.");
		}
		
		long nrRows = -1;
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(1);
		insertValues.put(MAIL, newMail);
		try
		{
			this.db.beginTransaction();
			nrRows = this.db.update(TABLE_CLIENT, insertValues, MAIL+"=?", new String[] {oldMail});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}
	
	/**
	 * Changes the name of a client in the DB.
	 * @param mail the mail which identifies the client, must not be null
	 * @param newName the new name the client wants to have in the DB, must not be null
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments is invalid
	 */
	public long changeName (String mail, String newName) throws SQLiteException, SQLException
	{
		// throws an exception if the mandatory information is not given
		if (mail == null)
		{
			throw new SQLException("No mail given");
		}
		else if (newName == null)
		{
			throw new SQLException("No new name given.");
		}
		
		long nrRows = -1;
		this.openWrite();

		ContentValues insertValues = new ContentValues(1);
		insertValues.put(CLIENT, newName);
		try
		{
			this.db.beginTransaction();
			nrRows = this.db.update(TABLE_CLIENT, insertValues, MAIL+"=?", new String[] {mail});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}
	
	/**
	 * Changes a client's password in the DB
	 * @param mail the client's mail
	 * @param newPassword the client's new password
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if one of the arguments is invalid
	 */
	public long changePassword (String mail, String newPassword) throws SQLiteException, SQLException
	{
		// throws an exception if the mandatory information is not given
		if (mail == null)
		{
			throw new SQLiteException("No mail given.");
		}
		else if (newPassword == null)
		{
			throw new SQLException("No password given.");
		}
		
		long nrRows = -1;
		this.openWrite();

		ContentValues insertValues = new ContentValues(1);
		insertValues.put(PASSWORD, newPassword);
		try
		{
			this.db.beginTransaction();
			nrRows = this.db.update(TABLE_CLIENT, insertValues, MAIL+"=?", new String[] {mail});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			this.db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}

	/**
	 * Changes the mail of a client in the DB.
	 * @param mail the client's mail which identifies him, must not be null
	 * @param newTel the phone number the client wants to have in the DB, may be null
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException if the DB cannot be accessed for writing
	 * @throws SQLException if the first argument is illegal
	 */
	public long changeTel (String mail, String newTel) throws SQLiteException, SQLException
	{
		// throws an exception if the mandatory information is not given
		if (mail == null)
		{
			throw new SQLException("No mail given.");
		}
		
		long nrRows = -1;
		this.openWrite();

		ContentValues insertValues = new ContentValues(1);
		insertValues.put(TEL, newTel);
		try
		{
			this.db.beginTransaction();
			nrRows = db.update(TABLE_CLIENT, insertValues, MAIL+"=?", new String[] {mail});
			if (nrRows > 0)
			{
				this.db.setTransactionSuccessful();
			}
			this.db.endTransaction();
		}
		finally
		{
			this.close();
		}
		return nrRows;
	}
	
	
	/*********
	 * 
	 * Orders
	 *
	 *********/
	
	/**
	 * Gets all the orders a client has ever made that are known to the DB.
	 * @param mail the client's mail
	 * @return  an ArrayList containing all orders relative to a client.
	 * All dishes are included (excepted their description and inventory).
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public ArrayList<Order> getClientOrders(String mail) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		ArrayList<Order> orders;
		try
		{
			// information held by the table order_overview
			c = this.db.query(TABLE_ORDER_OVERVIEW, new String[] {"_id"}, MAIL+"=?", new String[] {mail}, null, null, null);
			int count = c.getCount();
			if (count == 0) {
				return null;
			}
	
			orders = new ArrayList<Order>(count);
			while (c.moveToNext())
			{
				int orderNr = c.getInt(c.getColumnIndex("_id"));
				Order order = getOrder(orderNr);
				orders.add(order);
			}
		}
		finally
		{
			this.close();
		}
		return orders;
	}
	
	/**
	 * Inserts an order into the DB.
	 * @param order the order to insert into the DB, must not be null
	 * @return  if -1, then error; otherwise the rowId of the newly inserted data
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 * @throws SQLException if order is null or if insert fails
	 */
	public long addOrder(Order order) throws SQLiteException, SQLException
	{
		// throws an exception if the mandatory information is not given
		if (order == null)
		{
			throw new SQLException("No order provided.");
		}
		
		this.openWrite();
		
		// table order_overview
		long rowIdOverview = -1;
		ContentValues insertOverview = new ContentValues(2);
		insertOverview.put(RES, order.getOrderRestaurant());
		insertOverview.put(MAIL, order.getOrderEmail());
		try
		{
			// starts a new transaction
			this.db.beginTransaction();
			try
			{
				rowIdOverview = this.db.insertOrThrow(TABLE_ORDER_OVERVIEW, null, insertOverview);
				
				// table order_detail
				ContentValues insertDetail = new ContentValues();
				for (Dish d : order.getOrderDishes())
				{
					insertDetail.put(DISH, d.getName());
					insertDetail.put(QUANTITY, d.getQuantity());
					insertDetail.put(RES, order.getOrderRestaurant());
					insertDetail.put(ORDER_NR, rowIdOverview);
					this.db.insertOrThrow(TABLE_ORDER_DETAIL, null, insertDetail);
				}
				this.db.setTransactionSuccessful();
			}
			finally
			{
				this.db.endTransaction();
			}
		}
		finally
		{
			this.close();
		}
		return rowIdOverview;
	}
	
	
	/**************
	 * 
	 * Reservations
	 *
	 **************/
	
	/**
	 * Gets all the reservations a client has ever made that are known to the DB.
	 * @param mail the client's mail
	 * @return an ArrayList containing all the reservations relative to a client.
	 * If a reservation holds an order, it is contained.
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public ArrayList<Reservation> getClientReservations(String mail) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		ArrayList<Reservation> reservations;
		try
		{
			// information held by the client table
			String client = this.getClientName(mail);
			
			// information held by the reservation table
			c = this.db.query(TABLE_RESERVATION, new String[] {RES,ORDER_NR,DATETIME,PEOPLE}, MAIL+"=?",
					new String[] {mail}, null, null, DATETIME);
			int count = c.getCount();
			if (count == 0)
			{
				return null;
			}
			
			reservations = new ArrayList<Reservation>(count);
			while (c.moveToNext())
			{
				String resName = c.getString(c.getColumnIndex(RES));
				String datetime = c.getString(c.getColumnIndex(DATETIME));
				int seats = c.getInt(c.getColumnIndex(PEOPLE));
				
				// new Reservation
				Reservation reserv = new Reservation(resName, datetime, seats, client, mail);
				
				// adds the corresponding order if there is one
				if (!c.isNull(c.getColumnIndex(ORDER_NR)))
				{
					int orderNr = c.getInt(c.getColumnIndex(ORDER_NR));
					Order order = getOrder(orderNr);
					reserv.setReservationOrder(order);
				}
			}
		}
		finally
		{
			this.close();
		}
		return reservations;
	}
	
	/**
	 * Inserts a reservation into the DB.
	 * @param reservation the reservation to insert, must not be null
	 * @param orderNr if > 0, the identifier of the order bound to the reservation, otherwise
	 * indicates that no order has to be bound.
	 * @return if -1, then error; otherwise the rowId of the newly inserted data
	 * @throws SQLiteException if DB cannot be accessed
	 * @throws SQLException if reservation is null or if insert fails
	 */
	public long addReservation(Reservation reservation, long orderNr) throws SQLiteException, SQLException
	{
		// throws an exception if the mandatory information is not given
		if (reservation == null)
		{
			throw new SQLException("Arguments missing!");
		}

		this.openWrite();

		// table reservation
		long rowId = -1;
		ContentValues insertValues = new ContentValues(5);
		insertValues.put(RES, reservation.getReservationResName());
		insertValues.put(DATETIME, TimeTable.parseDateInString(reservation.getReservationTime()));
		insertValues.put(PEOPLE, reservation.getReservationPeople());
		insertValues.put(MAIL, reservation.getReservationEmail());
		if (orderNr > 0) {
			insertValues.put(ORDER_NR, orderNr);
		}
		try
		{
			// starts a new transaction
			this.db.beginTransaction();
			try
			{
				rowId = this.db.insertOrThrow(TABLE_RESERVATION, null, insertValues);
				this.db.setTransactionSuccessful();
			}
			finally
			{
				this.db.endTransaction();
			}
		}
		finally
		{
			this.close();
		}
		return rowId;
	}
	
	/**
	 * Gets the number of seats available in a restaurant on a given day between two given hours,
	 * based on the reservations and the total number of seats in the DB.
	 * @param resName the restaurant's name
	 * @param dtStart a String representing the date and start hour, format must be yyyy-mm-dd hh:mm
	 * @param dtEnd a String representing the date and ending hour, format must be yyyy-mm-dd hh:mm
	 * @return the number of seats available between the dtStart and dtEnd
	 * @throws SQLiteException if the DB cannot be accessed for reading
	 */
	public int getAvailBetweenDateTime(String resName, String dtStart, String dtEnd) throws SQLiteException
	{
		this.openRead();
		Cursor c;
		int seats;
		try
		{
			// tables reservation and restaurant
			//String avail = "stillAvailable";
			c = this.db.rawQuery("SELECT ("+SEATS+" - sum ("+PEOPLE+")) FROM "+TABLE_RESERVATION+" reserv, "+TABLE_RESTAURANT+
					" rest WHERE reserv."+RES+"= rest."+RES+" AND rest."+RES+"=? AND strftime('%Y-%m-%d %H:%M', reserv."+DATETIME+") " +
					"BETWEEN strftime('%Y-%m-%d %H:%M', ?) AND strftime('%Y-%m-%d %H:%M', ?)",
					new String []{resName,dtStart,dtEnd});
			int count = c.getCount();
			if (count != 1)
			{
				Log.e("DBHandler", "Error : the method failed.");
			}
			c.moveToFirst();
			if (c.isNull(0))
			{
				c = this.db.query(TABLE_RESTAURANT, new String[] {SEATS}, RES+"=?", new String[] {resName}, null, null, null);
				seats = c.getInt(0);
			}
			else
			{
				seats = c.getInt(0);
			}
			
		}
		finally
		{
			this.close();
		}
		return seats;
	}
	
	
	/*******************
	 * Internal methods
	 *******************/
	
	/**
	 * @param town the town name
	 * @return the town's latitude (average value of the restaurants in this town)
	 */
	private double getTownLat(String town)
	{
		Cursor c = this.db.rawQuery("SELECT avg("+LAT+") AS newLat FROM "+TABLE_RESTAURANT+" WHERE "+TOWN+"=?", new String[] {town});
		if (c.getCount() > 1)
		{
			Log.e("DBHandler","Error : unknown cause.");
		}
		c.moveToFirst();
		return c.getDouble(0);
	}
	
	/**
	 * @param town the town name
	 * @return the town's latitude (average value of the restaurants in this town)
	 */
	private double getTownLong(String town)
	{
		Cursor c = this.db.rawQuery("SELECT avg("+LONG+") AS newLong FROM "+TABLE_RESTAURANT+" WHERE "+TOWN+"=?", new String[] {town});
		if (c.getCount() > 1)
		{
			Log.e("DBHandler","Error : unknown cause.");
		}
		c.moveToFirst();
		return c.getDouble(0);
	}
	
	/**
	 * @param res the restaurant name
	 * @return the restaurant's latitude
	 */
	private double getResLat(String res)
	{
		Cursor c = this.db.query(TABLE_RESTAURANT, new String[] {LAT}, RES+"=? ", new String [] {res}, null, null, null);
		if (c.getCount() > 1)
		{
			Log.e("DBHandler","Error : unknown cause.");
		}
		c.moveToFirst();
		return c.getDouble(c.getColumnIndex(LAT));
	}
	
	/**
	 * @param res the restaurant name
	 * @return the restaurant's longitude
	 */
	private double getResLong(String res)
	{
		Cursor c = this.db.query(TABLE_RESTAURANT, new String[] {LONG}, RES+"=? ", new String [] {res}, null, null, null);
		if (c.getCount() > 1)
		{
			Log.e("DBHandler","Error : unknown cause.");
		}
		c.moveToFirst();
		return c.getDouble(c.getColumnIndex(LONG));
	}
	
	/**
	 * Gets a restaurant's price category.
	 * @param resName the restaurant's name
	 * @return the price category
	 */
	private double getResPriceCat(String resName) throws SQLiteException
	{
		Cursor c = this.db.rawQuery("SELECT avg(price) as "+PRICE_CAT+" FROM "+TABLE_DISH+" d, "+TABLE_RESTAURANT+
				" r WHERE d."+RES+"=r."+RES+" AND r."+RES+"=?", new String[] {resName});
		if (c.getCount() > 1)
		{
			Log.e("DBHandler","Error : two or more restaurants seem to have the same name.");
		}
		double priceCat = 0;
		while (c.moveToNext())
		{
			 priceCat = c.getDouble(c.getColumnIndex(PRICE_CAT));
		}

		return priceCat;
	}
	
	/**
	 * @param resName the restaurant's name
	 * @param dishName the dish's name
	 * @return an ArrayList of the allergens contained in the dish
	 */
	private ArrayList<String> searchForAllergens(String resName, String dishName) throws SQLiteException
	{
		// information held by the allergen table
		Cursor c = this.db.query(TABLE_ALLERGEN, new String[]{ALLERGEN},
				RES+"=? AND "+DISH+"=?", new String[]{resName,dishName}, null, null, ALLERGEN);
		int count = c.getCount();
		if (count == 0)
		{
			return null;
		}
		
		ArrayList<String> allergens = new ArrayList<String>(count);
		while (c.moveToNext())
		{
			allergens.add(c.getString(c.getColumnIndex(ALLERGEN)));
		}

		return allergens;
	}
	
	/**
	 * @param mail the client's mail
	 * @return the client's name
	 */
	private String getClientName(String mail) throws SQLiteException
	{
		// information held by the client table
		Cursor c = this.db.query(TABLE_CLIENT, new String[] {CLIENT}, MAIL+"=?", new String[] {mail}, null, null, null);
		if (c.getColumnCount() > 1)
		{
			Log.e("DBHandler", "Error : two or more client seem to have the same mail.");
		}
		c.moveToFirst();
		String name = c.getString(c.getColumnIndex(CLIENT));
		
		return name;
	}
	
	/**
	 * @param orderNr the identifier of the order in the DB
	 * @return an object order with all the information from the DB
	 */
	private Order getOrder(int orderNr) throws SQLiteException
	{		
		Cursor c;
		Cursor d;
		
		// information held by the order_overview table
		c = this.db.query(TABLE_ORDER_OVERVIEW, new String[] {RES, MAIL}, "_id=?",
				new String[] {Integer.toString(orderNr)}, null, null, null);
		if (c.getColumnCount() > 1)
		{
			Log.e("DBHandler","Error : two or more orders seem to have the same number.");
		}
		c.moveToFirst();
		String resName = c.getString(c.getColumnIndex(RES));
		String mail = c.getString(c.getColumnIndex(MAIL));
		
		// information held by the client table
		String client = this.getClientName(mail);
		
		// new Order, will be returned
		Order retour = new Order(resName, client, mail);
		
		// information held by the order_detail table
		c = this.db.query(TABLE_ORDER_DETAIL, new String[] {DISH, QUANTITY}, ORDER_NR+"=?", 
				new String[] {Integer.toString(orderNr)}, null, null, null);
		ArrayList<Dish> dishes = new ArrayList<Dish>(c.getCount());
		while (c.moveToNext())
		{
			String dishName = c.getString(c.getColumnIndex(DISH));
			int quantity = c.getInt(c.getColumnIndex(QUANTITY));
			
			// information held by the dish table
			d = this.db.query(TABLE_DISH, new String[] {TYPE, SUBTYPE, PRICE}, DISH+"=? AND "+RES+"=?",
					new String[] {dishName,resName}, null, null, null);
			if (d.getCount() > 1)
			{
				Log.e("DBHandler","Error : two or more dishes seem to have the same name and restaurant.");
			}
			String type = c.getString(c.getColumnIndex(TYPE));
			String subtype = c.getString(c.getColumnIndex(SUBTYPE));
			float price = c.getFloat(c.getColumnIndex(PRICE));
			
			// information held by the allergen table
			ArrayList<String> allergens = this.searchForAllergens(resName, dishName);
				
			Dish dish = new Dish(dishName, type, subtype, price, 0, null, allergens);
			dish.setQuantity(quantity);
			dishes.add(dish);
		}
		retour.setOrderDishes(dishes);

		return retour;
	}
}
