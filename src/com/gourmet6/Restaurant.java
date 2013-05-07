package com.gourmet6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * A class representing a restaurant and its properties.
 * 
 * @author Group 6
 * @version 06.05.2013
 */

public class Restaurant {
	
	private String name;
	private String chain;
	private String address;
	private short zip;
	private String town;
	private float latitude;
	private float longitude;
	private String tel;
	private String mail;
	private String web;
	private String description;
	private ArrayList<String> cuisines;
	private short seats;
	private short availableSeats; // en temps reel
	private float rating;
	private int nbrPrsHasVoted;
	private float priceCat; // Si un restaurant est cher -> moyenne des prix
	private ArrayList<Dish> listDishes = null;
	/**
	 * Semaine contient 7 cases, une par jour de la semaine. A chaque jour correspond une list de plage horaire.
	 * Correspondance :
	 * 0 : Dimanche
	 * 1 : Lundi
	 * 2 : Mardi
	 * 3 : Mercredi
	 * 4 : Jeudi
	 * 5 : Vendredi
	 * 6 : Samedi
	 */
	private ArrayList<TimeTable> semaine[];
	
	public Restaurant(String name)
	{
		this.name = name;
		
	}
	
	/**
	 * Constructor.
	 * @param name
	 * @param chain
	 * @param address
	 * @param town
	 * @param tel
	 * @param description
	 * @param rating
	 * @param nbrPrsHasVoted
	 * @param zip
	 * @param seats
	 * @param availableSeats
	 * @param latitude
	 * @param longitude
	 * @param priceCat
	 */
	public Restaurant(String name, String chain, String address, String town, String tel, 
			String description, float rating, int nbrPrsHasVoted, short zip, short seats, 
			short availableSeats, float latitude, float longitude, float priceCat)
	{
		this.name = name;
		this.chain = chain;
		this.address = address;
		this.town = town;
		this.tel = tel;
		this.description = description;
		this.rating = rating;
		this.nbrPrsHasVoted = nbrPrsHasVoted;
		this.zip = zip;
		this.seats = seats;
		this.availableSeats = availableSeats;
		this.latitude = latitude;
		this.longitude = longitude;
		this.priceCat = priceCat;
	}
	

	/**
	 * Adds all the restaurant's dishes known by the DB to the restaurant.
	 * @param dbh the DBHandler to be used to interact with the DB
	 */
	public void createListDishes(DBHandler dbh)
	{
		if (this.listDishes == null)
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
	 * @param name
	 * @return the dish which has the name specified in param. If the listDishes, or if no dish corresponded,
	 * return null.
	 */
	public Dish getDish(String name)
	{
		if(listDishes==null)
		{
			System.err.println("getDishe with an empty listDishes. Call createDishes before.");
		}
		else
		{
			for(Dish d : listDishes)
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
			System.err.println("sortDishesPrice with an empty listDishes. Call createDishes.");
			return;
		}

		Collections.sort(this.listDishes, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return (int) (o1.getPrice()-o2.getPrice());
			}

		});
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
			System.err.println("filterDishesType with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : this.listDishes)
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
			System.err.println("getDishesSubtype with an empty listDishes. Call createDishes.");
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
	
	/**
	 * @param subtype the subtype of the wanted Dishes
	 * @return an ArrayList of Dishes of the given subtype. If there is no such Dish or
	 * if the list of Dishes is empty, returns an empty ArrayList.
	 */
	public ArrayList<Dish> filterDishesSubtype(String subtype)
	{
		if (this.listDishes == null)
		{
			System.err.println("filterDishesSubtype with an empty listDishes. Call createDishes.");
		}
		
		ArrayList<Dish> result = new ArrayList<Dish>();
		for (Dish d : this.listDishes)
		{
			if (d.getSubtype().equals(subtype))
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
	
	
	/**
	 * Changes the rating of the restaurant given the current rate, the new rating
	 * and the number of people who have voted. Updates the DB.
	 * @param vote the new rating
	 * @param dbh the DBHandler used to interact with the DB
	 */
	public void rateRestaurant(float vote, DBHandler dbh)
	{
		this.rating *= this.nbrPrsHasVoted;
		this.nbrPrsHasVoted++;
		this.rating += vote;
		this.rating /= this.nbrPrsHasVoted;
		
		dbh.rateRestaurant(this.name, this.rating, this.nbrPrsHasVoted);
	}
	
	public boolean checkOrder(Order order)
	{
		for (Dish dish : order.getOrderDishes())
		{
			if (dish.getQuantity()>dish.getInventory()) return false;
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
	public boolean checkReservation(Reservation res){
		if(res.getOrder()!=null){
			for(Dish dish : res.getOrder().getOrderDishes()){
				if(dish.getQuantity()>dish.getInventory()) return false;
			}
		}
		//Check hour.
		boolean timeTableOk = false; 
		int reservationHour = res.getReservationTime().get(GregorianCalendar.HOUR_OF_DAY);
		int reservationMinute = res.getReservationTime().get(GregorianCalendar.MINUTE);
		for(TimeTable tt : semaine[res.getReservationTime().get(GregorianCalendar.DAY_OF_WEEK)-1]){
			if(tt.getOpenTime().get(GregorianCalendar.HOUR_OF_DAY)<=reservationHour
			&& tt.getOpenTime().get(GregorianCalendar.MINUTE)<=reservationMinute
			&& reservationHour <= tt.getClosingTime().get(GregorianCalendar.HOUR_OF_DAY)
			&& reservationMinute <= tt.getClosingTime().get(GregorianCalendar.MINUTE)){
				timeTableOk = true;
			}
		}
		return (timeTableOk && res.getReservationPeople()<=availableSeats);
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
	public short getZip() {
		return zip;
	}
	public void setZip(short zip) 
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
	public float getLatitude() 
	{
		return latitude;
	}
	public void setLatitude(float latitude) 
	{
		this.latitude = latitude;
	}
	public float getLongitude() 
	{
		return longitude;
	}
	public void setLongitude(float longitude) 
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
	public short getSeats() 
	{
		return seats;
	}
	public void setSeats(short seats) 
	{
		this.seats = seats;
	}
	public short getAvailableSeats() 
	{
		return availableSeats;
	}
	public void setAvailableSeats(short availableSeats) 
	{
		this.availableSeats = availableSeats;
	}
	public float getRating() 
	{
		return rating;
	}
	public void setRating(float rating) 
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
	public float getPriceCat() {
		return priceCat;
	}
	public void setPriceCat(float priceCat)
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
	public ArrayList<TimeTable>[] getSemaine() 
	{
		return semaine;
	}
	public void setSemaine(ArrayList<TimeTable>[] semaine) 
	{
		this.semaine = semaine;
	}
}
