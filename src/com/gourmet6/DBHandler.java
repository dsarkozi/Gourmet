package com.gourmet6;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * A class which handles the database, i.e. gives methods to 
 * perform requests, add, update and delete data from the DB.
 * 
 * @author Group 6
 * @version 04.05.2013
 */
public class DBHandler {
	
	protected SQLiteDatabase db;
	protected DBHelper dbHelper;
	
	private boolean read = false;
	private boolean write = false;
	
	/* 
	 *  Name constants
	 */
	
	/* Tables */
	public static final String TABLE_ALLERGEN = "allergen";
	public static final String TABLE_CLIENT = "client";
	public static final String TABLE_CUISINE = "cuisine";
	public static final String TABLE_DISH = "dish";
	public static final String TABLE_ORDER_DETAIL = "order_detail";
	public static final String TABLE_ORDER_OVERVIEW = "order_overview";
	public static final String TABLE_RESERVATION = "reservation";
	public static final String TABLE_RESTAURANT = "restaurant";
	public static final String TABLE_TIMETABLE = "timetable";
	
	/* Column names */
    public static final String ALLERGEN = "alleName";
    public static final String AVAIL = "avail";
	public static final String CHAIN = "chainName";
    public static final String CLIENT = "cliName";
    public static final String DATETIME = "datetime";
    public static final String DAY = "day";
    public static final String DESCRIPTION = "description";
    public static final String DISH = "dishName";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String MAIL = "mail";
    public static final String ORDER_NR = "orderNr";
    public static final String PRICE = "price";
    public static final String PRICE_CAT = "priceCat";
    public static final String PASSWORD = "password";
    public static final String QUANTITY = "quantity";
    public static final String RATING = "rating";
    public static final String RES = "resName";
    public static final String RESERV_NR = "reservNr";
    public static final String INVENTORY = "inventory";
    public static final String SEATS = "seats";
    public static final String STREET = "street";
    public static final String SUBTYPE = "subtype";
    public static final String TEL = "tel";
    public static final String TIME_OPEN = "timeOpen";
    public static final String TIME_CLOSE = "timeClose";
    public static final String TOWN = "town";
    public static final String TYPE = "type";
    public static final String VOTES = "votes";
    public static final String ZIP = "zip";
    
    // helps managing the timetables given a certain day
    static public HashMap<String,Integer> weekMap = new HashMap<String,Integer>();
    static {
    	weekMap.put("dimanche", 0);
    	weekMap.put("lundi", 1);
    	weekMap.put("mardi", 2);
    	weekMap.put("mercredi", 3);
    	weekMap.put("jeudi", 4);
    	weekMap.put("vendredi", 5);
    	weekMap.put("samedi", 6);
    }

    
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
	 */
	public void openRead()
	{
		if (!read)
		{
			db = dbHelper.getReadableDatabase();
			
		}
		read = true;
	}
	
	/**
	 * Opens the DB for reading and writing.
	 */
	public void openWrite()
	{
		if(!write)
		{
			db = dbHelper.getWritableDatabase();
		}
		write = true; read = true;
	}
	
	/**
	 * Closes the DB.
	 */
	public void close()
	{
		db.close();
		read = false; write = false;
	}
	
	
	/************
	 * 
	 * Restaurant
	 * 
	 ************/
	
	/**
	 * Returns an ArrayList of the distinct towns known by the DB.
	 * @return a String ArrayList containing the names of all the known towns
	 */
	public ArrayList<String> getTowns()
	{
		this.openRead();
		Cursor c;
		
		c = db.query(true, TABLE_RESTAURANT, new String[] {TOWN}, null, null, null, null, TOWN, null);
		int count = c.getCount();
		ArrayList<String> towns = new ArrayList<String>(count);
		for (int i=0; i<count; i++)
		{
			towns.add(c.getString(i));
		}
		
		this.close();
		return towns;
	}
	
	/**
	 * Returns an ArrayList containing all the distinct town names appearing in the DB table restaurant,
	 * sorted in alphabetically.
	 * @param town a town in which to search restaurants
	 * @return an String ArrayList containing all the town names; if town is null, returns all the towns.
	 */
	public ArrayList<String> getAllResNames(String town)
	{
		this.openRead();
		Cursor c;
		
		if (town != null)
		{
			c = db.query(TABLE_RESTAURANT, new String[] {RES}, TOWN+"='"+town+"'", null, null, null, RES);
		} else {
			c = db.query(TABLE_RESTAURANT, new String[] {RES}, null, null, null, null, RES);
		}
		int length = c.getCount();
		ArrayList<String> retour = new ArrayList<String>(length);
		for (int i=0; i<length; i++)
		{
			c.moveToPosition(i);
			retour.add(c.getString(i));
		}
		
		this.close();
		return retour;
	}
	
