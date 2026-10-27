package de.freihube.barandsushicalculator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class AdapterForIconGrid extends ArrayAdapter<Integer>
{
	Context context;
	Integer[] data = null;
	
	public AdapterForIconGrid(Activity callingActivity, Integer[] data)
	{
		super(callingActivity, R.layout.griditem_icon, data);
		this.context = callingActivity;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		ViewHolder holder = null;
		
		if (row == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.griditem_icon, parent, false);
			
			holder = new ViewHolder();
			
			holder.imageIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.imageIcon.setImageResource(data[position]);
		
		return row;
	}
	
	static class ViewHolder
	{
		ImageView imageIcon;
	}
}
