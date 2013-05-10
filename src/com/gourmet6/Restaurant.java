package com.gourmet6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * A class representing a restaurant and its properties.
 * 
 * @author Group 6
 * @version 06.05.2013
 */

@SuppressLint("DefaultLocale")
public class Restaurant {
	
	private String name;
	private String chain;
	private String address;
	private String town;
	private String tel;
	private String mail;
	private String web;
	private String description;
	private ArrayList<String> cuisines;
	private int zip;
	private int seats;
	private int nbrPrsHasVoted;
	private int availableSeats; // real-time based
	private int nbrImage;
	private double latitude;
	private double longitude;
	private double rating;
	private double priceCat; // average price
	private ArrayList<Dish> listDishes = null;
	private ArrayList<TimeTable> semaine;
	

	public Restaurant(String name, String chain, String address, String town, String tel, String web, String mail,
			String description, double rating, int nbrPrsHasVoted, int zip, int seats, 
			int availableSeats, double latitude, double longitude, double priceCat, int nbrImg)
	{
		this.name = name;
		this.chain = chain;
		this.address = address;
		this.town = town;
		this.tel = tel;
		this.web = web;
		this.mail = mail;
		this.description = description;
		this.rating = rating;
		this.nbrPrsHasVoted = nbrPrsHasVoted;
		this.zip = zip;
		this.seats = seats;
		this.availableSeats = availableSeats;
		this.latitude = latitude;
		this.longitude = longitude;
		this.priceCat = priceCat;
		this.nbrImage = nbrImg;
	}
	

	/**
	 * Adds all the restaurant's dishes known by the DB to the restaurant.
	 * @param dbh the DBHandler to be used to interact with the DB
	 */
	public void createListDishes(DBHandler dbh)
	{
		if (this.listDishes != null){
			this.listDishes.clear();
		}
		this.listDishes = dbh.getDishes(this.name);
		
	}
	
	/**
	 * Gets the names of all the Dishes served in the restaurant.
	 * @return a table containing the name of all Dishes served in the restaurant.
	 * If listDishes is null, returns null.
	 */
	public String[] getDishesNames()
	{
		if (this.listDishes == null)
		{
			return null;
		}
		
		int length = this.listDishes.size();
		String [] names = new String[length];
		for (int i=0; i<length; i++)
		{
			names[i] = this.listDishes.get(i).getName();
		}
		return names;
	}
	
	/**
	 * @param name hte dish's name
	 * @return the dish which has the name specified in param. If the listDishes is empty
	 *  or if no dish corresponded, returns null.
	 */
	public Dish getDish(String name)
	{
		if(listDishes==null)
		{
			Log.e("Restaurant", "getDish with an empty listDishes. Call createDishes before.");
		}
		else
		{
			for(Dish d : listDishes)
			{
				if(d.getName().equals(name)) 
					return d;
			}
		}
		return null;
	}
	
	public Dish getDish(String name, ArrayList<Dish> toFilter )
	{
		if(toFilter==null)
		{
			Log.e("Restaurant", "getDish with an empty listDishes. Call createDishes before.");
		}
		else
		{
			for(Dish d : toFilter)
			{
				if(d.getName().equals(name)) return d;
			}
		}
		return null;
	}
	