	/**
	 * Returns a restaurant object based on his name in the DB.
	 * @param name
	 * @return the Restaurant corresponding to name, without his dishes
	 */
	public Restaurant getRestaurant(String name)
	{
		this.openRead();
		Cursor c;
		
		// information held by the restaurant table
		c = db.query(TABLE_RESTAURANT, new String[]{CHAIN,DESCRIPTION,LAT,LONG,STREET,ZIP,
				TOWN,TEL,RATING,VOTES,PRICE_CAT,SEATS,AVAIL}, RES+"='"+name+"'", null, null, null, null);
		if (c.getCount() > 1)
		{
			System.err.println("Error : two or more retaurants seem to have the same name.");
		}
		c.moveToFirst();
		String chain = c.getString(c.getColumnIndex(CHAIN));
		String description = c.getString(c.getColumnIndex(DESCRIPTION));
		float latitude = c.getFloat(c.getColumnIndex(LAT));
		float longitude = c.getFloat(c.getColumnIndex(LONG));
		String address = c.getString(c.getColumnIndex(STREET));
		short zip = c.getShort(c.getColumnIndex(ZIP));
		String town = c.getString(c.getColumnIndex(TOWN));
		String tel = c.getString(c.getColumnIndex(TEL));
		float rating = c.getFloat(c.getColumnIndex(RATING));
		int votes = c.getInt(c.getColumnIndex(VOTES));
		float priceCat = c.getFloat(c.getColumnIndex(PRICE_CAT));
		short seats = c.getShort(c.getColumnIndex(SEATS));
		short availableSeats = c.getShort(c.getColumnIndex(AVAIL));
		
		// creates the Restaurant object to be returned
		Restaurant retour = new Restaurant(name, chain, address, town, tel, description, rating, votes,
				zip, seats, availableSeats, latitude, longitude, priceCat);
		
		// cuisine
		c = db.query(TABLE_CUISINE, new String [] {TYPE}, RES+"='"+name+"'", null, null, null, TYPE);
		ArrayList<String> cuisine = new ArrayList<String>(c.getCount());
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			cuisine.add(c.getString(1));
			c.moveToNext();
		}
		retour.setCuisines(cuisine);
		
		
		// timetable
		@SuppressWarnings("unchecked")
		ArrayList<TimeTable> semaine[] = new ArrayList[7];
		for (int i=0; i<7; i++)
		{
			semaine[i] = new ArrayList<TimeTable>(2);
		}
		 
		/*
		 * fonctionne aussi normalement
		 * c = db.rawQuery("SELECT day,timeOpen,timeClose FROM timetable WHERE resName='Crêperie Bretonne' ORDER BY CASE day"+
		 *		" WHEN 'lundi' THEN 0 WHEN 'mardi' THEN 1 WHEN 'mercredi' THEN 2 WHEN 'jeudi' THEN 3 WHEN 'vendredi' THEN 4 WHEN"+
		 *		" 'samedi' THEN 5 WHEN 'dimanche' THEN 6 END, timeOpen DESC", null);
		 */
		c = db.query(TABLE_TIMETABLE, new String[] {DAY,"strftime('%H:%M', timeOpen)","strftime('%H:%M', timeClose)"},
			RES+"='"+name+"'", null, null, null, "CASE "+DAY+" WHEN 'lundi' THEN 0 WHEN 'mardi' THEN 1 WHEN 'mercredi'"+
			" THEN 2 WHEN 'jeudi' THEN 3 WHEN 'vendredi' THEN 4 WHEN 'samedi' THEN 5 WHEN 'dimanche' THEN 6 END, timeOpen");
		c.moveToFirst();
		int position;
		while(!c.isAfterLast())
		{
			position = (int) weekMap.get(c.getString(c.getColumnIndex(DAY)));
			TimeTable temp = new TimeTable(c.getString(c.getColumnIndex(TIME_OPEN)),c.getString(c.getColumnIndex(TIME_CLOSE)));
			semaine[position].add(temp);
			c.moveToNext();
		}
		retour.setSemaine(semaine);

