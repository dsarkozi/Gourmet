package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ReservationActivity extends Activity {
	
	private int people = 0;
	private int hour = 0;
	private int date = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//récupération des valeurs entrées par le client
		EditText seats = (EditText) findViewById(R.id.editText1);
		this.people = Integer.parseInt(seats.getText().toString());
		
		EditText time = (EditText) findViewById(R.id.editText2);
		
		EditText date = (EditText) findViewById(R.id.editText3);
		
		//Reaction du bouton de commande
		Button order = (Button) findViewById(R.id.button1);
		order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO create the reservation object
				Intent commande = new Intent(ReservationActivity.this, OrderActivity.class);
				startActivity(commande);			
			}
		});	
		
		//Reaction du bouton de soumission
		Button submit = (Button) findViewById(R.id.button2);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO checker la réservation
				
			}
		});
		
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
		getMenuInflater().inflate(R.menu.reservation, menu);
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
