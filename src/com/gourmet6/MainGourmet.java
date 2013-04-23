package com.gourmet6;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class MainGourmet extends Activity
{
	private String[] towns;
	private String currentTown;
	private String[] restaurants;
	private Location location = null;
	
	public Location getDeviceLocation()
	{
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener()
		{
			public void onLocationChanged(Location location)
			{
				// Called when a new location is found by the network location provider.
				MainGourmet.this.location = location;
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras)
		    {
		    	
		    }

		    public void onProviderEnabled(String provider)
		    {
		    	
		    }

		    public void onProviderDisabled(String provider)
		    {
		    	
		    }
		}
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		while (location == null)
		{
			
		}
		locationManager.removeUpdates(locationListener);
		return loc
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener()
		{
			public void onLocationChanged(Location location)
			{
				// Called when a new location is found by the network location provider.
				MainGourmet.this.location = location;
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras)
		    {
		    	
		    }

		    public void onProviderEnabled(String provider)
		    {
		    	
		    }

		    public void onProviderDisabled(String provider)
		    {
		    	
		    }
		}
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		//locationManager.removeUpdates(locationListener);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
