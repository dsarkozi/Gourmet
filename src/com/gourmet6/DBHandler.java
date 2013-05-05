/**
 * 
 */
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
 *
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
    public static final String RESER_NR = "reservNr";
    public static final String INVENTORY = "inventory";
    public static final String SEATS = "seats";
    public static final String STREET = "street";
    public static final String TEL = "tel";
    public static final String TIME_OPEN = "timeOpen";
    public static final String TIME_CLOSE = "timeClose";
    public static final String TOWN = "town";
    public static final String TYPE = "type";
    public static final String VOTES = "votes";
    public static final String ZIP = "zip";
    
	 // variable globale ?
	 //Integer get(String)
    // helps managing the timetables given a certain day
    static HashMap<String,Integer> weekMap = new HashMap<String,Integer>();
    static {
    	weekMap.put("lundi", 0);
    	weekMap.put("mardi", 1);
    	weekMap.put("mercredi", 2);
    	weekMap.put("jeudi", 3);
    	weekMap.put("vendredi", 4);
    	weekMap.put("samedi", 5);
    	weekMap.put("dimanche", 6);
    }

    
    /*
     * General access methods.
     * 
     */
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
	
	
	/*
	 * Queries on the restaurant
	 */
	
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
		byte rating = (byte) c.getInt(c.getColumnIndex(RATING));
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
			" THEN 2 WHEN 'jeudi' THEN 3 WHEN 'vendredi' THEN 4 WHEN 'samedi' THEN 5 WHEN 'dimanche' THEN 6 END, timeOpen DESC");
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
	 * @param resName
	 * @param rating
	 * @return 
	 */
	long rateRestaurant (String resName, int rating)
	{
		// TODO
		return 0;
	}
	
	/**
	 * Returns an array containing all the distinct town names appearing in the DB table restaurant,
	 * sorted in alphabetically.
	 * @param town
	 * @return a String array containing all the town names; if town is null, returns all the towns.
	 */
	public String[] getAllResNames(String town)
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
		String[] retour = new String[length];
		for (int i=0; i<length; i++)
		{
			c.moveToPosition(i);
			retour[i] = c.getString(1);
		}
		
		this.close();
		return retour;
	}
	
	
	/*
	 * Queries on the dishes
	 */
	
	/**
	 * Returns an arraylist of the dishes served in a restaurant, sorted by type (starter, main course, dessert, drinks)
	 * and the alphabetically.
	 * @param resName
	 * @return the arraylist of all the dishes served in the restaurant resName
	 */
	public ArrayList<Dish> getDishes(String resName)
	{
		this.openRead();
		Cursor c;
		Cursor d;
		
		c = db.query(TABLE_DISH, new String[] {DISH, RES, TYPE, DESCRIPTION, INVENTORY, PRICE},
				RES+"='"+resName+"'", null, null, null, " CASE "+TYPE+" WHEN 'entrée' THEN 1 WHEN 'plat' THEN 2"+
				" WHEN 'dessert' THEN 3 WHEN 'boisson' THEN 4 END, "+DISH+" DESC");
		c.moveToFirst();
		
		ArrayList<Dish> dishes = new ArrayList<Dish>(c.getCount());
		while (!c.isAfterLast())
		{
			String dishName = c.getString(c.getColumnIndex(DISH));
			String type = c.getString(c.getColumnIndex(TYPE));
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
			Dish dish = new Dish(dishName, type, price, inventory, description, allergens);
			dishes.add(dish);
			c.moveToNext();
		}
		
		this.close();
		return dishes;
	}
	
	
	/*
	 * Queries on the client
	 */
	
	/**
	 * Checks whether the client has entered the right password.
	 * @param clientMail
	 * @param password
	 * @return true or false, depending on whether the password is the same as the one found in the DB
	 */
	boolean correctPassword (String clientMail, String password)
	{
		this.openRead();
		Cursor c;
		
		c = db.query(TABLE_CLIENT, new String[] {password}, MAIL+"='"+clientMail+"'", null, null, null, null);
		int count = c.getCount();
		if (count == 1)
		{
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

		ContentValues insertValues = new ContentValues();
		insertValues.put(MAIL, mail);
		insertValues.put(CLIENT, name);
		insertValues.put(PASSWORD, password);
		insertValues.put(TEL, tel);
		long rowId = db.insertOrThrow(TABLE_CLIENT, TEL, insertValues);
		
		return rowId;
	}
	
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
