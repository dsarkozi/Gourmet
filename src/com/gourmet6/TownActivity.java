package com.gourmet6;

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

public class TownActivity extends ListActivity
{
	private static final String TITLE = "Town list";
	private DBHandler dbHand;
	private String[] towns;
	private Gourmet g;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_town);
		dbHand = new DBHandler(this);
		// Show the Up button in the action bar.
		setupActionBar();
		setTitle(TITLE);
		g = (Gourmet) getApplication();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		try
		{
			towns = dbHand.getTowns();
		}
		catch (SQLiteException e)
		{
			ExceptionHandler.caughtException(this, e);
		}
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, 
				towns);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		TextView selection = (TextView)v;
		Intent returnIntent = new Intent();
		returnIntent.putExtra("selection", selection.getText().toString());
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(g.getClient() != null)
			getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent clientGo = new Intent(TownActivity.this, ClientActivity.class);
		startActivity(clientGo);
		return super.onOptionsItemSelected(item);
	}

}
