package com.gourmet6;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class OrderActivity extends Activity {
	
	private Gourmet g;
	private Restaurant current = null;
	private boolean fromRestaurant = false;
	private ExpandableListView dishes;
	private DishMenuAdapter dishad;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> types;
	private ArrayList<String> subtypes;
	private ArrayList<String> allergens;
	private ArrayList<String> filters;
	private ArrayList<Dish> listdish;

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
		
		updateLists(current.getListDishes());
		
		dishes = (ExpandableListView) findViewById(R.id.dish_menu);
		dishad = new DishMenuAdapter(this,current,listdish, true);
		dishes.setAdapter(dishad);
		dishes.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
				
				TextView txt = (TextView)v.findViewById(R.id.dishname);
				
				if(dishad.isChildSelectable(groupPosition, childPosition)){
					Intent display = new Intent(OrderActivity.this, DishDisplayActivity.class);
					display.putExtra("the_dish", txt.getText().toString());
					startActivity(display);
				}
				return true;
			}
		});
		
		Spinner sort = (Spinner) findViewById(R.id.spinner1);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, filters);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sort.setAdapter(adapter);
		sort.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				String str =(String)arg0.getSelectedItem();
				if(str == "All"){
					
					updateLists(current.getListDishes());
					dishes.setAdapter(new DishMenuAdapter(OrderActivity.this,current,listdish, true));
					
				}
				else if(str == "Price"){
					updateLists(current.sortDishesPrice(listdish));
					dishes.setAdapter(new DishMenuAdapter(OrderActivity.this,current,listdish, true));
				}
				else if(types.contains(str)){
					
					updateLists(current.filterDishesType(str, listdish));
					dishes.setAdapter(new DishMenuAdapter(OrderActivity.this,current,listdish, true));
				}
				else if(subtypes.contains(str)){
					
					updateLists(current.filterDishesSubtype(str, listdish));
					dishes.setAdapter(new DishMenuAdapter(OrderActivity.this,current,listdish, true));
				}
				else if(allergens.contains(str)){
					
					updateLists(current.filterDishesAllergen(str, listdish));
					dishes.setAdapter(new DishMenuAdapter(OrderActivity.this,current,listdish, true));
				}
				else{
					Toast.makeText(OrderActivity.this,"Current list does not contain any dish from "+str, Toast.LENGTH_LONG) .show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}	
		});
	}
	
	private void updateLists(ArrayList<Dish> filtered){
		this.listdish = filtered;
		this.filters = current.getFilters(filtered);
		this.types = current.getDishesTypes(filtered);
		this.subtypes = current.getDishesSubtypes(filtered);
		this.allergens = current.getAllergensForFilter(filtered);
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
