package com.gourmet6;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;

public class TimeTable {
	
	private GregorianCalendar openTime;
	private GregorianCalendar closingTime;
	
	public TimeTable(GregorianCalendar openTime, GregorianCalendar closingTime){
		this.openTime = openTime;
		this.closingTime = closingTime;
	}
	
	public TimeTable(String openTime, String closingTime){
		this.openTime = parseHour(openTime);
		this.closingTime = parseHour(closingTime);
	}
	
	/**
	 * 
	 * @param date
	 * @return Cette fonction transforme un String jour en une hour GregorianCalendar.
	 * Cette fonction supporte 1 format :
	 * hh:mm
	 * Si ces formats ne sont pas respectes, ou qu'elle ne reussit pas la conversion, elle renvoit null.
	 */
	@SuppressLint("SimpleDateFormat")
	public GregorianCalendar parseHour(String hour){
		SimpleDateFormat ourFormat;
		TimeZone timezone = TimeZone.getDefault();
		GregorianCalendar cal = null;
		if(hour.contains(":") && (!hour.contains("/") || !hour.contains("-"))){
			ourFormat = new SimpleDateFormat("hh:mm");
		}
		else return cal;
		try{
			cal = new GregorianCalendar();
			cal.setTime(ourFormat.parse(hour));
			cal.setTimeZone(timezone);
		}
		catch (ParseException e){
			System.out.println("Error encoding the date : "+e);
			e.printStackTrace();
		}
		return cal;
	}
	
	public GregorianCalendar getOpenTime() {
		return this.openTime;
	}
	public void setOpenTime(GregorianCalendar openTime) {
		this.openTime = openTime;
	}
	public void setOpenTime(String openTime){
		this.openTime = parseHour(openTime);
	}
	public GregorianCalendar getClosingTime() {
		return this.closingTime;
	}
	public void setClosingTime(GregorianCalendar closingTime) {
		this.closingTime = closingTime;
	}
	public void setClosingTime(String closingTime){
		this.closingTime = parseHour(closingTime);
	}

}
