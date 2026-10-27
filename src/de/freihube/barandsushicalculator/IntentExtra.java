package de.freihube.barandsushicalculator;

import java.io.Serializable;


public class IntentExtra implements Serializable
{
	private static final long serialVersionUID = 3697246010684555615L;
	
	//	public static final String  = "";
	public static final String KEY_INTENT_EXTRA = "INTENT_EXTRA";
	public static final String KEY_ITEM_FOOD_LIST = "ITEM_FOOD_LIST";
	public static final String KEY_ICON_ID = "ICON_ID";
	
	public enum IntentExtraType
	{
		CHOOSE_BILL_FILENAME,
		CHOOSE_LOCATION_FILENAME,
		//
		RETURN_BILL_FILENAME,
		RETURN_LOCATION_FILENAME
	}
	
	private IntentExtraType type;
	private String message;
	
	public IntentExtra(IntentExtraType intentType)
	{
		this.type = intentType;
	}
	
	public IntentExtra(IntentExtraType intentType, String intentMessage)
	{
		this.type = intentType;
		this.message = intentMessage;
	}

	public IntentExtraType getType()
	{
		return type;
	}

	public void setType(IntentExtraType type)
	{
		this.type = type;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
}
