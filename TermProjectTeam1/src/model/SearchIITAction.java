// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the SearchInventoryItemTypesAction for the Library application */
//==============================================================
public class SearchIITAction extends Action
{
	private InventoryItemTypeCollection list; 

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
	public SearchIITAction()
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
		dependencies.setProperty("ModifyIIT", "ActionError");
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
			list = new InventoryItemTypeCollection();
			itemTypeNameSearched = data[0]; notesSearched = data[1];
			list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			createAndShowIITListView();
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
			iitUpdateStatusMessage = (String)iit.getState("UpdateStatusMessage");
			list = new InventoryItemTypeCollection();
			list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			createAndShowIITListView();
		}
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
		if(key.equals("UpdateStatusMessage"))
			return inventoryUpdateStatusMessage;
		if(key.equals("InventoryItemTypeList"))
			return list;
		if(key.equals("IITData"))
		{
			String[] iitData = {itemTypeName, units, unitMeasure, validityDays, reorderPoint, notes, status};
			return iitData;
		}
		if (iit != null)
			return iit.getState(key);
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("SearchInventoryItemTypesAction.sCR: key: " + key);

		if(key.equals("DoYourJob"))
			doYourJob();
		else if(key.equals("IITData"))
			processAction((String[])value);
		else if(key.equals("CancelInventoryItemTypeList"))
			swapToView(createView());
		else if(key.equals("ModifyIIT")) {
			try {
				iit = new InventoryItemType((String)value);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createAndShowModifyIITView();
		}
		else if(key.equals("ModifyIITData"))
			processAction((Properties)value);
		else if(key.equals("ConfirmDeleteIIT")) {
			try {
				iit = new InventoryItemType((String)value);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createAndShowDeleteIITView();
		}
		else if(key.equals("CancelModify"))
			createAndShowIITListView();
		else if(key.equals("Delete"))
		{
			iit.persistentState.setProperty("Status", "Inactive");
			iit.update();
		}
		else if (key.equals("IITData") == true)
			processAction((Properties)value);

		myRegistry.updateSubscribers(key, this);
	}
	
	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	
//	public void deleteIIT()
//	{
//		iit.persistentState.setProperty("Status", "Inactive");
//		iit.update();
//	}
	
	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("SearchIITActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchIITActionView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchIITActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	protected void createAndShowIITListView()
	{
		View newView = ViewFactory.createView("InventoryItemTypeCollectionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("InventoryItemTypeCollectionView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowModifyIITView()
	{
		View newView = ViewFactory.createView("ModifyIITActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("ModifyIITActionView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowDeleteIITView()
	{
		View newView = ViewFactory.createView("DeleteIITActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("DeleteIITActionView", currentScene);

		swapToView(currentScene);
	}

}