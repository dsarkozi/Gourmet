/**
 * 
 */
package com.gourmet6;

import java.sql.Timestamp;

/**
 * @author Lena
 *
 */
public class Order {
	
	private int number;
	private Timestamp time;
	private int people;
	
	public int getOrderNumber() {
		return number;
	}

	public void setOrderNumber(int number) {
		this.number = number;
	}

	public Timestamp getOrderTime() {
		return time;
	}

	public void setOrderTime(Timestamp time) {
		this.time = time;
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
