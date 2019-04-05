// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the SearchInventoryItemTypesAction for the Library application */
//==============================================================
public class SearchIITAction extends Action
{
	private InventoryItemTypeCollection list;

	// GUI Components
	private String name, notes;
	private String actionErrorMessage = "";
	private String inventoryUpdateStatusMessage = "";

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
			name = data[0]; notes = data[1];
			list.findAllIITWithNameNotes(name, notes);
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

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
		if(key.equals("UpdateStatusMessage"))
			return inventoryUpdateStatusMessage;
		if(key.equals("InventoryItemTypeList"))
			return list;
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
		else if(key.equals("ModifyIIT"))
			createAndShowModifyIITView();
		else if(key.equals("CancelModify"))
			swapToView(createView());

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

}

