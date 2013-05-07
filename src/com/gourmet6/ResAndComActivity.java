package com.gourmet6;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class ResAndComActivity extends Activity {

	private Gourmet g = (Gourmet)getApplication();
	private Client currentCli;
	private ExpandableListView expandableList = null;
	private ArrayList<Reservation> myRes = new ArrayList<Reservation>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_and_com);
		
		currentCli = g.getClient();
		
		//myRes = ;  utiliser m�thode Lena permettant de r�cup�rer les r�sevations d'un Client depuis la DB
		
		/*
		 * expandableList = (ExpandableListView) findViewById(R.id.expandableListView1);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		
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
		
		public ResAndComAdapter(Context context, ArrayList<Reservation> myResRAC) //myResRac = myRes !!!
		{
			this.context = context;
			myResHere = myResRAC;
			inflater = LayoutInflater.from(context);
		}

		@Override //on a une liste de r�servations qui contient une liste de plats
		public Object getChild(int indexR, int indexO) {
			return myRes.get(indexR).getOrder().getOrderDishes().get(indexO);
		}

		@Override
		public long getChildId(int indexR, int indexO) {
			return indexO;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			DishViewHolder dholder;
			
			Dish dish = (Dish) this.getChild(groupPosition, childPosition);
			
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.dish_list_element, null);
				
				dholder.name = (TextView) convertView.findViewById(R.id.dishname);
				dholder.price = (TextView) convertView.findViewById(R.id.textView1);
				dholder.count = (TextView) convertView.findViewById(R.id.textView2);
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
			
			
			dholder.name.setText(dish.getName());
			//dholder.price.setText(String.valueOf(currentdish.getPrice()));
			
			return convertView;
		}
		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		
		private class DishViewHolder{
			public TextView name;
			public TextView price;
			public TextView count;
		}
	}
}


