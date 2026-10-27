package de.freihube.barandsushicalculator;

public class ItemFileList
{
	enum FileType
	{
		BILL,
		LOCATION,
		ERROR
	}
	
	private static final int ICON_BILL = R.drawable.icon_bill;
	private static final int ICON_LOCATION = R.drawable.icon_location;
	private static final int ICON_ERROR = R.drawable.empty_space;
	
	private static final int LAYOUT = R.layout.listitem_file;
	
	private FileType fileType;
	private String name;
	
	public ItemFileList(FileType type, String nameToShow)
	{
		this.name = nameToShow;
		this.fileType = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getIconID()
	{
		switch(fileType)
		{
			case BILL:
				return ICON_BILL;
			case LOCATION:
				return ICON_LOCATION;
			default:
				return ICON_ERROR;
		}
	}
	
	public int getLayoutID()
	{
		return LAYOUT;
	}
}
