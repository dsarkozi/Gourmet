package com.gourmet6;

import java.util.ArrayList;

public class Restaurant {
	
	private String name;
	private String adress;
	private String town;
	private String tel;
	private String Description;
	private String cuisines;
	private ArrayList<String> chain;
	private ArrayList<String> photos;
	private byte rating;
	private short zip;
	private short seats;
	private short availableSeats; //en temps rèelle.
	private float latitude;
	private float longitude;
	private float priceCat; //Si un restaurant est cher -> moyenne des prix
	private ArrayList<Dish> listDishes;
	
	public Restaurant(){
		
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
	public String getCuisines() {
		return cuisines;
	}
	public void setCuisines(String cuisines) {
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
	
	public String checkReservation(){
		//TODO
		return null;
	}
	
	public String checkOrder(){
		//TODO
		return null;
	}
	
	public void sortDishes(String s){
		//TODO
	}
}
