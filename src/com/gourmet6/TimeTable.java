package com.gourmet6;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;

public class TimeTable {
	
	private GregorianCalendar openTime;
	private GregorianCalendar closingTime;
	
	public TimeTable(GregorianCalendar openTime, GregorianCalendar closingTime)
	{
		this.openTime = openTime;
		this.closingTime = closingTime;
	}
	
	public TimeTable(String openTime, String closingTime)
	{
		this.openTime = parseDate(openTime);
		this.closingTime = parseDate(closingTime);
	}
	
	/***********************
	 * Formatting functions
	 ***********************/
	/**
	 * Formats a String containing a date and/or time into a GregorianCalendar object representing the date.
	 * @param date the date to parse, may be one of the following 3 formats :
	 * 			dd/MM/yyyy hh:mm
	 * 			dd-MM-yyyy hh:mm
	 * 			hh:mm  
	 * @return a GregorianCalendar representing the date originally contained in the String.
	 * 		   If the format is not respected, returns null.
	 */
	@SuppressLint("SimpleDateFormat")
	public static GregorianCalendar parseDate(String date){
		SimpleDateFormat ourFormat;
		TimeZone timezone = TimeZone.getDefault();
		GregorianCalendar cal = null;
		if (date.contains("/") && date.contains(":"))
		{
			ourFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		}
		else if (date.contains("-") && date.contains(":"))
		{
			ourFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		}
		else if (date.contains(":") && (!date.contains("/") || !date.contains("-")))
		{
			ourFormat = new SimpleDateFormat("hh:mm");
		}
		else
		{
			System.err.println("Date's format unknow");
			return cal;
		}
		try
		{
			cal = new GregorianCalendar();
			cal.setTime(ourFormat.parse(date));
			cal.setTimeZone(timezone);
		}
		catch (ParseException e)
		{
			System.err.println("Error encoding the date : "+e);
			e.printStackTrace();
		}
		return cal;
	}
	
	/**********************
	 * Getters and setters
	 **********************/
	public GregorianCalendar getOpenTime()
	{
		return this.openTime;
	}
	public void setOpenTime(GregorianCalendar openTime)
	{
		this.openTime = openTime;
	}
	public void setOpenTime(String openTime)
	{
		this.openTime = parseDate(openTime);
	}
	public GregorianCalendar getClosingTime()
	{
		return this.closingTime;
	}
	public void setClosingTime(GregorianCalendar closingTime)
	{
		this.closingTime = closingTime;
	}
	public void setClosingTime(String closingTime)
	{
		this.closingTime = parseDate(closingTime);
	}
}
