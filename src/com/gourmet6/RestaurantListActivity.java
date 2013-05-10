package com.gourmet6;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RestaurantListActivity extends ListActivity
{
	private static final String TITLE = "Restaurant list";
	private DBHandler dbHand;
	private ArrayList<String> restaurants;
	private ArrayList<String> resNames;
	private Gourmet g;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_town);
		dbHand = new DBHandler(this);
		// Show the Up button in the action bar.
		setupActionBar();
		resNames = new ArrayList<String>();
		setTitle(TITLE);
		g = (Gourmet)getApplication();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Intent intent = getIntent();
		String currentTown = intent.getStringExtra("currentTown");
		try
		{
			restaurants = dbHand.getAllResNames(currentTown);
		}
		catch (SQLiteException e)
		{
			ExceptionHandler.caughtException(this, e);
		}
		for (String resto : restaurants)
		{
			String[] restos = resto.split("_");
			resNames.add(restos[0]);
		}
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, 
				resNames);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		TextView selection = (TextView)v;
		Intent returnIntent = new Intent();
		String resName = selection.getText().toString();
		for (String name : restaurants)
		{
			if (name.contains(resName))
			{
				resName = name;
				break;
			}
		}
		returnIntent.putExtra("selection", resName);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(g.getClient() != null)
			getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent clientGo = new Intent(RestaurantListActivity.this, ClientActivity.class);
		startActivity(clientGo);
		return super.onOptionsItemSelected(item);
	}
	*/

}
