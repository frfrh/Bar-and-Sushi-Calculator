package de.freihube.barandsushicalculator;

import java.io.File;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class Activity_Main extends TabActivity
{
	static TabHost tabHost;
	
	protected static boolean extStoAvail = false;
	protected static boolean extStoWrite = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Statics.activity = this;
		Statics.DEBUGred("Bar and Sushi Calculator", "+++ ON CREATE MAIN +++");
		
		Statics.FILE_PATH_SD_CARD = Environment.getExternalStorageDirectory()
				.getPath();
		Statics.FILE_PATH_SAVE_DATA = Statics.FILE_PATH_SD_CARD
				+ File.separator
				+ getResources().getString(R.string.path_folder_to_save)
				+ File.separator;
		createDir();
		setContentView(R.layout.activity_main);
		
		ItemFoodList.setPriceSeperator(getResources().getString(R.string.price_separator));
		ItemFoodList.setPriceUnit(getResources().getString(R.string.text_price_unit_upper));
		
		Resources res = getResources();
		tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		intent = new Intent().setClass(this, ActivityBill.class);
		spec =
				tabHost.newTabSpec(getString(R.string.tab_name_bill))
						.setIndicator(getString(R.string.tab_name_bill),
								res.getDrawable(R.drawable.icon_tab_bill))
						.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, ActivityLocation.class);
		spec =
				tabHost.newTabSpec(getString(R.string.tab_name_location))
						.setIndicator(getString(R.string.tab_name_location),
								res.getDrawable(R.drawable.icon_tab_location))
						.setContent(intent);
		tabHost.addTab(spec);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Statics.DEBUGred("Bar and Sushi Calculator", "--- ON DESTROY Main ---");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_add_food:
	        	Statics.DEBUGgreen("Activity_Main - onOptionsItemSelected", "add_food");
	            return true;
	        case R.id.action_settings:
	        	Statics.DEBUGgreen("Activity_Main - onOptionsItemSelected", "settings");
	            return true;
	        case R.id.action_save_bill:
	        	Statics.DEBUGgreen("Activity_Main - onOptionsItemSelected", "save_bill");
	            return true;
	        case R.id.action_load_bill:
	        	Statics.DEBUGgreen("Activity_Main - onOptionsItemSelected", "load_bill");
	            return true;
	        case R.id.action_save_location:
	        	Statics.DEBUGgreen("Activity_Main - onOptionsItemSelected", "save_location");
	            return true;
	        case R.id.action_load_location:
	        	Statics.DEBUGgreen("Activity_Main - onOptionsItemSelected", "load_location");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	protected static void checkEnvironment()
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			// We can read and write the media
			extStoAvail = true;
			extStoWrite = true;
		}
		else
			if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
			{
				// We can only read the media
				extStoAvail = true;
				extStoWrite = false;
			}
			else
			{
				extStoAvail = false;
				extStoWrite = false;
			}
	}
	protected static void createDir()
	{
		File file = new File(
				Statics.FILE_PATH_SAVE_DATA);
		if(file.exists())
			return;
		file.mkdirs();
	}
}
