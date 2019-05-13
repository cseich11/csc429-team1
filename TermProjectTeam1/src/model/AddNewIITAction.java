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

/** The class containing the TransferAction for the ATM application */
//==============================================================
public class AddNewIITAction extends Action
{
	// GUI Components

	private String actionErrorMessage = "";
	private String IITUpdateStatusMessage = "";
	
	private String itemTypeName;
	private String units;
	private String unitMeasure;
	private String validityDays;
	private String reorderPoint;
	private String notes;
	
	private InventoryItemType iit;
	
	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------
	public AddNewIITAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("AddNewIIT", "ActionError");
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("IITData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the book,
	 * verifying ownership, crediting, etc. etc.
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

		if(itemTypeName != null && units != null && unitMeasure != null && validityDays!= null && reorderPoint != null && notes != null)
		{
			iit = new InventoryItemType(props);
			iit.add();
			IITUpdateStatusMessage = (String)iit.getState("UpdateStatusMessage");
		}
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
			return IITUpdateStatusMessage;
		}
		else if(key.equals("IITData"))
		{
			String[] iitData = {itemTypeName, units, unitMeasure, validityDays, reorderPoint, notes};
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
		if (key.equals("IITData") == true)
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

		Scene currentScene = myViews.get("AddNewIITActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("AddNewIITActionView", this);
			currentScene = new Scene(newView);
			myViews.put("AddNewIITActionView", currentScene);

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
		View newView = ViewFactory.createView("AddNewIITActionView", this);
		Scene newScene = new Scene(newView);

		myViews.put("AddNewIITActionView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}

}

