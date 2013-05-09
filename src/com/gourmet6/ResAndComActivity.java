package com.gourmet6;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class ResAndComActivity extends Activity {

	private DBHandler dbHand;
	private Gourmet g ;
	private Client currentCli;
	private ExpandableListView expandableList = null;
	private ArrayList<Reservation> myRes = new ArrayList<Reservation>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_and_com);
		dbHand = new DBHandler(this);
		
		g = (Gourmet)getApplication();
		
		currentCli = g.getClient();
		myRes = dbHand.getClientReservations(currentCli.getEmail());
		expandableList = (ExpandableListView) findViewById(R.id.expandableListView1);
		
		ResAndComAdapter adapter = new ResAndComAdapter(this, myRes);
		expandableList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.res_and_com, menu);
		return true;
	}
	
	
	public class ResAndComAdapter extends BaseExpandableListAdapter{
		
		private Context context;
		private LayoutInflater inflater;
		private ArrayList<Reservation> myResHere;
		
		public ResAndComAdapter(Context context, ArrayList<Reservation> myResHere) //myResRac = myRes !!!
		{
			this.context = context;
			this.myResHere = myResHere;
			inflater = LayoutInflater.from(context);
		}

		@Override //on a une liste de r�servations qui contient une liste de plats
		public Object getChild(int indexR, int indexO) {
			return myResHere.get(indexR).getReservationOrder().getOrderDishes().get(indexO);
		}

		@Override
		public long getChildId(int indexR, int indexO) {
			return indexO;
		}

		@Override
		public View getChildView(int indexR, int indexO, boolean isLastChild, View convertView, ViewGroup parent) {
			DishViewHolder dholder;
			
			final Dish dish = (Dish) this.getChild(indexR, indexO);
			
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.dish_list, null);
				
				dholder.name = (TextView) convertView.findViewById(R.id.dishName);
				dholder.price = (TextView) convertView.findViewById(R.id.dishPrice);
				dholder.count = (TextView) convertView.findViewById(R.id.dishCount);
				dholder.type = (TextView) convertView.findViewById(R.id.dishType);
				dholder.subtype = (TextView) convertView.findViewById(R.id.dishSubtype);
				
				convertView.setTag(dholder);
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
			dholder.name.setText(dish.getName());
			dholder.price.setText(String.valueOf(dish.getPrice()));
			dholder.count.setText(dish.getQuantity());
			dholder.type.setText(dish.getType());
			dholder.subtype.setText(dish.getSubtype());
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int indexR) {
			return myResHere.get(indexR).getReservationOrder().getOrderDishes().size();
		}

		@Override
		public Object getGroup(int indexR) {
			return myResHere.get(indexR);
		}

		@Override
		public int getGroupCount() {
			return myResHere.size();
		}

		@Override
		public long getGroupId(int indexR) {
			return indexR;
		}

		@Override
		public View getGroupView(int indexR, boolean indexO, View convertView, ViewGroup parent) {
			ResViewHolder rholder;
			
			Reservation res = (Reservation) getGroup(indexR);
			
			if(convertView == null){
				rholder = new ResViewHolder();
				
				convertView = inflater.inflate(R.layout.res_list, null);
				
				rholder.tvRes = (TextView)convertView.findViewById(R.id.resData);
				
				convertView.setTag(rholder);
			}
			else{
				rholder = (ResViewHolder)convertView.getTag();
			}
			rholder.tvRes.setText(res.getReservationTime()+" "+res.getReservationPeople()+" "+res.getReservationResName());
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}
		
		
		
		private class DishViewHolder{
			public TextView name;
			public TextView price;
			public TextView count;
			public TextView type;
			public TextView subtype;
		}
		
		private class ResViewHolder{
			public TextView tvRes;
			
		}
	}
}


