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
public class SearchVendorAction extends Action
{
	// GUI Components

	private String actionErrorMessage = "";
	
	private String vId, vName, vPhone, vStatus, itemTypeNameSearched, notesSearched, notesEntered, barcodeEntered, iitNameEntered;
	
	private String vNameSearched;
	private String numberSearched;
	private String updateStatusMessage = "";
	
	private Vendor v;
	private InventoryItemType iit;
	private InventoryItemTypeCollection iitList;
	private VendorCollection vc;
	private InventoryItem ii;
	private VendorInventoryItemType viit;
	

	
	//----------------------------------------------------------
	public SearchVendorAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("SearchVendor", "ActionError");
		dependencies.setProperty("ProcessInvoice", "ActionError");
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("CancelSearchVendor", "CancelAction");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("VendorData", "UpdateStatusMessage");
		dependencies.setProperty("ModifyVendorData", "UpdateStatusMessage");
		dependencies.setProperty("SearchIIT", "ActionError");
		dependencies.setProperty("IITData", "UpdateStatusMessage");
		dependencies.setProperty("ModifyVendor", "ActionError");
		dependencies.setProperty("InvoiceData", "ActionError");
		dependencies.setProperty("InvoiceData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	
	//----------------------------------------------------------
	public void processActionSearchVender(String[] data)
	{
		if(data.length == 2 && data[0] != null && data[1] != null)
		{
			vc = new VendorCollection();
			vNameSearched = data[0]; numberSearched = data[1];
			vc.findVendors(vNameSearched, numberSearched);
			createAndShowVendorCollectionView();
		}
	}
	
	//----------------------------------------------------------
	public void processAction(String[] data)
	{
		if(data.length == 2 && data[0] != null && data[1] != null)
		{
			iitList = new InventoryItemTypeCollection();
			itemTypeNameSearched = data[0]; notesSearched = data[1];
			iitList.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
				
				
				
			createAndShowIITListView();
		}
	}
		
	
	/**
	 * This method encapsulates all the logic of updating the iit
	 */
	//----------------------------------------------------------
	public void processActionModify(Properties props)
	{

		vName = props.getProperty("vendorName");
		vPhone = props.getProperty("phoneNumber");
		vStatus = props.getProperty("status");

		if(vName != null && vPhone != null && vStatus != null)
		{
			v.persistentState.setProperty("vName", vName);
			v.persistentState.setProperty("vPhone", vPhone);
			v.persistentState.setProperty("vStatus", vStatus);
			v.update();
			updateStatusMessage = (String)v.getState("UpdateStatusMessage");
			//list = new InventoryItemTypeCollection();
			//list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			//createAndShowIITListView();
		}
	}
	
	
	//-----------------------------------------------------------
	public void processInvoiceAction(Properties props)
	{

		iitNameEntered = props.getProperty("i");
		barcodeEntered = props.getProperty("b");
		notesEntered = props.getProperty("n");
		
		try {
			viit = new VendorInventoryItemType(vId, iitNameEntered);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.now();
			String curDate = format.format(date);
			System.out.println(curDate);
			
			Properties prop = new Properties();
			prop.setProperty("Barcode", barcodeEntered);
			prop.setProperty("InventoryItemTypeName", iitNameEntered);
			prop.setProperty("Vendor", vId);
			prop.setProperty("DateRecieved", curDate);
			prop.setProperty("DateLastUsed", curDate);
			prop.setProperty("Notes", notesEntered);
			prop.setProperty("Status", "Available");
			ii = new InventoryItem(prop);
			ii.update();
			updateStatusMessage = (String)ii.getState("UpdateStatusMessage");
		} catch(InvalidPrimaryKeyException e) {
			actionErrorMessage = "Inventory Item Type does not exist for this Vendor";
		}
		
		
	}	


	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError") == true)
			return actionErrorMessage;
		else if(key.equals("VendorList"))
			return vc;
		else if(key.equals("VendorData") || key.equals("SearchVendor"))
		{
			String[] vData = {vName, vPhone};
			return vData;
		}
		else if(key.equals("UpdateStatusMessage"))
			return updateStatusMessage;
		else if (v != null)
			return v.getState(key);
		else if (iit != null)
			return iit.getState(key);
		else
			return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("DoYourJob"))
			doYourJob();
		
		else if (key.equals("VendorData"))
			processActionSearchVender((String[])value);
		
		else if(key.equals("CancelVendorList"))
			swapToView(createView());
		
		else if(key.equals("ModifyVendor")) {
			try{
				v = new Vendor((String)value);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createAndShowModifyVendorView();
		}
		
		else if(key.equals("CancelModify"))
			createAndShowVendorCollectionView();
		
		else if(key.equals("ModifyVendorData"))
			processActionModify((Properties)value);
		
		
		else if(key.equals("ProcessInvoice")) 
		{
			vId = (String) value;
			createAndShowSubmitInvoiceView();
		}
		
		else if(key.equals("InvoiceData")) 
		{
			processInvoiceAction((Properties)value);
		}
		
		else if(key.equals("ModifyVendorData"))
			processActionModify((Properties) value);
		
		else if(key.equals("AddVIIT"))
		{
			vId = (String) value;
		
			try {
				v = new Vendor(vId);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			createAndShowSearchIITActionView();
		}
		
		else if(key.equals("IITData"))
			processAction((String[]) value);
	
			
		myRegistry.updateSubscribers(key, this);
	}


	//------------------------------------------------------
	public Scene createView()
	{

		Scene currentScene = myViews.get("SearchVendorActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchVendorActionView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchVendorActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}


	//------------------------------------------------------
	protected Scene createAndShowView()
	{

		Scene currentScene = myViews.get("SearchVendorActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchVendorActionView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchVendorActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	//------------------------------------------------------
	 protected void createAndShowVendorCollectionView()
	    {

	        View newView = ViewFactory.createView("VendorCollectionView", this);
	        Scene localScene = new Scene(newView);
	        myViews.put("VendorCollectionView", localScene);
	        // make the view visible by installing it into the frame
	        swapToView(localScene);
			
	    }

	    //-----------------------------------------------------------------------------------
	
	
	//DELETE THIS HACK AFTER PRESENTING
	public void createAndShowViewModify()
	{
		Scene currentScene = (Scene)myViews.get("SearchVendorActionView");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchVendorActionView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("SearchVendorActionView", currentScene);
		}
				

		// make the view visible by installing it into the frame
		//swapToView(currentScene);


		myStage.setScene(currentScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);
	}
	
	//-----------------------------------------------------------
	protected void createAndShowModifyVendorView()
	{
		View newView = ViewFactory.createView("ModifyVendorView", this);
		Scene newScene = new Scene(newView);
		
		myViews.put("ModifyVendorView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}
	
	//-----------------------------------------------------------
	protected void createAndShowSubmitInvoiceView()
	{
		View newView = ViewFactory.createView("SubmitInvoiceView", this);
		Scene newScene = new Scene(newView);

		myViews.put("SubmitInvoiceView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}
	
	//-----------------------------------------------------------
	protected void createAndShowSearchIITActionView()
	{
		View newView = ViewFactory.createView("SearchIITActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("SearchIITActionView", currentScene);

		swapToView(currentScene);
	}
	
	//-----------------------------------------------------------
		protected void createAndShowIITListView()
		{
			View newView = ViewFactory.createView("InventoryItemTypeCollectionView", this);
			Scene currentScene = new Scene(newView);
			myViews.put("InventoryItemTypeCollectionView", currentScene);

			swapToView(currentScene);
		}
}