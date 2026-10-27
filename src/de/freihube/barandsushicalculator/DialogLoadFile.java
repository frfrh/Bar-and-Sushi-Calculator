package de.freihube.barandsushicalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;


public class DialogLoadFile extends Activity
{
	// Return Intent extra
	public static final String EXTRA_FILE_NAME = "file name";
	//	
	//	public static String currentPath = "", fileName = "";
	ArrayList<ItemFileList> fileViewItems = new ArrayList<ItemFileList>();
	
	private boolean showAllFiles = false;
	
	private File file;
	
	private ListView listViewFile;
	
	private IntentExtra intentExtra;
	
	String[] acceptedFileEndings =
	{
			Statics.FILE_EXTENSION_BILL, Statics.FILE_EXTENSION_LOCATION
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.dialog_load_file);
		// Set result CANCELED in case the user backs out
		setResult(Activity.RESULT_CANCELED);
		
		try
		{
			intentExtra =
					(IntentExtra) getIntent()
							.getSerializableExtra(IntentExtra.KEY_INTENT_EXTRA);
			if (intentExtra.getType() != IntentExtra.IntentExtraType.CHOOSE_LOCATION_FILENAME
					&& intentExtra.getType() != IntentExtra.IntentExtraType.CHOOSE_BILL_FILENAME)
			{
				cancelResult();
			}
		} catch (Exception e)
		{
			cancelResult();
		}
		
		listViewFile = (ListView) findViewById(R.id.list_sdo_files);
		listViewFile.setOnItemClickListener(new ListItemHandler());
		updateList();
	}
	
	private class ListItemHandler implements AdapterView.OnItemClickListener
	{
		public void onItemClick(
				AdapterView<?> parent,
				View view,
				int position,
				long id)
		{
			String fileName = getFileFromList(position);
			IntentExtra.IntentExtraType intentExtraType;
			
			Intent intent = new Intent();
			
			switch (intentExtra.getType())
			{
				case CHOOSE_LOCATION_FILENAME:
					intentExtraType =
							IntentExtra.IntentExtraType.RETURN_LOCATION_FILENAME;
					intent.putExtra(IntentExtra.KEY_INTENT_EXTRA, new IntentExtra(intentExtraType,
							fileName));
					setResult(Activity.RESULT_OK, intent);
					break;
				case CHOOSE_BILL_FILENAME:
					intentExtraType =
							IntentExtra.IntentExtraType.RETURN_BILL_FILENAME;
					intent.putExtra(IntentExtra.KEY_INTENT_EXTRA, new IntentExtra(intentExtraType,
							fileName));
					
					setResult(Activity.RESULT_OK, intent);
					break;
				default:
					setResult(Activity.RESULT_CANCELED, intent);
			}
			finish();
		}
	}
	
	private void cancelResult()
	{
		setResult(Activity.RESULT_CANCELED, new Intent());
		finish();
	}
	
	private String getFileFromList(int position)
	{
		return fileViewItems.get(position).getName();
	}
	
	public void updateList()
	{
		ItemFileList.FileType fileType = null;
		try
		{
			IntentExtra.IntentExtraType intentType = intentExtra.getType();
			switch (intentType)
			{
				case CHOOSE_BILL_FILENAME:
					fileType = ItemFileList.FileType.BILL;
					acceptedFileEndings = new String[] { Statics.FILE_EXTENSION_BILL };
					break;
				case CHOOSE_LOCATION_FILENAME:
					fileType = ItemFileList.FileType.LOCATION;
					acceptedFileEndings = new String[] { Statics.FILE_EXTENSION_LOCATION };
					break;
				default:
					clearList(getResources()
							.getString(R.string.error_intent_extra_read_error));
			}
		} catch (Exception e)
		{
			clearList(getResources()
					.getString(R.string.error_intent_extra_read_error));
		}
		ArrayList<String> filStruc = new ArrayList<String>();
		file = new File(Statics.FILE_PATH_SAVE_DATA);
		if (!file.exists())
		{
			file.mkdirs();
		}
		File[] filArr = file.listFiles();
		fileViewItems.clear();
		
		if (filArr.length == 0)
		{
			clearList(getResources().getString(R.string.error_no_file_found));
		}
		
		for (int i = 0; i < filArr.length; i++)
		{
			File fileTmp = filArr[i];
			if (fileTmp.isFile())
			{
				boolean filtered = true;
				String fileNameTmp = fileTmp.getName().toUpperCase(Locale.US);
				String fileNameToAdd = "";
				
				if(fileNameTmp.startsWith("."))
					continue;
				
				for (int j = 0; j < acceptedFileEndings.length; j++)
				{
					acceptedFileEndings[j] =
							acceptedFileEndings[j].toUpperCase(Locale.US);
					if (fileNameTmp.endsWith(acceptedFileEndings[j]))
					{
						filtered = false;
						fileNameToAdd =
								fileTmp.getName().substring(0,
										fileTmp.getName().length()
												- acceptedFileEndings[j]
														.length());
						break;
					}
				}
				
				if (!filtered || showAllFiles)
				{
					filStruc.add(fileNameToAdd);
				}
			}
		}
		java.util.Collections.sort(filStruc, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < filStruc.size(); i++)
		{
			String fileNameTmp = filStruc.get(i);
			fileViewItems.add(new ItemFileList(fileType, fileNameTmp));
			
		}
		listViewFile.setAdapter(new AdapterForFile(this, fileViewItems));
	}
	
	private void clearList(String Message)
	{
		fileViewItems
				.add(new ItemFileList(ItemFileList.FileType.ERROR, Message));
		listViewFile.setAdapter(new AdapterForFile(this, fileViewItems));
	}
}
