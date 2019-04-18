// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

//==============================================================
public class DeleteIIAction extends Action
{
	// GUI Components

	private String actionErrorMessage = "";
	private String IIUpdateStatusMessage = "";
	
	private String barcode;
	private String inventoryItemTypeName;
	private String vendor;
	private String dateRecieved;
	private String dateLastUsed;
	private String notes;
	private String status;
	
	private InventoryItem ii;
	
	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------
	public DeleteIIAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("DeleteII", "ActionError");
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("IIData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the book,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processAction(Properties props)
	{
		barcode = props.getProperty("Barcode");
		inventoryItemTypeName = props.getProperty("InventoryItemTypeName");
		vendor = props.getProperty("Vendor");
		dateRecieved = props.getProperty("DateRecieved");
		dateLastUsed = props.getProperty("DateLastUsed");
		notes = props.getProperty("Notes");
		status = props.getProperty("Status");
		
	}


	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError") == true)
		{
			return actionErrorMessage;
		}
		else
		if (key.equals("UpdateStatusMessage") == true)
		{
			return IIUpdateStatusMessage;
		}
		else if(key.equals("IITData"))
		{
			String[] iitData = {barcode, inventoryItemTypeName, vendor, dateRecieved, dateLastUsed, notes, status};
			return iitData;
		}
		
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("DoYourJob") == true)
		{
			doYourJob();
		}
		else
		if (key.equals("IIData") == true)
		{
			processAction((Properties)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the stage
	 */
	//------------------------------------------------------
	protected Scene createView()
	{

		Scene currentScene = myViews.get("DeleteIIActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("DeleteIIActionView", this);
			currentScene = new Scene(newView);
			myViews.put("DeleteIIActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}


	//------------------------------------------------------
	protected void createAndShowView()
	{
		View newView = ViewFactory.createView("DeleteIIActionView", this);
		Scene newScene = new Scene(newView);

		myViews.put("DeleteIIActionView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}

}