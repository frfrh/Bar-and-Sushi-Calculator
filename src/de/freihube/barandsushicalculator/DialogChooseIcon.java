package de.freihube.barandsushicalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;


public class DialogChooseIcon extends Activity
{
	private Integer[] icons;
	private Spinner spinner;
	private GridView gridViewIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Statics.DEBUGred("Bar and Sushi Calculator",
				"+++ ON CREATE ChooseIcon +++");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_choose_icon);
		
		setResult(Activity.RESULT_CANCELED);
		
		spinner = (Spinner) findViewById(R.id.spinnerIconType);
		ArrayAdapter<CharSequence> adapter =
				ArrayAdapter.createFromResource(this,
						R.array.string_array_icon_type,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		
		gridViewIcon = (GridView) findViewById(R.id.gridViewIcon);
		gridViewIcon.setOnItemClickListener(new GridItemHandler());
		
		addAll();
	}
	
	private void addAll()
	{
		ArrayList<Integer> arraysTmp = new ArrayList<Integer>();
		
		for (Integer icon : Statics.ICONS_SUSHI_PLATES)
			arraysTmp.add(icon);
		for (Integer icon : Statics.ICONS_FOOD)
			arraysTmp.add(icon);
		for (Integer icon : Statics.ICONS_DRINKS)
			arraysTmp.add(icon);
		for (Integer icon : Statics.ICONS_OTHERS)
			arraysTmp.add(icon);
		
		icons = (Integer[]) arraysTmp.toArray(new Integer[1]);
		updateView();
	}
	
	private void updateView()
	{
		gridViewIcon.setAdapter(new AdapterForIconGrid(this, icons));
	}
	
	private class GridItemHandler implements OnItemClickListener
	{
		public void onItemClick(
				AdapterView<?> parent,
				View view,
				int position,
				long id)
		{
			Statics.DEBUGblue("DialogChooseIcon - GridItemHandler",
					"Item Clicked: " + position);
			int iconID = icons[position];
			Intent intent = new Intent();
			intent.putExtra(IntentExtra.KEY_ICON_ID, iconID);
			
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}
	
	private class CustomOnItemSelectedListener implements
			OnItemSelectedListener
	{
		
		public void onItemSelected(
				AdapterView<?> parent,
				View view,
				int pos,
				long id)
		{	
			switch (pos)
			{
				case 0:
					addAll();
					break;
				case 1:
					icons = Statics.ICONS_SUSHI_PLATES;
					updateView();
					break;
				case 2:
					icons = Statics.ICONS_FOOD;
					updateView();
					break;
				case 3:
					icons = Statics.ICONS_DRINKS;
					updateView();
					break;
				case 4:
					icons = Statics.ICONS_OTHERS;
					updateView();
					break;
				default:
			}
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
		
	}
}
