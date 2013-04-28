package com.gourmet6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainGourmet extends Activity
{
	private String[] towns;
	private String currentTown;
	private String[] restaurants;
	private Location location = null;
	

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
		};
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		//locationManager.removeUpdates(locationListener);
		setContentView(R.layout.activity_main);

		findViewById(R.id.no_button_connection).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						showTowns();
					}
				});
		
		findViewById(R.id.yes_button_connection).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						setContentView(R.layout.activity_login);
					}
				});
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
		ListView townList = (ListView)findViewById(R.id.list);
		ArrayAdapter<String> townAdapter = new ArrayAdapter<String>(
				this,android.R.layout.simple_list_item_1,towns);
		townList.setAdapter(townAdapter);
		townList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Button currentButton = (Button) parent.getItemAtPosition(position);
				currentTown = currentButton.getText().toString();
			}
		});
		
	}



	public class ButtonArrayAdapter extends ArrayAdapter<String>
	{

		public ButtonArrayAdapter(Context context, int textViewResourceId,
				String[] objects)
		{
			super(context, textViewResourceId, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if(convertView == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.lists, parent, false);
			}
			Button button = (Button)convertView.findViewById(R.id.button_list);
			button.setText(this.getItem(position));
			return button;
		}
	}
}