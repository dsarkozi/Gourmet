package com.gourmet6;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MenuAdapter extends BaseExpandableListAdapter{

	private Context context;
	private LayoutInflater inflater;
	private Restaurant current;
	private ArrayList<Groupe> list;
	private boolean fromOrder = false;
	
	public MenuAdapter (Context c, ArrayList<Groupe> list, Restaurant current, boolean fromOrder){
		this.context = c;
		this.list = list;
		this.current = current;
		this.fromOrder = fromOrder;
		this.inflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition).getSubtypes().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		//TODO
		
		
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return list.get(groupPosition).getSubtypes().size();
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
	
	private class SubGroupeViewHolder{
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
		private ArrayList<Subgroupe> subtypes;
		
		public String getType()
		{
			return type;
		}
		
		public void setType(String type) 
		{
			this.type = type;
		}
		
		public ArrayList<Subgroupe> getSubtypes() 
		{
			return subtypes;
		}
		
		public void setSubtypes(ArrayList<Subgroupe> subtypes) 
		{
			this.subtypes = subtypes;
		}
		
	}
	
	private class Subgroupe{
		private Groupe grp;
		private String subtype;
		private ArrayList<DishElem> dishlist;
		
		public Groupe getGrp() 
		{
			return grp;
		}
		public void setGrp(Groupe grp) 
		{
			this.grp = grp;
		}
		public String getSubtype() 
		{
			return subtype;
		}
		public void setSubtype(String subtype) 
		{
			this.subtype = subtype;
		}
		public ArrayList<DishElem> getDishlist() 
		{
			return dishlist;
		}
		public void setDishlist(ArrayList<DishElem> dishlist) 
		{
			this.dishlist = dishlist;
		}
	}
	
	private class DishElem{
		private Subgroupe sgrp;
		private String name;
		
		public Subgroupe getSgrp() 
		{
			return sgrp;
		}
		public void setSgrp(Subgroupe sgrp) 
		{
			this.sgrp = sgrp;
		}
		public String getName() 
		{
			return name;
		}
		public void setName(String name) 
		{
			this.name = name;
		}
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
			DishViewHolder dholder;
			
			DishElem dish = (DishElem) getChild(groupPosition, childPosition);
			Dish currentdish = current.getDish(dish.getName());
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
			
			if(fromOrder){
				dholder.plus.setVisibility(View.VISIBLE);
				dholder.plus.setClickable(true);
				dholder.minus.setVisibility(View.VISIBLE);
				dholder.minus.setClickable(true);
			}
			
			dholder.name.setText(dish.getName());
			dholder.price.setText(String.valueOf(currentdish.getPrice()));
			
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
			SubGroupeViewHolder sholder;
			
			Subgroupe sgrp = (Subgroupe) getGroup(groupPosition);
			
			if(convertView == null)
			{
				sholder = new SubGroupeViewHolder();
				convertView = inflater.inflate(R.layout.child_layout, null);
				sholder.type = (TextView) convertView.findViewById(R.id.child); 
				convertView.setTag(sholder);
			}
			else{
				sholder = (SubGroupeViewHolder) convertView.getTag();
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
