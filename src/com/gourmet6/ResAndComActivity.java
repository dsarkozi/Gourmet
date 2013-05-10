package com.gourmet6;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
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
	private ResAndComAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_and_com);
		dbHand = new DBHandler(this);
		
		g = (Gourmet)getApplication();
		
		currentCli = g.getClient();
		try{
			myRes = dbHand.getClientReservations(currentCli.getEmail());
		}catch (SQLiteException e) {
			ExceptionHandler.caughtException(this, e);
		}
		
		expandableList = (ExpandableListView) findViewById(R.id.rescom);		
		adapter = new ResAndComAdapter(this, myRes);
		expandableList.setAdapter(adapter);
		
		//expandableList.setOnChildClickListener(onChildClickListener)
		
		
	}
	
	private void viewerDialog(Reservation res){
		
		int people = res.getReservationPeople();
		Order order = res.getReservationOrder();
		 // affiche quantité + nom d'un dish
		String dishesS;
		if(!order.equals(null)){
			ArrayList<Dish> dishes = order.getOrderDishes();
			if(!dishes.equals(null)){
				dishesS = "Dishes reserved :\n"+dishes.get(0).getQuantity()+" * "+dishes.get(0).getName()+"\n";
				for(int i = 1;i<dishes.size();i++){
					dishesS = dishesS + dishes.get(i).getQuantity()+" * "+dishes.get(i).getName()+"\n";
				}
			}
			dishesS="No dishes reserved.";
		}
		else dishesS = "No order in this reservation.";
		
		AlertDialog.Builder builder = new AlertDialog.Builder(ResAndComActivity.this);
		builder.setTitle(R.string.title_activity_res_and_com);
		builder.setMessage("Number of people : "+people+"\n"+dishesS); 
		
		builder.setNeutralButton(R.string.ok_button, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public class ResAndComAdapter extends BaseExpandableListAdapter{
		
		private Context context;
		private LayoutInflater inflater;
		private ArrayList<Groupe> head;
		
		public ResAndComAdapter(Context context, ArrayList<Reservation> myResHere) 
		{
			this.context = context;
			inflater = LayoutInflater.from(context);
			this.head = setSeed(myResHere);
		}

		@Override //on a une liste de reservations qui contient une liste de plats
		public Object getChild(int groupPosition, int childPosition) {
			return head.get(groupPosition).getRes().get(childPosition);
		}

		@Override
		public long getChildId(int indexR, int indexO) {
			return indexO;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
			
			ViewHolder dholder;
			
			Reservation resv = (Reservation) getChild(groupPosition, childPosition);

			if(convertView == null){
				dholder = new ViewHolder();
				convertView = inflater.inflate(R.layout.child_layout, null);
				dholder.data = (TextView) convertView.findViewById(R.id.child);
				convertView.setTag(dholder);
				
			}else{
				dholder = (ViewHolder) convertView.getTag();
			}
			
			dholder.data.setText(TimeTable.parseDateInString(resv.getReservationTime()));
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return head.get(groupPosition).getRes().size();
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
			ViewHolder gholder;
			
			Groupe group = (Groupe) getGroup(groupPosition);
			
			if(convertView == null)
			{
				gholder = new ViewHolder();
				convertView = inflater.inflate(R.layout.grp_layout, null);
				gholder.data = (TextView) convertView.findViewById(R.id.groupe);
				convertView.setTag(gholder);
			}
			else{
				gholder = (ViewHolder) convertView.getTag();
			}
			
			gholder.data.setText(group.getName());
			
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
		
		public ArrayList<Groupe> setSeed(ArrayList<Reservation> resv){
			ArrayList<Groupe> res = new ArrayList<Groupe>();
			ArrayList<String> rest = concernedRes(resv);
			
			for(String s: rest){
				res.add(new Groupe(s, filterRes(s, resv)));
			}
			
			return res;
		}
		
		private class ViewHolder{
			public TextView data;
			
		}
		
		private class Groupe{
			private String name;
			private ArrayList<Reservation> res;
			
			public Groupe(String name,ArrayList<Reservation> res){
				this.setName(name);
				this.setRes(res);
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

		}
	}
	
	private ArrayList<String> concernedRes(ArrayList<Reservation> resv){
		ArrayList<String> res = new ArrayList<String>();
		for(Reservation r : resv){
			String rest = r.getReservationResName();
			if(!(res.contains(rest))){
				res.add(rest);
			}
		}
		return res;
	}
	
	private ArrayList<Reservation> filterRes(String resto, ArrayList<Reservation> resv){
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		
		for(Reservation r : resv){
			String rest = r.getReservationResName();
			if(resto.equals(rest)){
				res.add(r);
			}
		}
		
		return res;
	}
}


