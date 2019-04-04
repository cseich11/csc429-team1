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
public class InventoryItemTypeCollection extends EntityBase implements IView
{
	private static final String myTableName = "InventoryItemType";

	private Vector<InventoryItemType> list;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public InventoryItemTypeCollection()
	{
		super(myTableName);
	}

	//----------------------------------------------------------------------------------
	public void findAllIITWithNameNotes(String name, String notes)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE ItemTypeName LIKE '%" + name + "%' AND Notes LIKE '%" + notes + "%'";
		
		Vector allDataRetrieved = getSelectQueryResult(query);
		
		System.out.println(query + "\n");

		if (allDataRetrieved != null)
		{
			list = new Vector<InventoryItemType>();

			for (int i = 0; i < allDataRetrieved.size(); i++)
			{
				Properties nextInventoryItemTypeData = (Properties)allDataRetrieved.elementAt(i);

				InventoryItemType inventoryItemType = new InventoryItemType(nextInventoryItemTypeData);

				if (inventoryItemType != null)
				{
					addIIT(inventoryItemType);
				}
				
			}

		}
	}
	
	public void findAllIIT()
	{
		String query = "SELECT * FROM " + myTableName;
		
		Vector allDataRetrieved = getSelectQueryResult(query);
		
		System.out.println(query + "\n");

		if (allDataRetrieved != null)
		{
			list = new Vector<InventoryItemType>();

			for (int i = 0; i < allDataRetrieved.size(); i++)
			{
				Properties nextInventoryItemTypeData = (Properties)allDataRetrieved.elementAt(i);

				InventoryItemType inventoryItemType = new InventoryItemType(nextInventoryItemTypeData);

				if (inventoryItemType != null)
				{
					addIIT(inventoryItemType);
				}
				
			}

		}
	}
	
	private void addIIT(InventoryItemType a)
	{
		//list.add(a);
		int index = findIndexToAdd(a);
		list.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(InventoryItemType a)
	{
		//users.add(u);
		int low=0;
		int high = list.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			InventoryItemType midSession = list.elementAt(middle);

			int result = InventoryItemType.compare(a,midSession);

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
	public InventoryItemType retrieve(String inventoryItemTypeName)
	{
		InventoryItemType retValue = null;
		for (int cnt = 0; cnt < list.size(); cnt++)
		{
			InventoryItemType nextInventoryItemType = list.elementAt(cnt);
			String nextInventoryItemTypeName = (String)nextInventoryItemType.getState("ItemTypeName");
			if (nextInventoryItemTypeName.equals(inventoryItemTypeName) == true)
			{
				retValue = nextInventoryItemType;
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
