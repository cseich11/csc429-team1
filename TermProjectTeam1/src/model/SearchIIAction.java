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
	private int barcode = 0;
	private String actionErrorMessage = "";
//	private String inventoryUpdateStatusMessage = "";
	private String iiUpdateStatusMessage = "";
	
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
	 * This method encapsulates all the logic of creating the account,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processAction(String bCode)
	{
		if(bCode.length() != 0)
			createAndShowDeleteIIView();
	}
	
	/**
	 * This method encapsulates all the logic of updating the ii
	 */
	//----------------------------------------------------------
	public void processAction(Properties props)
	{

		barcode = props.getProperty("Barcode");

		if(barcode != 0)
		{
			ii.persistentState.setProperty("Barcode", barcode);
			ii.update();
//			inventoryUpdateStatusMessage = (String)ii.getState("UpdateStatusMessage");
		}
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
//		if (key.equals("ActionMessage"))
//			return inventoryUpdateStatusMessage;
		if(key.equals("IIData"))
			return barcode;
		
		if (ii != null)
			return ii.getState(key);
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("SearchInventoryItemTypesAction.sCR: key: " + key);

		if(key.equals("DoYourJob"))
			doYourJob();
		else if(key.equals("IIData"))
			processAction((String)value);
		else if(key.equals("GetII"))
		{
			try {
				ii = new InventoryItem((String)value);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(ii.persistentState.getProperty("InventoryItemTypeName"));
			getInventoryItem();
		}
		else if (key.equals("IIData") == true)
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
		System.out.println("test");
		View newView = ViewFactory.createView("DeleteIIActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("DeleteIIActionView", currentScene);

		swapToView(currentScene);
	}
	
	protected void getInventoryItem()
	{
		
	}
	
}