package model;
	
	//system imports
	import java.sql.SQLException;
	import java.util.Enumeration;
	import java.util.Properties;
	import java.util.Vector;
	import javax.swing.JFrame;
	
	//project imports
	import exception.InvalidPrimaryKeyException;
	import database.*;
import event.Event;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
	import userinterface.ViewFactory;
import userinterface.WindowPosition;
	
	/** The class containing the Book for the application */
	//==============================================================
	public class VendorInventoryItemType extends EntityBase implements IView
	{
		private static final String myTableName = "VendorInventoryItemType";
		
		public Scene prevScene = myStage.getScene();
	
		protected Properties dependencies;
	
		// GUI Components
	
		private String updateStatusMessage = "";
	
		
		
		// constructor for this class
		//----------------------------------------------------------
		public VendorInventoryItemType(String viitId)
				throws InvalidPrimaryKeyException
			{
				super(myTableName);
		
				setDependencies();
				String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + viitId + ")";
		
				Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		
				// You must get one viit at least
				if (allDataRetrieved != null)
				{
					int size = allDataRetrieved.size();
		
					// There should be EXACTLY one viit. More than that is an error
					if (size != 1)
					{
						throw new InvalidPrimaryKeyException("Multiple VIIT's matching id : "
							+ viitId + " found.");
					}
					else
					{
						// copy all the retrieved data into persistent state
						Properties retrievedVIITData = allDataRetrieved.elementAt(0);
						persistentState = new Properties();
						System.out.println(retrievedVIITData);
		
						Enumeration allKeys = retrievedVIITData.propertyNames();
						while (allKeys.hasMoreElements() == true)
						{
							String nextKey = (String)allKeys.nextElement();
							String nextValue = retrievedVIITData.getProperty(nextKey);
							// accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));
		
							if (nextValue != null)
							{
								persistentState.setProperty(nextKey, nextValue);
							}
						}
		
					}
				}
				// If no viit found for this viitId, throw an exception
				else
				{
					throw new InvalidPrimaryKeyException("No VIIT matching id : "
						+ viitId + " found.");
				}
			}
		
		public VendorInventoryItemType(String vendorId, String itemTypeName)
				throws InvalidPrimaryKeyException
			{
				super(myTableName);
		
				setDependencies();
				String query = "SELECT * FROM " + myTableName + " WHERE (VendorId = " + vendorId + ") && (InventoryItemTypeName = " + itemTypeName + ")";
		
				Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		
				// You must get one viit at least
				if (allDataRetrieved != null)
				{
					int size = allDataRetrieved.size();
		
					// There should be EXACTLY one viit. More than that is an error
					if (size != 1)
					{
						throw new InvalidPrimaryKeyException("Multiple VIIT's matching id : "
							+ vendorId + " and " + itemTypeName + " found.");
					}
					else
					{
						// copy all the retrieved data into persistent state
						Properties retrievedVIITData = allDataRetrieved.elementAt(0);
						persistentState = new Properties();
						System.out.println(retrievedVIITData);
		
						Enumeration allKeys = retrievedVIITData.propertyNames();
						while (allKeys.hasMoreElements() == true)
						{
							String nextKey = (String)allKeys.nextElement();
							String nextValue = retrievedVIITData.getProperty(nextKey);
							// accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));
		
							if (nextValue != null)
							{
								persistentState.setProperty(nextKey, nextValue);
							}
						}
		
					}
				}
				// If no viit found for this viitId, throw an exception
				else
				{
					throw new InvalidPrimaryKeyException("No VIIT matching id : "
						+ vendorId + " and " + itemTypeName + " found.");
				}
			}
	
		// Can also be used to create a NEW Book (if the system it is part of
		// allows for a new book to be set up)
		//----------------------------------------------------------
		public VendorInventoryItemType(Properties props)
		{
			super(myTableName);
			
			setDependencies();
			persistentState = new Properties();
			Enumeration allKeys = props.propertyNames();
			while (allKeys.hasMoreElements() == true)
			{
				String nextKey = (String)allKeys.nextElement();
				String nextValue = props.getProperty(nextKey);
	
				if (nextValue != null)
				{
					persistentState.setProperty(nextKey, nextValue);
				}
			}
		}
		
//		public Book()
//		{
//			super(myTableName);
//			
//			setDependencies();
//			persistentState = new Properties();
//		}
		
		//------------------------------------------------------------
		public void createAndShowVIITView()
		{
			
			Scene currentScene = (Scene)myViews.get("VIITView");

			if (currentScene == null)
			{
				// create our initial view
				View newView = ViewFactory.createView("VIITView", this); // USE VIEW FACTORY
				currentScene = new Scene(newView);
				myViews.put("IITView", currentScene);
			}
					
			swapToView(currentScene);
			
		}

	
		public void swapToView(Scene newScene)
		{

			
			if (newScene == null)
			{
				System.out.println("VendorInventoryItemType.swapToView(): Missing view for display");
				new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Missing view for display ", Event.ERROR);
				return;
			}

			myStage.setScene(newScene);
			myStage.sizeToScene();
			
				
			//Place in center
			WindowPosition.placeCenter(myStage);

		}

//		@Override
//		public void stateChangeRequest(String key, Object value) {
//			// TODO Auto-generated method stub
			
//		}
		
		//-----------------------------------------------------------------------------------
		private void setDependencies()
		{
			dependencies = new Properties();
		
			myRegistry.setDependencies(dependencies);
		}
	
		//----------------------------------------------------------
		public Object getState(String key)
		{
			if (key.equals("UpdateStatusMessage") == true)
				return updateStatusMessage;
	
			return persistentState.getProperty(key);
		}
	
		//----------------------------------------------------------------
		public void stateChangeRequest(String key, Object value)
		{
	
			myRegistry.updateSubscribers(key, this);
		}
	
		/** Called via the IView relationship */
		//----------------------------------------------------------
		public void updateState(String key, Object value)
		{
			stateChangeRequest(key, value);
		}
	
	
		//----------------------------------------------------------
		public void processNewVIIT(Properties prop)
		{
			Enumeration allKeys = prop.propertyNames();
			while (allKeys.hasMoreElements() == true)
			{
				String nextKey = (String)allKeys.nextElement();
				String nextValue = prop.getProperty(nextKey);
	
				if (nextValue != null)
				{
					persistentState.setProperty(nextKey, nextValue);
				}
			}
			update();
		}
//	
////		/**
////		 * Credit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
////		 */
////		//----------------------------------------------------------
////		public void credit(String amount)
////		{
////			String myBalance = (String)getState("Balance");
////			double myBal = Double.parseDouble(myBalance);
////	
////			double incrementAmount = Double.parseDouble(amount);
////			myBal += incrementAmount;
////	
////			persistentState.setProperty("Balance", ""+myBal);
////		}
////	
////		/**
////		 * Debit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
////		 */
////		//----------------------------------------------------------
////		public void debit(String amount)
////		{
////			String myBalance = (String)getState("Balance");
////			double myBal = Double.parseDouble(myBalance);
////	
////			double incrementAmount = Double.parseDouble(amount);
////			myBal -= incrementAmount;
////	
////			persistentState.setProperty("Balance", ""+myBal);
////		}
////	
////		/**
////		 * Check balance -- returns true/false depending on whether
////		 * there is enough balance to cover withdrawalAmount or not
////		 * (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
////		 *
////		 */
////		//----------------------------------------------------------
////		public boolean checkBalance(String withdrawalAmount)
////		{
////			String myBalance = (String)getState("Balance");
////			double myBal = Double.parseDouble(myBalance);
////	
////			double checkAmount = Double.parseDouble(withdrawalAmount);
////	
////			if (myBal >= checkAmount)
////			{
////				return true;
////			}
////			else
////			{
////				return false;
////			}
////		}
////	
////		//----------------------------------------------------------
////		public void setServiceCharge(String value)
////		{
////			persistentState.setProperty("ServiceCharge", value);
////			updateStateInDatabase();
////		}
////		
////		//-----------------------------------------------------------------------------------
		public static int compare(VendorInventoryItemType a, VendorInventoryItemType b)
		{
			String aNum = (String)a.getState("ItemTypeName");
			String bNum = (String)b.getState("ItemTypeName");
	
			return aNum.compareTo(bNum);
		}
	
		//-----------------------------------------------------------------------------------
		public void update()
		{
			updateStateInDatabase();
		}
		
//		public void add()
//		{
//			addInDatabase();
//		}
		
		//-----------------------------------------------------------------------------------
		private void updateStateInDatabase() 
		{
			try
			{
				if (persistentState.getProperty("Id") != null)
				{
					Properties whereClause = new Properties();
					whereClause.setProperty("Id",persistentState.getProperty("Id"));
					updatePersistentState(mySchema, persistentState, whereClause);
					updateStatusMessage = "Data for VIIT number : " + persistentState.getProperty("Id") + " updated successfully in database!";
				}
				else
				{
					Integer viitId =
						insertAutoIncrementalPersistentState(mySchema, persistentState);
					persistentState.setProperty("Id", "" + viitId.intValue());
					updateStatusMessage = "data for new VIIT : " +  persistentState.getProperty("Id")
						+ " installed successfully in database!";
				}
			}
			catch (SQLException ex)
			{
				updateStatusMessage = "Error in installing VIIT in database!";
			}
//			 System.out.println(updateStatusMessage);
		}
		
//		private void addInDatabase() 
//		{
//			try
//			{
//				String itemTypeName = persistentState.getProperty("ItemTypeName");
//				String query = "SELECT * FROM " + myTableName + " WHERE (ItemTypeName = " + itemTypeName + ")";
//				
//				Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
//		
//				// You must get one book at least
//				if (allDataRetrieved != null)
//				{
//					Properties whereClause = new Properties();
//					whereClause.setProperty("ItemTypeName",persistentState.getProperty("ItemTypeName"));
//					updatePersistentState(mySchema, persistentState, whereClause);
//					updateStatusMessage = "Item: " + persistentState.getProperty("ItemTypeName") + " updated in database!";
//				}
//				else
//				{
//						insertPersistentState(mySchema, persistentState);
//					persistentState.setProperty("ItemTypeName", itemTypeName);
//					updateStatusMessage = "New Item: " +  persistentState.getProperty("ItemTypeName")
//						+ " added to database!";
//				}
//			}
//			catch (SQLException ex)
//			{
//				updateStatusMessage = "Error in installing IIT data in database!";
//			}
//			 System.out.println(updateStatusMessage);
//		}
	
	
		/**
		 * This method is needed solely to enable the IIT information to be displayable in a table
		 *
		 */
		//--------------------------------------------------------------------------
		public Vector<String> getEntryListView()
		{
			Vector<String> v = new Vector<String>();
			
			v.addElement(persistentState.getProperty("Id"));
			v.addElement(persistentState.getProperty("InventoryItemTypeName"));
			v.addElement(persistentState.getProperty("UnitMeasure"));
			v.addElement(persistentState.getProperty("ValidityDays"));
			v.addElement(persistentState.getProperty("ReorderPoint"));
			v.addElement(persistentState.getProperty("Notes"));
			v.addElement(persistentState.getProperty("Status"));
	
			return v;
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
	
