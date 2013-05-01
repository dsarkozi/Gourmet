/**
 * 
 */
package com.gourmet6;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;

/**
 * @author Lena
 *
 */
public class DishDBHandler extends DBHandler {

	public static final String TABLE_NAME = "dish";
	public static final String COL_dishName = "dishName";
	public static final String COL_resName = "resName";
	public static final String COL_type = "type";
	public static final String COL_description = "description";
	public static final String COL_stock = "stock";
	public static final String COL_price = "price";

	public DishDBHandler(Context context)
	{
		super(context);
	}

	/**
	 * 
	 * @param resName
	 * @return the arraylist of all the dishes served in the restaurant resName
	 */
	public ArrayList<Dish> getDishes(String resName)
	{
		this.openRead();
		
		Cursor c = db.query(TABLE_NAME, new String[] {COL_dishName, COL_resName, COL_type, COL_description, COL_stock, COL_price},
				COL_resName+"="+"'"+resName+"'", null, null, null, " CASE "+COL_type+" WHEN 'entrée' THEN 1 WHEN 'plat' THEN 2"+
				" WHEN 'dessert' THEN 3 WHEN 'boisson' THEN 4 END, "+COL_dishName+" DESC");
		c.moveToFirst();
		
		ArrayList<Dish> dishes = new ArrayList<Dish>(c.getCount());
		Cursor d;
		while (!c.isAfterLast())
		{
			int i;
			i = c.getColumnIndex(COL_dishName); String dishName = c.getString(i);
			i = c.getColumnIndex(COL_type); String type = c.getString(i);
			i = c.getColumnIndex(COL_description); String description = c.getString(i);
			i = c.getColumnIndex(COL_stock); int stock = c.getInt(i);
			i = c.getColumnIndex(COL_price); float price = c.getFloat(i);
			
			d = db.query("allergen", new String[]{"alleName"}, COL_resName+"='"+resName+"' AND "+COL_dishName+"='"+dishName+"'",
					null, null, null, "alleName DESC");
			
			ArrayList<String> allergens = new ArrayList<String>();
			if (d.getCount() > 0 )
			{
				d.moveToFirst();
				while (!d.isAfterLast())
				{
					allergens.add(c.getString(1));
					d.moveToNext();
				}
			}
			
			Dish dish = new Dish(dishName, type, price, stock, description, allergens);
		
			dishes.add(dish);
			c.moveToNext();
		}
		this.close();
		return dishes;
	}
}
