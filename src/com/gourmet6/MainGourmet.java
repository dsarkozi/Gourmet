package com.gourmet6;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainGourmet extends Activity
{
	@SuppressWarnings("unused")
	private String[] towns;
	@SuppressWarnings("unused")
	private String currentTown;
	private String[] restaurants;
	@SuppressWarnings("unused")
	private Location location = null;
	public DBHandler dbHand;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
		{
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex)
			{
				Log.e("MainGourmet", Log.getStackTraceString(ex), ex);
				ExceptionHandler.kill();
				/*runOnUiThread(new Runnable()
				{
					
					@Override
					public void run()
					{
				        ExceptionHandler.showDialog(MainGourmet.this, false, stackTrace.toString());
					}
				});*/
			}
		});

		/** LOCATION MANAGEMENT **/
		/*
		 * LocationManager locationManager = (LocationManager)
		 * this.getSystemService(Context.LOCATION_SERVICE); LocationListener
		 * locationListener = new LocationListener() { public void
		 * onLocationChanged(Location location) { // Called when a new location
		 * is found by the network location provider. MainGourmet.this.location
		 * = location; }
		 * 
		 * public void onStatusChanged(String provider, int status, Bundle
		 * extras) {
		 * 
		 * }
		 * 
		 * public void onProviderEnabled(String provider) {
		 * 
		 * }
		 * 
		 * public void onProviderDisabled(String provider) {
		 * 
		 * } };
		 * locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
		 * , 0, 0, locationListener);
		 * //locationManager.removeUpdates(locationListener);
		 */
		dbHand = new DBHandler(this);

	}

	@Override
	protected void onStart()
	{
		super.onStart();
		setContentView(R.layout.activity_main);

		findViewById(R.id.no_button_connection).setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						showTowns();
					}
				});

		findViewById(R.id.yes_button_connection).setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						login(view);
						//showTowns();
					}
				});
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void login(View view)
	{
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	public void showTowns()
	{
		startActivity(new Intent(this, TownActivity.class));
	}

	public void showRestaurants() // TODO SimpleAdapter -- junk
	{
		setContentView(R.layout.lists);
		ListView restoList = (ListView) findViewById(R.id.list);
		ArrayAdapter<String> restoAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, restaurants);
		restoList.setAdapter(restoAdapter);
		restoList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Button buttonClicked = (Button) parent
						.getItemAtPosition(position);
				makeRestaurant(buttonClicked.getText().toString());
			}
		});
	}

	// pas dans Restaurant, mais dans DBHandler
	public Restaurant makeRestaurant(String restaurant)
	{
		return new Restaurant(restaurant);
	}
}