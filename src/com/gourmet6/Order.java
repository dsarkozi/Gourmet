/**
 * 
 */
package com.gourmet6;

import java.util.ArrayList;

/**
 * @author Lena
 *
 */
public class Order {
	
	private int number;
	private int people;
	private ArrayList<Dish> dishes;
	private float price;
	
	public int getOrderNumber() {
		return number;
	}

	public void setOrderNumber(int number) {
		this.number = number;
	}

	public int getOrderPeople() {
		return people;
	}

	public void setOrderPeople(int people) {
		this.people = people;
	}

	public void addOrder() {

	}

}
