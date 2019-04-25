// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the SearchInventoryItemAction */
//==============================================================
public class SearchIIAction extends Action
{
	private static final String myTableName = "InventoryItem";
	//private InventoryItemTypeCollection list; 

	// GUI Components
	private String barcode = "";
	private String actionErrorMessage = "";
//	private String inventoryUpdateStatusMessage = "";
//	private String iiUpdateStatusMessage = "";
	
	private InventoryItem ii;
	
	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------
	public SearchIIAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("SearchII", "ActionError");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("IIData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of updating the ii
	 */
	//----------------------------------------------------------
	public void processAction(Properties props)
	{
		if(props.getProperty("Status").equals("Available"))
			createAndShowDeleteIIView();
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
		if(key.equals("IIData"))
			return barcode;
		
		if (ii != null)
			return ii.getState(key);
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if(key.equals("DoYourJob"))
			doYourJob();
		else if(key.equals("IIData"))
			processAction((Properties)value);
		else if(key.equals("GetII"))
		{
			try {
				ii = new InventoryItem((String)value);
			} catch (InvalidPrimaryKeyException e) {
				e.printStackTrace();
			}
			if(ii.getStatus().equals("Available"))
				createAndShowDeleteIIView();
			else
				System.out.println("ERRORROROROROR");
		}
		else if(key.equals("IIDelete"))
			processActionDelete((Properties)value);

		myRegistry.updateSubscribers(key, this);
	}
	
	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}
	
	public void processActionDelete(Properties props)
	{
		ii.persistentState.setProperty("Status", "Used");
		ii.update();
	}
	
	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("SearchIIActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchIIActionView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchIIActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	protected void createAndShowDeleteIIView()
	{
		View newView = ViewFactory.createView("DeleteIIActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("DeleteIIActionView", currentScene);

		swapToView(currentScene);
	}
}