		this.close();
		return retour;
	}
	
	/**
	 * Updates the DB after a new rating.
	 * @param resName the name of the restaurant, must not be null
	 * @param rating the new rating, must be between 0 and 5
	 * @param votes the new number of votes, must be > 0
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 */
	long rateRestaurant (String resName, float rating, int votes) throws SQLiteException
	{
		// throws an exception if rating is not valid or if resName is null
		if (rating>5 || rating<0)
		{
			throw new SQLiteException("Error : wrong rating : "+rating);
		} 
		else if (votes<=0)
		{
			throw new SQLiteException("Error : wrong number of votes : "+votes);
		}
		else if (resName == null)
		{
			throw new SQLiteException("Error : no restaurant name given for request");
		}
		
		this.openWrite();

		ContentValues insertValues = new ContentValues(2);
		insertValues.put(RATING, rating);
		insertValues.put(VOTES, votes+1);
		long nrRows = db.update(TABLE_RESTAURANT, insertValues, RES+"='"+resName+"'", null);
		
		this.close();
		return nrRows;
	}
	
	/**
	 * @param resName the name of the restaurant, must not be null
	 * @param newAvail the new number of available seats, must be >= 0
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 */
	long updateAvail (String resName, short newAvail)
	{
		// throws an exception if newAvail is not valid or if resName is null
		if (newAvail<0)
		{
			throw new SQLiteException("Error : wrong rating : "+newAvail);
		} 
		else if (resName == null)
		{
			throw new SQLiteException("Error : no restaurant name given for request");
		}
		
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(1);
		insertValues.put(AVAIL, newAvail);
		long nrRows = db.update(TABLE_RESTAURANT, insertValues, RES+"='"+resName+"'", null);
		
		this.close();
		return nrRows;
	}

	
	
	/*******
	 * 
	 * Dish
	 * 
	 *******/
	
	/**
	 * Returns an arraylist of the dishes served in a restaurant, sorted by type (starter, main course, dessert, drinks)
	 * and the alphabetically.
	 * @param resName the name of the restaurant
	 * @return the arraylist of all the dishes served in the restaurant resName
	 */
	public ArrayList<Dish> getDishes(String resName)
	{
		this.openRead();
		Cursor c;
		Cursor d;
		
		c = db.query(TABLE_DISH, new String[] {DISH, RES, TYPE, SUBTYPE, DESCRIPTION, INVENTORY, PRICE},
				RES+"='"+resName+"'", null, null, null, " CASE "+TYPE+" WHEN 'Entrées' THEN 1 WHEN 'Plats' THEN 2"+
				" WHEN 'Desserts' THEN 3 WHEN 'Boissons' THEN 4 END, "+SUBTYPE);
		c.moveToFirst();
		
		ArrayList<Dish> dishes = new ArrayList<Dish>(c.getCount());
		while (!c.isAfterLast())
		{
			String dishName = c.getString(c.getColumnIndex(DISH));
			String type = c.getString(c.getColumnIndex(TYPE));
			String subtype = c.getString(c.getColumnIndex(SUBTYPE));
			String description = c.getString(c.getColumnIndex(DESCRIPTION));
			int inventory = c.getInt(c.getColumnIndex(INVENTORY));
			float price = c.getFloat(c.getColumnIndex(PRICE));
			
			// the dish's allergens
			d = db.query(TABLE_ALLERGEN, new String[]{ALLERGEN}, RES+"='"+resName+"' AND "+DISH+"='"+dishName+"'",
					null, null, null, ALLERGEN);
			ArrayList<String> allergens = new ArrayList<String>();
			if (d.getCount() > 0 )
			{
				d.moveToFirst();
				while (!d.isAfterLast())
				{
					allergens.add(c.getString(1));
					d.moveToNext();
				}
			} else {
				allergens = null;
			}
			
			// new dish object
			Dish dish = new Dish(dishName, type, subtype, price, inventory, description, allergens);
			dishes.add(dish);
			c.moveToNext();
		}
		
		this.close();
		return dishes;
	}
	
	
	/*********
	 * 
	 * Client
	 * 
	 *********/
	
	/**
	 * Checks whether the client has entered the right password.
	 * @param clientMail
	 * @param password
	 * @return true or false, depending on whether the password is the same as the one found in the DB
	 */
	boolean checkPassword (String clientMail, String password)
	{
		this.openRead();
		Cursor c;
		
		c = db.query(TABLE_CLIENT, new String[] {password}, MAIL+"='"+clientMail+"'", null, null, null, null);
		int count = c.getCount();
		if (count == 1)
		{
			this.close();
			return (password.equals(c.getString(1)));
			
		}
		else if (count > 1)
		{
			System.err.println("Error : two or more clients seem to have the same mail.");
		} 
		else if (count == 0)
		{
			// no such client in the DB
		} 
		else 
		{
			System.err.println("Error : the access to the DB must have failed.");
		}
		this.close();
		return false;
	}

	/**
	 * Adds a client to the DB.
	 * @param mail, must not be null
	 * @param name, must not be null
	 * @param password, must not be null
	 * @param tel
	 * @return if -1, then error; otherwise the new rowId
	 */
	long addClient (String mail, String name, String password, String tel) throws SQLiteException
	{
		// throws an exception if the mandatory information is not given
		if ((mail==null) || (name==null) || (password==null))
		{
			throw new SQLiteException("Arguments missing!");
		}
		
		this.openWrite();

		ContentValues insertValues = new ContentValues(4);
		insertValues.put(MAIL, mail);
		insertValues.put(CLIENT, name);
		insertValues.put(PASSWORD, password);
		insertValues.put(TEL, tel);
		long rowId = db.insertOrThrow(TABLE_CLIENT, TEL, insertValues);
		
		this.close();
		return rowId;
	}
	
	/**
	 * Changes the mail of a client in the DB.
	 * @param oldMail the client's old mail (used to identify him), must not be null
	 * @param newMail the client's new mail, must not be null
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException
	 */
	long changeMail (String oldMail, String newMail) throws SQLiteException
	{
		// throws an exception if the mandatory information is not given
		if ((oldMail==null) || (newMail==null))
		{
			throw new SQLiteException("Arguments missing!");
		}
		
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(1);
		insertValues.put(MAIL, newMail);
		long nrRows = db.update(TABLE_CLIENT, insertValues, MAIL+"='"+oldMail+"'", null);
		
		this.close();
		return nrRows;
	}
	
	/**
	 * Changes the name of a client in the DB.
	 * @param mail the mail which identifies the client, must not be null
	 * @param newName the new name the client wants to have in the DB, must not be null
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException
	 */
	long changeName (String mail, String newName) throws SQLiteException
	{
		// throws an exception if the mandatory information is not given
		if ((mail==null) || (newName==null))
		{
			throw new SQLiteException("Arguments missing!");
		}

		this.openWrite();
		
		ContentValues insertValues = new ContentValues(1);
		insertValues.put(CLIENT, newName);
		long nrRows = db.update(TABLE_CLIENT, insertValues, MAIL+"='"+mail+"'", null);

		this.close();
		return nrRows;
	}
	
	long changePassword (String mail, String newPassword) throws SQLiteException
	{
		// throws an exception if the mandatory information is not given
		if ((mail==null) || (newPassword==null))
		{
			throw new SQLiteException("Arguments missing!");
		}
		
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(1);
		insertValues.put(PASSWORD, newPassword);
		long nrRows = db.update(TABLE_CLIENT, insertValues, MAIL+"='"+mail+"'", null);
		
		this.close();
		return nrRows;
	}
	
	/**
	 * Changes the mail of a client in the DB.
	 * @param mail the client's mail which identifies him, must not be null
	 * @param newTel the phone number the client wants to have in the DB, may be null
	 * @return 1 if update was performed on 1 row as expected, any other value means an error has occurred
	 * @throws SQLiteException
	 */
	long changeTel (String mail, String newTel) throws SQLiteException
	{
		// throws an exception if the mandatory information is not given
		if (mail==null)
		{
			throw new SQLiteException("Arguments missing!");
		}
		
		this.openWrite();
		
		ContentValues insertValues = new ContentValues(1);
		insertValues.put(TEL, newTel);
		long nrRows = db.update(TABLE_CLIENT, insertValues, MAIL+"='"+mail+"'", null);
		
		this.close();
		return nrRows;
	}
	
	
	
	/*********
	 * 
	 * Orders
	 *
	 *********/
	
	
	
	/**************
	 * 
	 * Reservations
	 *
	 **************/
	
	/* NULL value safe DB access methods */
	// UTILITE ?
	// tests sur le Quick!
	String getStringOrNull(Cursor c, int i)
	{
		if(c.isNull(i))
		{ 
			return null;
		} else
		{
			return c.getString(i);
		}
	}
	int getIntOrNull(Cursor c, int i)
	{
		if (c.isNull(i))
		{
			return 0;
		} else
		{
			return c.getInt(i);
		}
	}
	short getShortOrNull(Cursor c, int i)
	{
		if (c.isNull(i))
		{
			return 0;
		} else
		{
			return c.getShort(i);
		}
	}
	float getFloatOrNull(Cursor c, int i)
	{
		if (c.isNull(i))
		{
			return 0;
		} else
		{
			return c.getFloat(i);
		}
	}

}
