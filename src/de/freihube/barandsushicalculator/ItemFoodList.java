package de.freihube.barandsushicalculator;

import java.io.Serializable;

import android.app.Activity;


public class ItemFoodList implements Serializable
{
	private static final long serialVersionUID = 4877300965813073098L;

	enum FoodType
	{
		FOOD,
		DRINK
	}
	
	protected static Activity activity;
	
	private static Integer PRICE_DIVISOR = 100;
	private static String PRICE_SEPARATOR = ",";
	private static String PRICE_UNIT = "€";
	
	private Integer priceCent;
	private Integer iconID;
	private FoodType foodType;
	private String name;
	
	public ItemFoodList(String name, FoodType type, int priceCent, int iconID)
	{
		setPrice(priceCent);
		setIconID(iconID);
		setFoodType(type);
		setName(name);
	}
	
	public ItemFoodList(String name, FoodType type, int priceUpper,
			int priceLower, int iconID)
	{
		setPrice(priceUpper, priceLower);
		setIconID(iconID);
		setFoodType(type);
		setName(name);
	}
	
	public int getPriceDivisor()
	{
		return PRICE_DIVISOR;
	}
	
	public void setPriceDivisor(int priceDivisor)
	{
		PRICE_DIVISOR = priceDivisor;
	}
	
	public float getPrice()
	{
		return getPrice(this.priceCent);
	}
	
	protected float getPrice(int price)
	{
		return (price / PRICE_DIVISOR)
				+ (((float) (price % PRICE_DIVISOR)) / PRICE_DIVISOR);
	}
	
	public int getPriceUpper()
	{
		return getPriceUpper(priceCent);
	}
	
	public int getPriceLower()
	{
		return getPriceLower(priceCent);
	}
	
	public String getPriceUpperString()
	{
		return Integer.toString(getPriceUpper());
	}
	
	public String getPriceLowerString()
	{
		int priceLower = getPriceLower();
		String output = Integer.toString(getPriceLower());
		for (int i = 10; i < PRICE_DIVISOR; i *= 10)
		{
			if (priceLower < i)
			{
				output = "0" + output;
			}
		}
		return output;
	}
	
	protected static int getPriceUpper(int price)
	{
		return (price / PRICE_DIVISOR);
	}
	
	protected static int getPriceLower(int price)
	{
		return (price % PRICE_DIVISOR);
	}
	
	public static String getPriceString(int price)
	{
		int priceLower = getPriceLower(price);
		return ""
				+ getPriceUpper(price)
				+ getPriceSeperator()
				+ (priceLower < 10 ? "0" + priceLower : priceLower)
				+ getPriceUnit();
	}
	
	public String getPriceString()
	{
		int priceLower = getPriceLower(this.priceCent);
		return ""
				+ getPriceUpper(this.priceCent)
				+ getPriceSeperator()
				+ (priceLower < 10 ? "0" + priceLower : priceLower)
				+ getPriceUnit();
	}
	
	protected static String getPriceSeperator()
	{
		return PRICE_SEPARATOR;
	}
	
	protected static String getPriceUnit()
	{
		return PRICE_UNIT;
	}
	
	public void setPrice(int upper, int lower)
	{
		int lowerTmp = lower;
		int upperTmp = upper;
		if (lowerTmp > PRICE_DIVISOR)
		{
			upperTmp += (lower / PRICE_DIVISOR);
			lowerTmp = (lower % PRICE_DIVISOR);
		}
		this.priceCent = (upperTmp * PRICE_DIVISOR) + lowerTmp;
	}
	
	public void setPriceUpper(int upper)
	{
		setPrice(upper, getPriceLower());
	}
	
	public void setPriceLower(int lower)
	{
		setPrice(getPriceUpper(), lower);
	}
	
	public void setPrice(int priceCent)
	{
		this.priceCent = priceCent;
	}
	
	public void increasePriceUpper()
	{
		priceCent += PRICE_DIVISOR;
	}
	
	public void decreasePriceUpper()
	{
		if(priceCent >= PRICE_DIVISOR)
			priceCent -= PRICE_DIVISOR;
	}
	
	public void increasePriceLower()
	{
		priceCent++;
	}
	
	public void decreasePriceLower()
	{
		if(priceCent == 0)
			priceCent = PRICE_DIVISOR;
			
		priceCent--;
	}
	
	public int getIconID()
	{
		return iconID;
	}
	
	public void setIconID(int iconID)
	{
		this.iconID = iconID;
	}
	
	public FoodType getFoodType()
	{
		return foodType;
	}
	
	public void setFoodType(FoodType foodType)
	{
		this.foodType = foodType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public static void setPriceSeperator(String priceSeperator)
	{
		PRICE_SEPARATOR = priceSeperator;
	}
	
	public static void setPriceUnit(String priceUnit)
	{
		PRICE_UNIT = priceUnit;
	}

	protected Integer getPriceCent()
	{
		return priceCent;
	}

	protected void setPriceCent(Integer priceCent)
	{
		this.priceCent = priceCent;
	}

	protected void setIconID(Integer iconID)
	{
		this.iconID = iconID;
	}
	
}
