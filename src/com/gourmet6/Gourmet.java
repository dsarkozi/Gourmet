package com.gourmet6;

import android.app.Application;
import android.location.Location;

public class Gourmet extends Application
{
	private Client currentcli = null;
	private Restaurant currentres = null;
	private Order currentorder = null;
	private Reservation currentreserv = null;
	private Location currentlocation = null;

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	public Client getClient()
	{
		return currentcli;
	}

	public void setClient(Client currentcli)
	{
		this.currentcli = currentcli;
	}

	public Restaurant getRest()
	{
		return currentres;
	}

	public void setRest(Restaurant currentres)
	{
		this.currentres = currentres;
	}

	public Order getOrder()
	{
		return currentorder;
	}

	public void setOrder(Order currentorder)
	{
		this.currentorder = currentorder;
	}

	public Reservation getReservation()
	{
		return currentreserv;
	}

	public void setReservation(Reservation currentreserv)
	{
		this.currentreserv = currentreserv;
	}

	public Location getLocation()
	{
		return currentlocation;
	}

	public void setLocation(Location currentlocation)
	{
		this.currentlocation = currentlocation;
	}

}
