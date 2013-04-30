package com.gourmet6;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 
 * @author Quentin
 *
 */

public class Restaurant {
	
	private String name;
	private String adress;
	private String town;
	private String tel;
	private String Description;
	private ArrayList<String> cuisines;
	private ArrayList<String> chain;
	private ArrayList<String> photos;
	private byte rating;
	private short zip;
	private short seats;
	private short availableSeats; // en temps reel
	private int nbrPrsHasVoted;
	private float latitude;
	private float longitude;
	private float priceCat; // Si un restaurant est cher -> moyenne des prix
	private ArrayList<Dish> listDishes;
	/**
	 * Semaine contient 7 cases, une par jour de la semaine. A chaque jour correspond une list de plage horraire.
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
	
	public Restaurant(String restaurant)
	{
		//TODO DB query based on the restaurant's name
		
	}
	
	public Restaurant(String name, String adress, String town, String tel, short zip, short seats, 
			short availableSeats, float latitude, float longitude, ArrayList<Dish> listDishes, 
			ArrayList<TimeTable>[] semaine){
		this.name = name;
		this.adress = adress;
		this.town = town;
		this.tel = tel;
		this.zip = zip;
		this.seats = seats;
		this.availableSeats = availableSeats;
		this.latitude = latitude;
		this.longitude = longitude;
		this.listDishes = listDishes;
		this.semaine = semaine;
	}
	
	/**
	 * 
	 * @param date
	 * @return Cette fonction transforme un String date en une date GregorianCalendar.
	 * Cette fonction supporte 3 formats :
	 * dd/MM/yyyy hh:mm
	 * dd-MM-yyyy hh:mm
	 * hh:mm
	 * Si ces formats ne sont pas respectes, ou qu'elle ne reussit pas la conversion, elle renvoit null.
	 */
	@SuppressLint("SimpleDateFormat")
	public GregorianCalendar parseDate(String date){
		SimpleDateFormat ourFormat;
		TimeZone timezone = TimeZone.getDefault();
		GregorianCalendar cal = null;
		if(date.contains("/") && date.contains("-")){
			ourFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		}
		else if(date.contains("-") && date.contains(":")){
			ourFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		}
		else if(date.contains(":") && (!date.contains("/") || !date.contains("-"))){
			ourFormat = new SimpleDateFormat("hh:mm");
		}
		else return cal;
		try{
			cal = new GregorianCalendar();
			cal.setTime(ourFormat.parse(date));
			cal.setTimeZone(timezone);
		}
		catch (ParseException e){
			System.out.println("Error encoding the date : "+e);
			e.printStackTrace();
		}
		return cal;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public ArrayList<String> getCuisines() {
		return cuisines;
	}
	public void setCuisines(ArrayList<String> cuisines) {
		this.cuisines = cuisines;
	}
	public ArrayList<String> getChain() {
		return chain;
	}
	public void setChain(ArrayList<String> chain) {
		this.chain = chain;
	}
	public ArrayList<String> getPhotos() {
		return photos;
	}
	public void setPhotos(ArrayList<String> photos) {
		this.photos = photos;
	}
	public byte getRating() {
		return rating;
	}
	public void setRating(byte rating) {
		this.rating = rating;
	}
	public short getZip() {
		return zip;
	}
	public void setZip(short zip) {
		this.zip = zip;
	}
	public short getSeats() {
		return seats;
	}
	public void setSeats(short seats) {
		this.seats = seats;
	}
	public short getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(short availableSeats) {
		this.availableSeats = availableSeats;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getPriceCat() {
		return priceCat;
	}
	public void setPriceCat(float priceCat) {
		this.priceCat = priceCat;
	}
	public ArrayList<Dish> getListDishes() {
		return listDishes;
	}
	public void setListDishes(ArrayList<Dish> listDishes) {
		this.listDishes = listDishes;
	}
	public ArrayList<TimeTable>[] getSemaine() {
		return semaine;
	}
	public void setSemaine(ArrayList<TimeTable>[] semaine) {
		this.semaine = semaine;
	}


	public void createListDishes(){
		//TODO
	}
	
	/**
	 * 
	 * @param res
	 * @return cette methode renvoit true si la reservation est correcte, c-a-d:
	 * -Qu'elle specifie des plats qui sont encore disponibles, si une commande est joind à la reservation
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
		//Check heure.
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
	
	public boolean checkOrder(Order order){
		for(Dish dish : order.getOrderDishes()){
			if(dish.getQuantity()>dish.getInventory()) return false;
		}
		return true;
	}
	
	public void sortDishes(String s){
		//TODO
	}
	
	public void rateRestaurant(byte vote){
		rating *=nbrPrsHasVoted;
		nbrPrsHasVoted++;
		rating += vote;
		rating /=nbrPrsHasVoted;
	}
}
