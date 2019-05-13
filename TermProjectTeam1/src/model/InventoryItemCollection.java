// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the InventoryItemTypeCollection for the Library application */
//==============================================================
public class InventoryItemCollection extends EntityBase implements IView
{
	private static final String myTableName = "InventoryItem";

	protected Vector<InventoryItem> list;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public InventoryItemCollection()
	{
		super(myTableName);
	}

	//----------------------------------------------------------------------------------
	public void findAllInventoryItemsWithName(String name)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE InventoryItemTypeName LIKE \"%" + name + "%\" AND Status = 'Available'";
		
		Vector allDataRetrieved = getSelectQueryResult(query);
		
//		System.out.println(query + "\n");

		if (allDataRetrieved != null)
		{
			list = new Vector<InventoryItem>();

			for (int i = 0; i < allDataRetrieved.size(); i++)
			{
				Properties nextInventoryItemData = (Properties)allDataRetrieved.elementAt(i);

				InventoryItem inventoryItem = new InventoryItem(nextInventoryItemData);

				if (inventoryItem != null)
				{
					addIIT(inventoryItem);
				}
				
			}

		}
	}
	
	public void findAllInventoryItems()
	{
		String query = "SELECT * FROM " + myTableName + " WHERE Status = 'Available'";
		
		Vector allDataRetrieved = getSelectQueryResult(query);
		
//		System.out.println(query + "\n");

		if (allDataRetrieved != null)
		{
			list = new Vector<InventoryItem>();

			for (int i = 0; i < allDataRetrieved.size(); i++)
			{
				Properties nextInventoryItemTypeData = (Properties)allDataRetrieved.elementAt(i);

				InventoryItem inventoryItemType = new InventoryItem(nextInventoryItemTypeData);

				if (inventoryItemType != null)
				{
					addIIT(inventoryItemType);
				}
				
			}

		}
	}
	
	
	protected void addIIT(InventoryItem a)
	{
		//list.add(a);
		int index = findIndexToAdd(a);
		list.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(InventoryItem a)
	{
		//users.add(u);
		int low=0;
		int high = list.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			InventoryItem midSession = list.elementAt(middle);

			int result = InventoryItem.compare(a,midSession);

			if (result ==0)
			{
				return middle;
			}
			else if (result<0)
			{
				high=middle-1;
			}
			else
			{
				low=middle+1;
			}


		}
		return low;
	}


	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("InventoryItemTypes"))
			return list;
		else
		if (key.equals("InventoryItemTypeList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public InventoryItem retrieve(String inventoryItemTypeName)
	{
		InventoryItem retValue = null;
		for (int cnt = 0; cnt < list.size(); cnt++)
		{
			InventoryItem nextInventoryItem = list.elementAt(cnt);
			String nextInventoryItemTypeName = (String)nextInventoryItem.getState("InventoryItemTypeName");
			if (nextInventoryItemTypeName.equals(inventoryItemTypeName) == true)
			{
				retValue = nextInventoryItem;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		Scene localScene = myViews.get("InventoryItemTypeCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("InventoryItemTypeCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("InventoryItemTypeCollectionView", localScene);
		}
		// make the view visible by installing it into the frame
		swapToView(localScene);
		
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
}
