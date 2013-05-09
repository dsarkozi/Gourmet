package com.gourmet6;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class OrderActivity extends Activity {
	
	private Gourmet g;
	private Restaurant current = null;
	private boolean fromRestaurant = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		// Show the Up button in the action bar.
		setupActionBar();

		setTitle(R.string.activity_order_title);		
		
		g = (Gourmet)getApplication();
		current = g.getRest();
		
		Bundle extra = getIntent().getExtras();
		this.fromRestaurant = extra.getBoolean("from");
		
		ExpandableListView dishes = (ExpandableListView) findViewById(R.id.expandableListView1);
		dishes.setAdapter(new DishMenuAdapter(this,current, current.getListDishes(), true));
		
		Button submit = (Button) findViewById(R.id.button1);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO check order
				if(fromRestaurant){
					AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
					builder.setTitle(R.string.activity_reservation_title);
					builder.setMessage(R.string.do_you_res);
					
					builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO add a reservation
							Intent resv = new Intent(OrderActivity.this,ReservationActivity.class);
							startActivity(resv);
						}
					});
					builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO quit
							
						}
					});
					
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
		
	}
	
	private ArrayList<Dish> ordered()
	{
		ArrayList<Dish> res = new ArrayList<Dish>();
		
		for(Dish d : current.getListDishes()){
			if(d.getQuantity() != 0){
				res.add(d);
			}
		}
		
		return res;
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
		getMenuInflater().inflate(R.menu.order, menu);
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
