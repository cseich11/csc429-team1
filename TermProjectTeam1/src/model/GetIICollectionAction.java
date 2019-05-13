// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

//==============================================================
public class GetIICollectionAction extends Action
{
	// GUI Components

	private String actionErrorMessage = "";
		
	private InventoryItemCollection ic;


	
	//----------------------------------------------------------
	public GetIICollectionAction()
		throws Exception
	{
		super();
	}


	protected void setDependencies() {
		
		dependencies = new Properties();
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("SearchII", "ActionError");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("IIData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
		
	}


	protected Scene createView() {

		Scene currentScene = myViews.get("InventoryItemCollectionView");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("InventoryItemCollectionView", this);
			currentScene = new Scene(newView);
			myViews.put("InventoryItemCollectionView", currentScene);

			return currentScene;
		}
		else
		{	
			return currentScene;
		}

		// create our initial view
//		View newView = ViewFactory.createView("InventoryItemCollectionView", this);
//		currentScene = new Scene(newView);
//		myViews.put("InventoryItemCollectionView", currentScene);
//
//		return currentScene;
	}


	public Object getState(String key) {
		
//		if (key.equals("ActionError"))
//			return actionErrorMessage;
//		if(key.equals("InventoryItemTypeList"))
//			return list;
//		if(key.equals("showSubmitButton"))
//			return false;
//		if(key.equals("IITData"))
//		{
//			String[] iitData = {itemTypeName, units, unitMeasure, validityDays, reorderPoint, notes, status};
//			return iitData;
//		}
//		if (iit != null)
//			return iit.getState(key);
//		if(key.equals("InventoryItemTypeList"))
//		{
////			System.out.println("test");
//			ic = new InventoryItemCollection();
//			ic.findAllII();
//			return ic;
//		}
		
//		ic = new InventoryItemCollection();
		
		return null;
	}

	public void stateChangeRequest(String key, Object value) {
		
		if(key.equals("DoYourJob"))
			doYourJob();
//		else if(key.equals("IITData"))
//			processAction((String[])value);
		else if(key.equals("CancelInventoryItemList"))
			swapToView(createView());
		
		myRegistry.updateSubscribers(key, this);
		
	}
}