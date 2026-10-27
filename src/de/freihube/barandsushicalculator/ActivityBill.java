package de.freihube.barandsushicalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.freihube.barandsushicalculator.IntentExtra.IntentExtraType;
import de.freihube.barandsushicalculator.ItemFoodList.FoodType;


public class ActivityBill extends FoodListActivity
{
	public static ActivityBill activityBill;
	public static Context context;
	
	private static TouchInterceptor listViewFood;
	private static TextView textViewTotalPriceBill;
	
	private static ArrayList<ItemFoodCountContainer> foodItems =
			new ArrayList<ItemFoodCountContainer>();
	protected static int foodItemClickPosition;
	
	private static Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Statics.DEBUGred("Bar and Sushi Calculator", "+++ ON CREATE Bill +++");
		super.onCreate(savedInstanceState);
		activityBill = this;
		context = this;
		setContentView(R.layout.activity_bill);
		
		listViewFood = (TouchInterceptor) findViewById(R.id.listViewFoodBill);
		textViewTotalPriceBill =
				(TextView) findViewById(R.id.textViewTotalPriceBill);
		
		listViewFood.setOnItemClickListener(new ListItemHandler());
		listViewFood.setOnItemLongClickListener(new ListItemLongHandler());
		listViewFood.setDropListener(new BillDropListener());
		
