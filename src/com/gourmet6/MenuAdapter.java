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
	
	public MenuAdapter (Context c, ArrayList<Groupe> list, Restaurant current){
		this.context = c;
		this.list = list;
		this.current = current;
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
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
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

}
