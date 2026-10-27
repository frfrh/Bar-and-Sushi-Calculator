package de.freihube.barandsushicalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterForFile extends ArrayAdapter<ItemFileList>
{
	Context context;
	ArrayList<ItemFileList> data = null;
	
	public AdapterForFile(Activity callingActivity, ArrayList<ItemFileList> data)
	{
		super(callingActivity, R.layout.listitem_file, data);
		this.context = callingActivity;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		ViewHolder holder = null;
		
		ItemFileList itemView = data.get(position);
		if (row == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.listitem_file, parent, false);
			
			holder = new ViewHolder();
			
			holder.textViewName =
					(TextView) row.findViewById(R.id.textViewName);
			
			holder.imageIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.textViewName.setText(itemView.getName());
		holder.imageIcon.setImageResource(itemView.getIconID());
		
		return row;
	}
	
	static class ViewHolder
	{
		ImageView imageIcon;
		TextView textViewName;
	}
}