		registerForContextMenu(listViewFood);
		ActivityBill.loadBill(Statics.FILE_NAME_CURRENT);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Statics.DEBUGred("Bar and Sushi Calculator", "++ ON RESUME Bill ++");
		if ((foodItems == null || foodItems.size() == 0)
				&& Statics.DEBUG_FILL_BILL)
		{
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_black_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_black));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_blue_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_blue));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_green_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_green));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_grey_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_grey));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_orange_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_orange));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_pink_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_pink));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_red_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_red));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_white_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_white));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_yellow_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_yellow));
			foodItems.add(new ItemFoodCountContainer(
					getString(R.string.text_name_purple_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_purple));
		}
		updateList(false);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Statics.DEBUGred("Bar and Sushi Calculator", "--- ON DESTROY Bill ---");
		saveBill(foodItems, Statics.FILE_NAME_CURRENT);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_activity_bill, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
			case R.id.action_add_food:
				Statics.DEBUGgreen("ActivityBill - onOptionsItemSelected",
						"add_food");
				addFood();
				return true;
			case R.id.action_save_bill:
				Statics.DEBUGgreen("ActivityBill - onOptionsItemSelected",
						"save_bill");
				saveBill();
				return true;
			case R.id.action_load_bill:
				Statics.DEBUGgreen("ActivityBill - onOptionsItemSelected",
						"load_bill");
				loadBill();
				return true;
			case R.id.action_clear_bill:
				Statics.DEBUGgreen("ActivityBill - onOptionsItemSelected",
						"clearBill");
				clearBill();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public static void updateList(boolean keepScroll)
	{
		if (keepScroll)
		{
			int currentVisiblePosition = listViewFood.getFirstVisiblePosition();
			View v = listViewFood.getChildAt(currentVisiblePosition);
			int currentYPosition = (v == null) ? 0 : v.getTop();
			
			listViewFood
					.setAdapter(new AdapterForBill(activityBill, foodItems));
			
			currentVisiblePosition += currentVisiblePosition;
			
			listViewFood.setSelectionFromTop(currentVisiblePosition,
					currentYPosition);
		}
		else
		{
			listViewFood
					.setAdapter(new AdapterForBill(activityBill, foodItems));
		}
		
		int totalPrice = 0;
		for (ItemFoodCountContainer foodItem : foodItems)
		{
			totalPrice += foodItem.getTotalPriceCent();
		}
		textViewTotalPriceBill.setText(ItemFoodList.getPriceString(totalPrice));
	}
	
	@Override
	public void updateFoodList(boolean keepScroll)
	{
		updateList(keepScroll);
	}
	
	private class ListItemHandler implements OnItemClickListener
	{
		public void onItemClick(
				AdapterView<?> parent,
				View view,
				int position,
				long id)
		{
			Statics.DEBUGgreen("ActivityBill - ClickHandler", "clicked");
		}
	}
	
	private class ListItemLongHandler implements OnItemLongClickListener
	{
		public boolean onItemLongClick(
				AdapterView<?> parent,
				View view,
				int position,
				long id)
		{
			Statics.DEBUGgreen("ActivityBill - LongClickHandler",
					"long clicked");
			
			foodItemClickPosition = position;
			
			return false;
		}
	}
	
	@Override
	public void onCreateContextMenu(
			ContextMenu menu,
			View v,
			ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_bill_list, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.context_view_change_count:
				return changeCount();
			case R.id.context_view_change_price:
				return changePrice();
			case R.id.context_view_remove_item:
				return removeItem();
			case R.id.context_view_rename:
				return renameItem();
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	@Override
	protected void
			onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == Statics.REQUEST_CODE_FILENAME_BILL)
		{
			if (resultCode == RESULT_OK)
			{
				IntentExtra result =
						(IntentExtra) data
								.getSerializableExtra(IntentExtra.KEY_INTENT_EXTRA);
				if (result != null)
					loadBill(result.getMessage());
			}
		}
		if (requestCode == Statics.REQUEST_CODE_ADD_FOOD)
		{
			if (resultCode == RESULT_OK)
			{
				ItemFoodList result =
						(ItemFoodList) data
								.getSerializableExtra(IntentExtra.KEY_ITEM_FOOD_LIST);
				foodItems.add(new ItemFoodCountContainer(result));
				updateList(false);
				
			}
		}
	}
	
	private boolean renameItem()
	{
		View checkBoxView =
				View.inflate(this, R.layout.dialog_name_edittext, null);
		final EditText editTextName =
				(EditText) checkBoxView.findViewById(R.id.editTextName);
		
		editTextName.setText(foodItems.get(foodItemClickPosition).getFoodItem()
				.getName());
		
		new AlertDialog.Builder(this)
				.setCancelable(true)
				.setView(checkBoxView)
				.setPositiveButton(R.string.text_rename,
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								foodItems
										.get(foodItemClickPosition)
										.getFoodItem()
										.setName(editTextName.getText()
												.toString());
								updateList(true);
							}
						}).setNegativeButton(R.string.text_abort, null).show();
		return true;
	}
	
	private boolean removeItem()
	{
		new AlertDialog.Builder(this)
				.setMessage(getResources()
						.getString(R.string.questionmark_leading)
						+ getResources().getString(R.string.text_sure_delete1)
						+ " "
						+ foodItems.get(foodItemClickPosition).getFoodItem()
								.getName()
						+ " "
						+ getResources().getString(R.string.text_sure_delete2)
						+ getResources().getString(R.string.questionmark))
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						foodItems.remove(foodItemClickPosition);
						updateList(true);
					}
				}).setNegativeButton("No", null).show();
		return true;
	}
	
	protected static void addItemToFoodList(ItemFoodList foodItem)
	{
		foodItems.add(new ItemFoodCountContainer(foodItem));
	}
	
	private boolean changePrice()
	{
		DialogChangePrice.foodItem = foodItems.get(foodItemClickPosition);
		activityBill
				.startActivity(new Intent(context, DialogChangePrice.class));
		return true;
	}
	
	private boolean changeCount()
	{
		DialogChangeCount.foodItem = foodItems.get(foodItemClickPosition);
		activityBill
				.startActivity(new Intent(context, DialogChangeCount.class));
		return true;
	}
	
	private void addFood()
	{
		Intent intent = new Intent(this, DialogAddFood.class);
		startActivityForResult(intent, Statics.REQUEST_CODE_ADD_FOOD);
	}
	
	private void clearBill()
	{
		new AlertDialog.Builder(this)
				.setMessage(getResources()
						.getString(R.string.questionmark_leading)
						+ getResources()
								.getString(R.string.text_sure_clear_bill)
						+ getResources().getString(R.string.questionmark))
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						foodItems.clear();
						updateList(false);
					}
				}).setNegativeButton("No", null).show();
	}
	
	private void loadBill()
	{
		Intent intent = new Intent(this, DialogLoadFile.class);
		intent.putExtra(IntentExtra.KEY_INTENT_EXTRA, new IntentExtra(
				IntentExtraType.CHOOSE_BILL_FILENAME));
		startActivityForResult(intent, Statics.REQUEST_CODE_FILENAME_BILL);
	}
	
	protected static void loadBill(String filename)
	{
		Statics.DEBUGgreen("ActivityBill - loadBill", "load from: " + filename);
		if (filename.equals(""))
		{
			return;
		}
		final String fileName =
				Statics.FILE_PATH_SAVE_DATA
						+ filename
						+ Statics.FILE_EXTENSION_BILL;
		
		handler.post(new Runnable()
		{
			@SuppressWarnings("unchecked")
			public void run()
			{
				Activity_Main.checkEnvironment();
				if (Activity_Main.extStoAvail)
				{
					FileInputStream fis = null;
					ObjectInputStream ois = null;
					try
					{
						File file = new File(fileName);
						fis = new FileInputStream(file);
						ois = new ObjectInputStream(fis);
						Object objTmp = ois.readObject();
						ActivityBill.foodItems =
								(ArrayList<ItemFoodCountContainer>) objTmp;
						updateList(false);
					} catch (OptionalDataException e)
					{
						Statics.DEBUGred("ActivityBill - loadBill",
								"OptionalDataException");
						e.printStackTrace();
					} catch (ClassNotFoundException e)
					{
						Statics.DEBUGred("ActivityBill - loadBill",
								"ClassNotFoundException");
						e.printStackTrace();
					} catch (IOException e)
					{
						Statics.DEBUGred("ActivityBill - loadBill",
								"IOException");
						e.printStackTrace();
					} catch (Exception e)
					{
						Statics.DEBUGred("ActivityBill - loadBill", "Exception");
						e.printStackTrace();
					} finally
					{
						if (fis != null)
						{
							try
							{
								fis.close();
							} catch (IOException e)
							{}
						}
						if (ois != null)
						{
							try
							{
								ois.close();
							} catch (IOException e)
							{}
						}
					}
				}
			}
		});
	}
	
	private boolean saveBill()
	{
		View checkBoxView =
				View.inflate(this, R.layout.dialog_name_edittext_checkbox, null);
		final EditText editTextName =
				(EditText) checkBoxView.findViewById(R.id.editTextName);
		final CheckBox checkBoxSaveCount =
				(CheckBox) checkBoxView.findViewById(R.id.checkBoxSaveCounts);
		
		editTextName.setText("");
		
		new AlertDialog.Builder(this)
				.setCancelable(true)
				.setView(checkBoxView)
				.setPositiveButton(R.string.text_save,
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								ArrayList<ItemFoodCountContainer> foodItemsToSave;
								
								if (checkBoxSaveCount.isChecked())
								{
									foodItemsToSave = foodItems;
								}
								else
								{
									foodItemsToSave =
											new ArrayList<ItemFoodCountContainer>();
									for (ItemFoodCountContainer foodItem : foodItems)
									{
										foodItemsToSave
												.add(new ItemFoodCountContainer(
														foodItem.getFoodItem()));
									}
								}
								
								String name = editTextName.getText().toString();
								if (name.length() > 0)
									saveBill(foodItemsToSave, editTextName
											.getText().toString());
								else
									try
									{
										Toast.makeText(getApplicationContext(),
												R.string.text_invalid_name,
												Toast.LENGTH_LONG).show();
									} catch (Exception e)
									{}
							}
						}).setNegativeButton(R.string.text_abort, null).show();
		return true;
	}
	
	protected static void saveBill(
			ArrayList<ItemFoodCountContainer> foodItemsToSave,
			String fileName)
	{
		Statics.DEBUGgreen("ActivityBill - saveBill", "save to: " + fileName);
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try
			{
				File file =
						new File(Statics.FILE_PATH_SAVE_DATA
								+ fileName
								+ Statics.FILE_EXTENSION_BILL);
				fos = new FileOutputStream(file);
				
				oos = new ObjectOutputStream(fos);
				oos.writeObject(foodItemsToSave);
			} catch (FileNotFoundException fnfe)
			{} catch (IOException ioe)
			{} catch (Exception e)
			{} finally
			{
				try
				{
					oos.flush();
				} catch (Exception e)
				{}
				try
				{
					oos.close();
				} catch (IOException e)
				{}
				try
				{
					fos.close();
				} catch (IOException e)
				{}
			}
		}
	}
	private class BillDropListener implements TouchInterceptor.DropListener
	{
		
		public void drop(int from, int to)
		{
			//Assuming that item is moved up the list
			int direction = -1;
			int loop_start = from;
			int loop_end = to;
			
			//For instance where the item is dragged down the list
			
			if (from < to)
			{
				direction = 1;
			}
			
			ItemFoodCountContainer target = foodItems.get(from);
			
			for (int i = loop_start; i != loop_end; i = i + direction)
			{
				foodItems.set(i, foodItems.get(i + direction));				
			}
			
			foodItems.set(to, target);
						
			((BaseAdapter) listViewFood.getAdapter()).notifyDataSetChanged();
			
		}
		
	};
}
