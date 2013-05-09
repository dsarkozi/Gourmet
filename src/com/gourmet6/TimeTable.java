package com.gourmet6;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;

public class TimeTable 
{
	
	private String jourDebut;
	private String jourFin;
	private GregorianCalendar openTime;
	private GregorianCalendar closingTime;
	
	public TimeTable(String jourDebut, String jourFin, GregorianCalendar openTime, GregorianCalendar closingTime)
	{
		this.setJourDebut(jourDebut);
		this.setJourFin(jourFin);
		this.openTime = openTime;
		this.closingTime = closingTime;
	}
	
	public TimeTable(String jourDebut, String jourFin, String openTime, String closingTime)
	{
		this.setJourDebut(jourDebut);
		this.setJourFin(jourFin);
		this.openTime = parseDate(openTime);
		this.closingTime = parseDate(closingTime);
	}
	
    static public HashMap<String,Integer> weekMap = new HashMap<String,Integer>(7);
    static {
    	weekMap.put("dimanche", 1);
    	weekMap.put("lundi", 	2);
    	weekMap.put("mardi", 	3);
    	weekMap.put("mercredi", 4);
    	weekMap.put("jeudi", 	5);
    	weekMap.put("vendredi", 6);
    	weekMap.put("samedi", 	7);
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
			Log.e("Timetable", "Date's format unknow");
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
			Log.e("Timetable", "Error encoding the date : "+e);
			e.printStackTrace();
		}
		return cal;
	}
	
	public static String parseDateInString(GregorianCalendar temp)
	{
		String year = temp.get(GregorianCalendar.YEAR)+"-"; String month;
		if((temp.get(GregorianCalendar.MONTH)+1)<10){
			month = "0"+(temp.get(GregorianCalendar.MONTH)+1)+"-";
		}
		else {
			month = (temp.get(GregorianCalendar.MONTH)+1)+"-";
		} String day;
		if(temp.get(GregorianCalendar.DAY_OF_MONTH)<10){
			day = "0"+temp.get(GregorianCalendar.DAY_OF_MONTH)+" ";
		}
		else{
			day = temp.get(GregorianCalendar.DAY_OF_MONTH)+" ";
		}String hour;
		if(temp.get(GregorianCalendar.HOUR_OF_DAY)<10){
			hour = "0"+temp.get(GregorianCalendar.HOUR_OF_DAY)+":";
		}
		else{
			hour = temp.get(GregorianCalendar.HOUR_OF_DAY)+":";
		} String minute;
		if(temp.get(GregorianCalendar.MINUTE)<10){
			minute = "0"+temp.get(GregorianCalendar.MINUTE);
		}
		else{
			minute = temp.get(GregorianCalendar.MINUTE)+"";
		}

		return year+month+day+hour+minute;
	}//
	
	public boolean isInTimeTable(GregorianCalendar date)
	{
		int day = date.get(GregorianCalendar.DAY_OF_WEEK);
		int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = date.get(GregorianCalendar.MINUTE);
		
		return (Integer)weekMap.get(this.getJourDebut())<=day
				&& day<=(Integer)weekMap.get(this.getJourFin())
				&& this.getOpenTime().get(GregorianCalendar.HOUR_OF_DAY)<=hour
				&& this.getOpenTime().get(GregorianCalendar.MINUTE)<=minute
				&& hour<=this.getClosingTime().get(GregorianCalendar.HOUR_OF_DAY)
				&& minute<=this.getClosingTime().get(GregorianCalendar.MINUTE);
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
	public String getJourDebut()
	{
		return jourDebut;
	}
	public void setJourDebut(String jourDebut) 
	{
		this.jourDebut = jourDebut;
	}

	public String getJourFin()
	{
		return jourFin;
	}
	public void setJourFin(String jourFin)
	{
		this.jourFin = jourFin;
	}
}
