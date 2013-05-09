package com.gourmet6;

import java.util.ArrayList;

import android.R.string;

/**
 * A class representing a dish and its properties. 
 * 
 * @author Group 6
 * @version 06.05.2013
 */
public class Dish {
	
	private String name;
	private String type;
	private String subtype;
	private double price;
	private int inventory;
	private int quantity;
	private String description;
	private ArrayList<String> allergens;
	
	/**
	 * Constructor.
	 * @param name the dish's name
	 * @param type the dish's type, may be 'Entrées', 'Plats', 'Desserts' or 'Boissons'
	 * @param subtype the dish's subtype, e.g. 'Viandes' or 'Salades'
	 * @param price the dish's price
	 * @param inventory the quantity still available
	 * @param description the dish's description
	 * @param allergens the allergens the dish contains
	 */
	public Dish(String name, String type, String subtype, float price, 
			int inventory, String description, ArrayList<String> allergens)
	{
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.price = price;
		this.inventory = inventory;
		this.quantity = 0;
		this.description = description;
		this.allergens = allergens;
	}
	
	/**
	 * Tests whether a dish contains a certain allergen.
	 * @param allergen the allergen to look for in the dish
	 * @return true if the dish contains the allergen, false otherwise
	 */
	public boolean hasAllergen(String allergen)
	{
		if (this.allergens != null)
		{
			return this.allergens.contains(allergen);
		}

		return false;
	}
	
	/**********************
	 * Getters and setters
	 **********************/
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	public String getSubtype()
	{
		return subtype;
	}
	public void setSubtype(String subtype)
	{
		this.subtype = subtype;
	}
	public double getPrice() 
	{
		return price;
	}
	public void setPrice(double price) 
	{
		this.price = price;
	}
	public int getInventory() 
	{
		return inventory;
	}
	public void setInventory(int inventory) 
	{
		this.inventory = inventory;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public ArrayList<String> getAllergens() 
	{
		return allergens;
	}
	public void setAllergens(ArrayList<String> allergens) 
	{
		this.allergens = allergens;
	}
	public int getQuantity() 
	{
		return quantity;
	}
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
}
