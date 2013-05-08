package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DishMenuActivity extends Activity {
	
	private Gourmet g = (Gourmet)getApplication();
	private Restaurant current  = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_menu);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setTitle(R.string.activity_menu_title);
		
		current = g.getRest();
		//TODO create listDishes in current if don't exist
		
		//Reaction du bouton de commande
		Button order = (Button) findViewById(R.id.button1);
		order.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				//TODO 
				Intent commande = new Intent(DishMenuActivity.this, OrderActivity.class);
				commande.putExtra("from", true);
				startActivity(commande);			
			}
		});	
		
		ExpandableListView dishes = (ExpandableListView) findViewById(R.id.expandableListView1);
		dishes.setAdapter(new MenuAdapter(this, current, false));
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dish_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
