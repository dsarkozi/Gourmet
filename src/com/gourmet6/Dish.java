package com.gourmet6;

import java.util.ArrayList;

public class Dish {
	private String name;
	private String type;
	private float price;
	private int inventory;
	private String description;
	private ArrayList<String> allergens;
	
	public Dish(String name,String type,float price,int inventory,String description,ArrayList<String> allergens)
	{
		this.name = name;
		this.type = type;
		this.price = price;
		this.inventory = inventory;
		this.description = description;
		this.allergens = allergens;
	}
	
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
	
	public float getPrice() 
	{
		return price;
	}
	
	public void setPrice(float price) 
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
	
	public boolean hasAllergen(String allergen)
	{
		return this.allergens.contains(allergen);
	}

}
