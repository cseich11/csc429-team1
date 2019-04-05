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
	public class InventoryItemType extends EntityBase implements IView
	{
		private static final String myTableName = "InventoryItemType";
		
		public Scene prevScene = myStage.getScene();
	
		protected Properties dependencies;
	
		// GUI Components
	
		private String updateStatusMessage = "";
	
		
		
		// constructor for this class
		//----------------------------------------------------------
		public InventoryItemType(String itemTypeName)
			throws InvalidPrimaryKeyException
		{
			super(myTableName);
	
			setDependencies();
			String query = "SELECT * FROM " + myTableName + " WHERE (ItemTypeName = " + itemTypeName + ")";
	
			Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
	
			// You must get one book at least
			if (allDataRetrieved != null)
			{
				int size = allDataRetrieved.size();
	
				// There should be EXACTLY one account. More than that is an error
				if (size != 1)
				{
					throw new InvalidPrimaryKeyException("Multiple InventoryItemTypes matching : "
						+ itemTypeName + " found.");
				}
				else
				{
					// copy all the retrieved data into persistent state
					Properties retrievedIITData = allDataRetrieved.elementAt(0);
					persistentState = new Properties();
					System.out.println(retrievedIITData);
	
					Enumeration allKeys = retrievedIITData.propertyNames();
					while (allKeys.hasMoreElements() == true)
					{
						String nextKey = (String)allKeys.nextElement();
						String nextValue = retrievedIITData.getProperty(nextKey);
						// accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));
	
						if (nextValue != null)
						{
							persistentState.setProperty(nextKey, nextValue);
						}
					}
				}
			}
			// If no book found for this bookId, throw an exception
			else
			{
				throw new InvalidPrimaryKeyException("No IIT matching : "
					+ itemTypeName + " found.");
			}
		}
	
		// Can also be used to create a NEW Book (if the system it is part of
		// allows for a new book to be set up)
		//----------------------------------------------------------
		public InventoryItemType(Properties props)
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
		public void createAndShowIITView()
		{
			
			Scene currentScene = (Scene)myViews.get("IITView");

			if (currentScene == null)
			{
				// create our initial view
				View newView = ViewFactory.createView("IITView", this); // USE VIEW FACTORY
				currentScene = new Scene(newView);
				myViews.put("IITView", currentScene);
			}
					
			swapToView(currentScene);
			
		}

	
		public void swapToView(Scene newScene)
		{
			if (newScene == null)
			{
				System.out.println("InventoryItemType.swapToView(): Missing view for display");
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
		public void processNewIIT(Properties prop)
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
		public static int compare(InventoryItemType a, InventoryItemType b)
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
		
		//-----------------------------------------------------------------------------------
		private void updateStateInDatabase() 
		{
			try
			{
				String itemTypeName = persistentState.getProperty("ItemTypeName");
				String query = "SELECT * FROM " + myTableName + " WHERE (ItemTypeName = " + itemTypeName + ")";
				
				Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		
				// You must get one book at least
				if (allDataRetrieved != null)
				{
					Properties whereClause = new Properties();
					whereClause.setProperty("ItemTypeName",persistentState.getProperty("ItemTypeName"));
					updatePersistentState(mySchema, persistentState, whereClause);
					updateStatusMessage = "data for IIT : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
				}
				else
				{
						insertPersistentState(mySchema, persistentState);
					persistentState.setProperty("ItemTypeName", itemTypeName);
					updateStatusMessage = "data for new IIT : " +  persistentState.getProperty("ItemTypeName")
						+ " installed successfully in database!";
				}
			}
			catch (SQLException ex)
			{
				updateStatusMessage = "Error in installing IIT data in database!";
			}
			 System.out.println(updateStatusMessage);
		}
	
	
		/**
		 * This method is needed solely to enable the IIT information to be displayable in a table
		 *
		 */
		//--------------------------------------------------------------------------
		public Vector<String> getEntryListView()
		{
			Vector<String> v = new Vector<String>();
			
			v.addElement(persistentState.getProperty("ItemTypeName"));
			v.addElement(persistentState.getProperty("Units"));
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
	
