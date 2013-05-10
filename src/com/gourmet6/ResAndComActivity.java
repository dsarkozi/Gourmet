package com.gourmet6;

import java.util.ArrayList;

import com.gourmet6.DishMenuAdapter.Groupe;
import com.gourmet6.DishMenuAdapter.GroupeViewHolder;

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
	private ExpandableListView expandableList;
	private ArrayList<Reservation> myRes;
	private ArrayList<Order> myOr;
	private ResAndComAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_and_com);
		dbHand = new DBHandler(this);
		
		g = (Gourmet)getApplication();
		
		currentCli = g.getClient();
		myRes = dbHand.getClientReservations(currentCli.getEmail());
		myOr = dbHand.getClientOrders(currentCli.getEmail());
		expandableList = (ExpandableListView) findViewById(R.id.rescom);
		
		adapter = new ResAndComAdapter(this, myRes, myOr);
		expandableList.setAdapter(adapter);
		
		expandableList.setOnChildClickListener(onChildClickListener)
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
		private ChildViewHolder dholder;
		private ArrayList<Groupe> head;
		private ArrayList<Reservation> myResHere;
		private ArrayList<Order> myOrHere;
		
		public ResAndComAdapter(Context context, ArrayList<Reservation> myResHere, ArrayList<Order> myOrHere) //myResRac = myRes !!!
		{
			this.context = context;
			this.myResHere = myResHere;
			this.myOrHere = myOrHere;
			inflater = LayoutInflater.from(context);
			this.head = setSeed(myResHere, myOrHere);
		}

		@Override //on a une liste de reservations qui contient une liste de plats
		public Object getChild(int groupPosition, int childPosition) {
			if(groupPosition == 0){
				return head.get(groupPosition).getRes().get(childPosition);
			}else{
				return head.get(groupPosition).getOr().get(childPosition);
			}
		}

		@Override
		public long getChildId(int indexR, int indexO) {
			return indexO;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
			
			if(groupPosition == 0){
				Reservation resv = (Reservation) getChild(groupPosition, childPosition);
			}else{
				Order ord = (Order) getChild(groupPosition, childPosition);
			}
			
			if(convertView == null){
				dholder = new ChildViewHolder();
				convertView = inflater.inflate(R.layout.res_list, null);
				dholder.resto = (TextView) convertView.findViewById(R.id.dishname);
				dholder.date = (TextView) convertView.findViewById(R.id.price);
				
			}else{
				
			}
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			if(groupPosition == 0){
				return head.get(groupPosition).getRes().size();
			}else{
				return head.get(groupPosition).getOr().size();
			}
		}

		@Override
		public Object getGroup(int indexR) {
			return head.get(indexR);
		}

		@Override
		public int getGroupCount() {
			return head.size();
		}

		@Override
		public long getGroupId(int indexR) {
			return indexR;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			GroupViewHolder gholder;
			
			Groupe group = (Groupe) getGroup(groupPosition);
			
			if(convertView == null)
			{
				gholder = new GroupViewHolder();
				convertView = inflater.inflate(R.layout.grp_layout, null);
				gholder.tvRes = (TextView) convertView.findViewById(R.id.groupe);
				convertView.setTag(gholder);
			}
			else{
				gholder = (GroupViewHolder) convertView.getTag();
			}
			
			gholder.tvRes.setText(group.getName());
			
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
		
		public ArrayList<Groupe> setSeed(ArrayList<Reservation> resv, ArrayList<Order> or){
			ArrayList<Groupe> res = new ArrayList<Groupe>();
			res.add(new Groupe("Bookings", resv, null));
			res.add(new Groupe("Bookings", null, or));
			return res;
		}
		
		private class ChildViewHolder{
			public TextView resto;
			public TextView date;
		}
		
		private class GroupViewHolder{
			public TextView tvRes;
			
		}
		
		private class Groupe{
			private String name;
			private ArrayList<Reservation> res;
			private ArrayList<Order> or;
			
			public Groupe(String name,ArrayList<Reservation> res,ArrayList<Order> or){
				this.setName(name);
				this.setRes(res);
				this.setOr(or);
			}
			
			public String getName() {
				return name;
			}
			
			public void setName(String name) {
				this.name = name;
			}

			public ArrayList<Reservation> getRes() {
				return res;
			}

			public void setRes(ArrayList<Reservation> res) {
				this.res = res;
			}

			public ArrayList<Order> getOr() {
				return or;
			}

			public void setOr(ArrayList<Order> or) {
				this.or = or;
			}

		}
	}
}


