/**
 * 
 */
package com.gourmet6;

import android.content.Context;

/**
 * @author Lena
 *
 */
public class RestaurantDBHandler extends DBHandler {
	
	public static final String TABLE_NAME = "restaurant";
    public static final String COL_resName = "resName";
    public static final String COL_chainName = "chainName";
    public static final String COL_description = "description";
    public static final String COL_lat = "lat";
    public static final String COL_long = "long";
    public static final String COL_street = "street";
    public static final String COL_zip = "zip";
    public static final String COL_town = "town";
    public static final String COL_tel = "tel";
    public static final String COL_rating = "rating";
    public static final String COL_priceCat = "priceCat";
    public static final String COL_avail = "avail";
	
	public RestaurantDBHandler(Context context)
	{
		super(context);
	}
	
	// pas prévu pour l'instant : 
	// insertRestaurant
	// updateRestaurant
	// removeRestaurant
	
	/**
	 * 
	 * @param name
	 * @return the Restaurant corresponding to name
	 */
	
/*	public Restaurant getRestaurant(String name)
	{
		this.openRead();
		// information held by the restaurant table
		Cursor c = db.query(TABLE_NAME, new String[]{COL_chainName,COL_description,COL_lat,COL_long,COL_street,COL_zip,
				COL_town,COL_tel,COL_rating,COL_priceCat,COL_avail}, COL_resName+"='"+name+"'", null, null, null, null);
		if (c.getCount() > 1)
		{
			System.err.println("Error : two retaurants seem to have the same name");
		}
		c.moveToFirst();
		
		int i;
		i = c.getColumnIndex(COL_chainName); String chain = c.getString(i);
		i = c.getColumnIndex(COL_description); String description = c.getString(i);
		i = c.getColumnIndex(COL_lat); float latitude = c.getFloat(i);
		i = c.getColumnIndex(COL_long); float longitude = c.getFloat(i);
		i = c.getColumnIndex(COL_street); String address = c.getString(i);
		i = c.getColumnIndex(COL_zip); short zip = c.getShort(i);
		i = c.getColumnIndex(COL_town); String town = c.getString(i);
		i = c.getColumnIndex(COL_tel); String tel = c.getString(i);
		i = c.getColumnIndex(COL_rating); byte rating = (byte) c.getInt(i);
		i = c.getColumnIndex(COL_priceCat); float priceCat = c.getFloat(i);
		i = c.getColumnIndex(COL_avail); short availableSeats = c.getShort(i);
		
		Restaurant retour = new Restaurant(name, chain, address, town, tel, description, rating,
				zip, availableSeats, availableSeats, latitude, longitude, priceCat);

		
		// horaires
		// information held by the timetable table
		c = db.rawQuery("SELECT day,timeOpen,timeClose FROM timetable WHERE resName='Crêperie Bretonne' ORDER BY CASE day"+
				" WHEN 'lundi' THEN 0 WHEN 'mardi' THEN 1 WHEN 'mercredi' THEN 2 WHEN 'jeudi' THEN 3 WHEN 'vendredi' THEN 4 WHEN"+
				" 'samedi' THEN 5 WHEN 'dimanche' THEN 6 END, timeOpen DESC", null);
		// TODO
		// à finir, voir avec Quentin pour le format de l'heure
		
		this.close();
		return retour;
	}*/
	
	/**
	 * 
	 * @param town
	 * @return a table of the names of all restaurants known by the DB
	 */
/*	public String[] getAllResNames(String town)
	{
		this.openRead();
		Cursor c;
		if (town != null)
		{
			c = db.query(TABLE_NAME, new String[] {COL_resName}, COL_town+"='"+town+"'", null, null, null, COL_resName);
		} else {
			c = db.query(TABLE_NAME, new String[] {COL_resName}, null, null, null, null, COL_resName);
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
	}*/
}
