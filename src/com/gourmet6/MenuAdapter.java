package com.gourmet6;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class MenuAdapter extends BaseExpandableListAdapter{

	private Context context;
	private LayoutInflater inflater;
	private Restaurant current;
	private ArrayList<Groupe> list;
	private boolean fromOrder = false;
	
	public MenuAdapter (Context c, Restaurant current, boolean fromOrder){
		this.context = c;
		this.current = current;
		this.fromOrder = fromOrder;
		this.inflater = LayoutInflater.from(context);
		this.list = populateType(current.getDishesTypes());
		
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition).getSubtypes();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		//TODO
		ExpandableListView dev = (ExpandableListView) getChild(groupPosition,childPosition);
		
		dev.setAdapter(new ChildAdapter(context, ((Groupe)getGroup(groupPosition)).getSub()));
		if(!fromOrder){
			dev.setOnChildClickListener(new OnChildClickListener() {
			
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {

					Intent display = new Intent(context, DishDisplayActivity.class);
					display.putExtra("the_dish",((Dish) getChild(groupPosition,childPosition)).getName());
					context.startActivity(display);
					return true;
				}
			});
		}
		convertView = dev;
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupeViewHolder gholder;
		
		Groupe group = (Groupe) getGroup(groupPosition);
		
		if(convertView == null)
		{
			gholder = new GroupeViewHolder();
			convertView = inflater.inflate(R.layout.grp_layout, null);
			gholder.type = (TextView) convertView.findViewById(R.id.groupe);
			convertView.setTag(gholder);
		}
		else{
			gholder = (GroupeViewHolder) convertView.getTag();
		}
		
		
			
		gholder.type.setText(group.getType());
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}
	
	private class GroupeViewHolder{
		public TextView type;
	}
	
	private class DishViewHolder{
		public TextView name;
		public TextView price;
		public Button plus;
		public TextView count;
		public Button minus;
	}
	
	private class Groupe{
		private String type;
		private ExpandableListView subtypes = new ExpandableListView(context);
		private ArrayList<Subgroupe> subtypelist;
		
		public Groupe(String type, ArrayList<Subgroupe> subt)
		{
			this.type = type;
			this.subtypelist = subt;
		}
		
		public String getType()
		{
			return type;
		}
		
		public ExpandableListView getSubtypes() 
		{
			return subtypes;
		}

		public ArrayList<Subgroupe> getSub() 
		{
			return subtypelist;
		}
	
	}
	
	private class Subgroupe{
		private String subtype;
		private ArrayList<Dish> dishlist;
		
		public Subgroupe(String sub, ArrayList<Dish> list)
		{
			this.subtype = sub;
			this.dishlist = list;
		}
		
		public String getSubtype() 
		{
			return subtype;
		}
		public ArrayList<Dish> getDishlist() 
		{
			return dishlist;
		}
	}

	private ArrayList<Groupe> populateType(ArrayList<String> types)
	{
		ArrayList<Groupe> res = new ArrayList<Groupe>(types.size());
		
		for(String s : types){
			res.add(new Groupe(s, populateSubType(s, current.getDishesSubtypes(s))));
		}
		
		return res;
	}
	
	private ArrayList<Subgroupe> populateSubType(String type ,ArrayList<String> subtypes)
	{
		ArrayList<Subgroupe> res = new ArrayList<Subgroupe>(subtypes.size());
		
		for(String s: subtypes){
			res.add(new Subgroupe(s, current.filterDishesSubtype(s, type)));
		}
		
		return res;
	}
	
	private class ChildAdapter extends BaseExpandableListAdapter{
		
		private Context context;
		private LayoutInflater inflater;
		private ArrayList<Subgroupe> sublist;
		
		public ChildAdapter(Context c, ArrayList<Subgroupe> list)
		{
			this.context = c;
			this.sublist = list;
			this.inflater = LayoutInflater.from(context);
			
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			
			return sublist.get(groupPosition).getDishlist().get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			final DishViewHolder dholder;
			
			final Dish currentdish = (Dish) getChild(groupPosition, childPosition);
			
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.dish_list_element, null);
				
				dholder.name = (TextView) convertView.findViewById(R.id.dishname);
				dholder.price = (TextView) convertView.findViewById(R.id.textView1);
				dholder.count = (TextView) convertView.findViewById(R.id.textView2);
				dholder.plus = (Button) convertView.findViewById(R.id.plus);
				dholder.minus = (Button) convertView.findViewById(R.id.minus);		
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
			
			dholder.name.setText(currentdish.getName());
			dholder.price.setText(String.valueOf(currentdish.getPrice()));
			dholder.count.setText("Stock:"+String.valueOf(currentdish.getInventory()));
			
			if(fromOrder){
				dholder.plus.setVisibility(View.VISIBLE);
				dholder.plus.setClickable(true);
				dholder.minus.setVisibility(View.VISIBLE);
				dholder.minus.setClickable(true);
			}
			
			dholder.plus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(currentdish.getQuantity() < currentdish.getInventory())
					{
						currentdish.setQuantity(currentdish.getQuantity() + 1 );
						dholder.count.setText(currentdish.getQuantity());
					}
				}
			});
			
			dholder.plus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(currentdish.getQuantity() > 0)
					{
						currentdish.setQuantity(currentdish.getQuantity() - 1 );
						dholder.count.setText(currentdish.getQuantity());
					}
				}
			});
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			
			return sublist.get(groupPosition).getDishlist().size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			
			return sublist.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			
			return sublist.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
			GroupeViewHolder sholder;
			
			Subgroupe sgrp = (Subgroupe) getGroup(groupPosition);
			
			if(convertView == null)
			{
				sholder = new GroupeViewHolder();
				convertView = inflater.inflate(R.layout.child_layout, null);
				sholder.type = (TextView) convertView.findViewById(R.id.child); 
				convertView.setTag(sholder);
			}
			else{
				sholder = (GroupeViewHolder) convertView.getTag();
			}
			
			sholder.type.setText(sgrp.getSubtype());
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			
			return true;
		}
		
	}

}