	/**
	 * Sorts the ArrayList of Dishes in increasing price order.
	 */
	public void sortDishesPrice(){
		if (this.listDishes == null)
		{
			Log.e("Restaurant", "sortDishesPrice with an empty listDishes. Call createDishes.");
			return;
		}

		Collections.sort(this.listDishes, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return (int) (o1.getPrice()-o2.getPrice());
			}

		});
	}
	
	public ArrayList<Dish> sortDishesPrice(ArrayList<Dish> toFilter){
		if (toFilter == null)
		{
			Log.e("Restaurant", "sortDishesPrice with an empty listDishes. Call createDishes.");
		}

		Collections.sort(toFilter, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return (int) (o1.getPrice()-o2.getPrice());
			}

		});
		
		return toFilter;
	}
	
	/**
	 * Gets all distinct types of all Dishes served in the restaurant.
	 * @return an ArrayList containing all the different types of Dishes.
	 * If listDishes is empty or if no Dish has a type, returns an empty Arraylist.
	 */
	public ArrayList<String> getDishesTypes()
	{
		if (this.listDishes == null)
		{
			System.err.println("getDishesType with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<String> result = new ArrayList<String>();
		String type;
		for (Dish d : this.listDishes)
		{
			type = d.getType();
			if (!(result.contains(type)))
				result.add(type);
		}
		return result;
	}
	
	public ArrayList<String> getDishesTypes(ArrayList<Dish> toFilter)
	{
		if (toFilter == null)
		{
			System.err.println("getDishesType with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<String> result = new ArrayList<String>();
		String type;
		for (Dish d : toFilter)
		{
			type = d.getType();
			if (!(result.contains(type)))
				result.add(type);
		}
		return result;
	}
	
	/**
	 * Gets all Dishes of a certain type served in the restaurant.
	 * @param type the type of the wanted Dishes
	 * @return an ArrayList of Dishes of the given type. If there is no such Dish or
	 * if the list of Dishes is empty, returns an empty ArrayList.
	 */
	public ArrayList<Dish> filterDishesType(String type)
	{
		if (this.listDishes == null)
		{
			Log.e("Restaurant", "filterDishesType with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : this.listDishes)
		{
			if (d.getType().equals(type))
				result.add(d);
		}
		return result;
	}
	
	public ArrayList<Dish> filterDishesType(String type, ArrayList<Dish> toFilter)
	{
		if (toFilter == null)
		{
			Log.e("Restaurant", "filterDishesType with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : toFilter)
		{
			if (d.getType().equals(type))
				result.add(d);
		}
		return result;
	}
	
	/**
	 * @return
	 */
	public ArrayList<String> getDishesSubtypes()
	{
		if (this.listDishes == null)
		{
			Log.e("Restaurant", "getDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<String> result = new ArrayList<String>();
		String subtype;
		for (Dish d : this.listDishes)
		{
			subtype = d.getSubtype();
			if (!(result.contains(subtype)))
				result.add(subtype);
		}
		return result;
	}
	
	public ArrayList<String> getDishesSubtypes(String type)
	{
		if (this.listDishes == null)
		{
			Log.e("Restaurant", "getDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<String> result = new ArrayList<String>();
		String subtype;
		for (Dish d : this.listDishes)
		{
			if(d.getType().equals(type)){
				subtype = d.getSubtype();
				if (!(result.contains(subtype)))
					result.add(subtype);
			}
		}
		return result;
	}
	
	public ArrayList<String> getDishesSubtypes(ArrayList<Dish> toFilter)
	{
		if (toFilter == null)
		{
			Log.e("Restaurant", "getDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<String> result = new ArrayList<String>();
		String subtype;
		for (Dish d : toFilter)
		{
			subtype = d.getSubtype();
			if (!(result.contains(subtype)))
				result.add(subtype);
		}
		return result;
	}
	
	public ArrayList<String> getDishesSubtypes(String type, ArrayList<Dish> toFilter)
	{
		if (toFilter == null)
		{
			Log.e("Restaurant", "getDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<String> result = new ArrayList<String>();
		String subtype;
		for (Dish d : toFilter)
		{
			if(d.getType().equals(type)){
				subtype = d.getSubtype();
				if (!(result.contains(subtype)))
					result.add(subtype);
			}
		}
		return result;
	}
	
	/**
	 * @param subtype the subtype of the wanted Dishes
	 * @return an ArrayList of Dishes of the given subtype. If there is no such Dish or
	 * if the list of Dishes is empty, returns an empty ArrayList.
	 */
	public ArrayList<Dish> filterDishesSubtype(String subtype)
	{
		if (this.listDishes == null)
		{
			Log.e("Restaurant", "filterDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : this.listDishes)
		{
			if (d.getSubtype().equals(subtype))
				result.add(d);
		}
		return result;
	}
	
	public ArrayList<Dish> filterDishesSubtype(String subtype, String type)
	{
		if (this.listDishes == null)
		{
			Log.e("Restaurant", "filterDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : this.listDishes)
		{
			if ((d.getType().equals(type)) && (d.getSubtype().equals(subtype)))
				result.add(d);
		}
		return result;
	}
	
	public ArrayList<Dish> filterDishesSubtype(String subtype, ArrayList<Dish> toFilter)
	{
		if (toFilter == null)
		{
			Log.e("Restaurant", "filterDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : toFilter)
		{
			if (d.getSubtype().equals(subtype))
				result.add(d);
		}
		return result;
	}
	
	public ArrayList<Dish> filterDishesSubtype(String subtype, String type, ArrayList<Dish> toFilter)
	{
		if (toFilter == null)
		{
			Log.e("Restaurant", "filterDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : toFilter)
		{
			if (d.getSubtype().equals(subtype) && d.getType().equals(type))
				result.add(d);
		}
		return result;
	}
	
	/**
	 * Gets all the Dishes not containing a certain allergen served in the restaurant.
	 * @param al the allergen to exclude
	 * @return an ArrayList of Dishes not containing al as allergen. If there is no such Dish or
	 * if the list of Dishes is empty, returns an empty ArrayList.
	 */
	public ArrayList<Dish> filterDishesAllergen(String al)
	{
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : this.listDishes)
		{
			if (!(d.hasAllergen(al)))
				result.add(d);
		}
		return result;
	}
	
	public ArrayList<Dish> filterDishesAllergen(String al, ArrayList<Dish> toFilter)
	{
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : toFilter)
		{
			if (!(d.hasAllergen(al)))
				result.add(d);
		}
		return result;
	}
	
	public ArrayList<String> getAllergensForFilter(ArrayList<Dish> dishes)
	{
		ArrayList<String> filters = new ArrayList<String>();
		for (Dish d : dishes)
		{
			ArrayList<String> allergens  = d.getAllergens();
			if(allergens != null){
				for (String al : allergens)
				{
					if (!(filters.contains(al)))
					{
						filters.add(al);
					}
				}
			}
		}
		return filters;
	}
	
	public ArrayList<String> getFilters(ArrayList<Dish> dishes)
	{
		ArrayList<String> filters = new ArrayList<String>();
		filters.add("All");
		filters.addAll(getAllergensForFilter(dishes));
		filters.addAll(getDishesTypes(dishes));
		filters.addAll(getDishesSubtypes(dishes));
		filters.add("Price");
		
		return filters;
	}
	
	
	/**
	 * Changes the rating of the restaurant given the current rate, the new rating
	 * and the number of people who have voted. Updates the DB.
	 * @param vote the new rating
	 * @param dbh the DBHandler used to interact with the DB
	 */
	public void rateRestaurant(double vote, DBHandler dbh)
	{
		System.out.println(rating+", et nbrPrsHasVoted="+nbrPrsHasVoted);
		this.rating *= this.nbrPrsHasVoted;
		this.nbrPrsHasVoted++;
		this.rating += vote;
		this.rating /= this.nbrPrsHasVoted;
		
		dbh.rateRestaurant(this.name, (float)this.rating, this.nbrPrsHasVoted);
		System.out.println(rating);
	}
	
	public boolean checkOrder(Order order)
	{
		if(order.getOrderDishes() == null)
			return false;
		
		for (Dish dish : order.getOrderDishes())
		{
			if (dish.getQuantity()>dish.getInventory()) 
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param res
	 * @return cette methode renvoit true si la reservation est correcte, c-a-d:
	 * -Qu'elle specifie des plats qui sont encore disponibles, si une commande est jointe à la reservation
	 * -Qu'elle reserve lorsque le restaurant est ouvert.
	 * -Qu'elle spécifie une nombre de personne <= au nombre de place encore disponible dans le restaurant.
	 * Renvoit false sinon.
	 */
	public String checkReservation(Reservation res, DBHandler dbh){
		if(res.getReservationOrder()!=null){
			for(Dish dish : res.getReservationOrder().getOrderDishes()){
				if(dish.getQuantity()>dish.getInventory()) return "Incorrect quantity";
			}
		}
		//Check hour.
		boolean timeTableOk = false;
		for(TimeTable tt : semaine)
		{
			if(tt.isInTimeTable(res.getReservationTime()))
			{
				timeTableOk = true;
				String start = getInString(res.getReservationTime(), -2, 0);
				String end = getInString(res.getReservationTime(), 2, 0);
				if(dbh.getAvailBetweenDateTime(res.getReservationResName(), start, end)<res.getReservationPeople()){
					return "Not enough place for this date";
				}
				
			}
		}
		if(!timeTableOk) return "Incorrect date or hour, please see the time table";
		if(res.getReservationPeople()<=availableSeats) return "Too many people";
		else return null;
	}
	
	private String getInString(GregorianCalendar date, int hour, int minute)
	{
		GregorianCalendar temp = date;
		//on soustrait le temps adéquat.
		temp.set(GregorianCalendar.HOUR_OF_DAY, temp.get(GregorianCalendar.HOUR_OF_DAY)+hour);
		temp.set(GregorianCalendar.MINUTE, temp.get(GregorianCalendar.MINUTE)+minute);
		return TimeTable.parseDateInString(temp);
	}

	public void Orderreboot()
	{
		if(this.listDishes == null)
			return;
		
		for(Dish d : this.listDishes)
		{
			d.setQuantity(0);
		}
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
	public String getChain()
	{
		return chain;
	}
	public void setChain(String chain)
	{
		this.chain = chain;
	}
	public String getAdress()
	{
		return address;
	}
	public void setAdress(String adress)
	{
		this.address = adress;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) 
	{
		this.zip = zip;
	}
	public String getTown()
	{
		return town;
	}
	public void setTown(String town) 
	{
		this.town = town;
	}
	public double getLatitude() 
	{
		return latitude;
	}
	public void setLatitude(double latitude) 
	{
		this.latitude = latitude;
	}
	public double getLongitude() 
	{
		return longitude;
	}
	public void setLongitude(double longitude) 
	{
		this.longitude = longitude;
	}
	public String getTel() 
	{
		return tel;
	}
	public void setTel(String tel) 
	{
		this.tel = tel;
	}
	public String getMail() 
	{
		return mail;
	}
	public void setMail(String mail) 
	{
		this.mail = mail;
	}
	public String getWeb() 
	{
		return web;
	}
	public void setWeb(String web) 
	{
		this.web = web;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public ArrayList<String> getCuisines() 
	{
		return cuisines;
	}
	public void setCuisines(ArrayList<String> cuisines) 
	{
		this.cuisines = cuisines;
	}
	public int getSeats() 
	{
		return seats;
	}
	public void setSeats(int seats) 
	{
		this.seats = seats;
	}
	public int getAvailableSeats() 
	{
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) 
	{
		this.availableSeats = availableSeats;
	}
	public double getRating() 
	{
		return rating;
	}
	public void setRating(double rating) 
	{
		this.rating = rating;
	}
	public int getNbrPrsHasVoted()
	{
		return this.nbrPrsHasVoted;
	}
	public void setNbrPrsHasVoted(int nbrPrsHasVoted)
	{
		this.nbrPrsHasVoted = nbrPrsHasVoted;
	}
	public double getPriceCat() {
		return priceCat;
	}
	public void setPriceCat(double priceCat)
	{
		this.priceCat = priceCat;
	}
	public ArrayList<Dish> getListDishes() 
	{
		return listDishes;
	}
	public void setListDishes(ArrayList<Dish> listDishes) 
	{
		this.listDishes = listDishes;
	}
	public ArrayList<TimeTable> getSemaine() 
	{
		return semaine;
	}
	public void setSemaine(ArrayList<TimeTable> semaine) 
	{
		this.semaine = semaine;
	}
	public static String getNameImg(String name)
	{
		String res = name;
		res = res.toLowerCase();
		res = res.replace(' ', '_');
		res = res.replace('é', 'e');
		res = res.replace('ê', 'e');
		res = res.replace('è', 'e');
		res = res.replace('à', 'a');
		res = res+"_";
		return res;
	}
	
	public void setDish(Dish d){
		int index = this.listDishes.indexOf(getDish(d.getName()));
		if(index == -1)
			return;
		
		this.listDishes.remove(index);
		this.listDishes.add(index,d);
	}


	public int getNbrImage() {
		return nbrImage;
	}


	public void setNbrImage(int nbrImage) {
		this.nbrImage = nbrImage;
	}


	public String getHoraireInString()
	{
		ArrayList<TimeTable> copy = new ArrayList<TimeTable>();
		for(TimeTable tt : semaine){
			copy.add(tt);
		}
		TimeTable temp; String res = "";
		while(copy.size()!=0)
		{
			temp = getTimeSlot(copy);
			res = res+temp.parseInString()+"\n";
		}
		return res;
	}


	private TimeTable getTimeSlot(ArrayList<TimeTable> copy)
	{
		int i;
		for(i=2; i<8; i++)
		{
			for(TimeTable tt : copy)
			{
				if(i==(Integer)TimeTable.weekMap.get(tt.getJourDebut()))
				{
					TimeTable res = tt;
					copy.remove(tt);
					return res;
				}
			}
		}
		i=1;
		for(TimeTable tt : copy)
		{
			if(i==(Integer)TimeTable.weekMap.get(tt.getJourDebut()))
			{
				TimeTable res = tt;
				copy.remove(tt);
				return res;
			}
		}
		return null;
	}
}
