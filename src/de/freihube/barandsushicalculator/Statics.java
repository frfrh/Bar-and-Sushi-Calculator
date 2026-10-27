package de.freihube.barandsushicalculator;

import android.app.Activity;
import android.util.Log;


public class Statics
{
	public static Activity activity;
	public static final boolean DEBUG = true;
	public static boolean DEBUG_FILL_LISTS = true;
	
	public static boolean DEBUG_FILL_BILL = DEBUG_FILL_LISTS && true;
	public static boolean DEBUG_FILL_LOCATION = DEBUG_FILL_LISTS && true;
	
	protected static final String FILE_EXTENSION_BILL = ".bil";
	protected static final String FILE_EXTENSION_LOCATION = ".loc";
	protected static final String FILE_NAME_CURRENT = ".current";
	protected static String FILE_PATH_SD_CARD;
	protected static String FILE_PATH_SAVE_DATA; 
	
	public static final int REQUEST_CODE_FILENAME_BILL = 1;
	public static final int REQUEST_CODE_FILENAME_LOCATION = 2;
	public static final int REQUEST_CODE_ADD_FOOD = 3;
	public static final int REQUEST_CODE_CHOOSE_ICON = 4;
	
	public static final Integer[] ICONS_SUSHI_PLATES =
	{
			R.drawable.plate_black, //
			R.drawable.plate_blue,	//
			R.drawable.plate_green, //
			R.drawable.plate_grey,	//
			R.drawable.plate_orange,//
			R.drawable.plate_pink,	//
			R.drawable.plate_purple,//
			R.drawable.plate_red, 	//
			R.drawable.plate_white, //
			R.drawable.plate_yellow,//
	};
	public static final Integer[] ICONS_DRINKS =
	{
			R.drawable.beer_glas,	//
			R.drawable.beer_bottle,	//
			R.drawable.cola_glas,	//
			R.drawable.water_glas,	//
			R.drawable.water_bottle,//
			R.drawable.wine_glas, 	//
			R.drawable.wine_bottle	//
	};
	public static final Integer[] ICONS_FOOD =
	{
			R.drawable.sushi_roll
	};
	public static final Integer[] ICONS_OTHERS =
	{
			R.drawable.ic_launcher,
			R.drawable.icon_bill,
			R.drawable.icon_location
	};
	
	public static void DEBUGgreen(String tag, String message)
	{
		if (DEBUG)
			Log.i(tag, message);
	}
	
	public static void DEBUGred(String tag, String message)
	{
		if (DEBUG)
			Log.e(tag, message);
	}
	
	public static void DEBUGblue(String tag, String message)
	{
		if (DEBUG)
			Log.d(tag, message);
	}
	
	public static void DEBUGorange(String tag, String message)
	{
		if (DEBUG)
			Log.w(tag, message);
	}
}
