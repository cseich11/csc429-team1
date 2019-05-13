// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the SearchInventoryItemTypesAction for the Library application */
//==============================================================
public class ReorderListAction extends Action
{
	private InventoryItemTypeCollection iitList; 
	private InventoryItemTypeCollection reorderList;

	// GUI Components
	private String itemTypeName, notes, units, unitMeasure, validityDays, reorderPoint, status, itemTypeNameSearched, notesSearched;
	private String actionErrorMessage = "";
	private String inventoryUpdateStatusMessage = "";
	private String iitUpdateStatusMessage = "";
	
	private InventoryItemType iit;
	
	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------
	public ReorderListAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("SearchIIT", "ActionError");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("ModifyIITData", "ActionMessage");
		dependencies.setProperty("IITData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the account,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processAction(String[] data)
	{
		if(data.length == 2 && data[0] != null && data[1] != null)
		{
			iitList = new InventoryItemTypeCollection();
			itemTypeNameSearched = data[0]; notesSearched = data[1];
			iitList.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
//			createAndShowIITListView();
		}
//		if (props.getProperty("author") != null && props.getProperty("title") != null
//				&& props.getProperty("pubYear") != null && props.getProperty("status") != null)
//		{
//			book = new InventoryItemType(props);
//			book.update();
//			libraryUpdateStatusMessage = (String)book.getState("UpdateStatusMessage");
//			//createAndShowReceiptView();
//
//		}
	}
	
	/**
	 * This method encapsulates all the logic of updating the iit
	 */
	//----------------------------------------------------------
	public void processAction(Properties props)
	{

		itemTypeName = props.getProperty("ItemTypeName");
		units = props.getProperty("Units");
		unitMeasure = props.getProperty("UnitMeasure");
		validityDays = props.getProperty("ValidityDays");
		reorderPoint = props.getProperty("ReorderPoint");
		notes = props.getProperty("Notes");
		status = props.getProperty("Status");

		if(itemTypeName != null && units != null && unitMeasure != null && validityDays!= null && reorderPoint != null && notes != null && status != null)
		{
			iit.persistentState.setProperty("Units", units);
			iit.persistentState.setProperty("UnitMeasure", unitMeasure);
			iit.persistentState.setProperty("ValidityDays", validityDays);
			iit.persistentState.setProperty("ReorderPoint", reorderPoint);
			iit.persistentState.setProperty("Notes", notes);
			iit.persistentState.setProperty("Status", status);
			iit.update();
			inventoryUpdateStatusMessage = (String)iit.getState("UpdateStatusMessage");
			//list = new InventoryItemTypeCollection();
			//list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			//createAndShowIITListView();
		}
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
		if (key.equals("ActionMessage"))
			return inventoryUpdateStatusMessage;
		if(key.equals("UpdateStatusMessage"))
			return iitUpdateStatusMessage;
		if(key.equals("ReorderList"))
			return reorderList;
//		if(key.equals("IITData"))
//		{
//			String[] iitData = {itemTypeName, units, unitMeasure, validityDays, reorderPoint, notes, status};
//			return iitData;
//		}
		if (iit != null)
			return iit.getState(key);
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("SearchInventoryItemTypesAction.sCR: key: " + key);

		if(key.equals("DoYourJob"))
		{
			iitList = new InventoryItemTypeCollection();
			reorderList = new InventoryItemTypeCollection();
			iitList.findAllIIT();
			if (iitList.list != null)
			{

				for (int i = 0; i < iitList.list.size(); i++)
				{
					InventoryItemType nextInventoryItemType = iitList.list.elementAt(i);
				
					if (nextInventoryItemType != null)
					{
						InventoryItemCollection tempIICollection = new InventoryItemCollection();
						tempIICollection.findAllInventoryItemsWithName(nextInventoryItemType.persistentState.getProperty("ItemTypeName"));
						if((Vector<InventoryItem>)tempIICollection.getState("InventoryItems") != null && !((Vector<InventoryItem>)tempIICollection.getState("InventoryItems")).isEmpty())
						{
							double count = ((Vector<InventoryItem>)tempIICollection.getState("InventoryItems")).size();
							if(count <= Double.parseDouble(nextInventoryItemType.persistentState.getProperty("ReorderPoint")))
							{
								reorderList.addIIT(nextInventoryItemType);
							}
						}
					}
					
				}
			}
			createAndShowReorderListView();
		}
		else if(key.equals("Cancel"))
			 new InventoryManager();

		myRegistry.updateSubscribers(key, this);
	}
	
	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	
	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("SearchVIITActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchVIITActionView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchVIITActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	
	
	protected void createAndShowReorderListView()
	{
		View newView = ViewFactory.createView("ReorderListView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("ReorderListView", currentScene);

		swapToView(currentScene);
	}
	
	
//	protected void createAndShowDeleteVIITView()
//	{
//		View newView = ViewFactory.createView("DeleteVIITActionView", this);
//		Scene currentScene = new Scene(newView);
//		myViews.put("DeleteVIITActionView", currentScene);
//
//		swapToView(currentScene);
//	}

}