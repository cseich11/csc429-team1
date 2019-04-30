package model;

import event.Event;
import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class InventoryItem extends EntityBase implements IView {
    private static final String myTableName = "InventoryItem";
	
	public Scene prevScene = myStage.getScene();

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";

    //-----------------------------------------------------------------------------------
    //public InventoryItem(String barcode) throws InvalidPrimaryKeyException {
//        super(myTableName);
//
//        setDependencies();
//        String query = "SELECT * FROM " + myTableName + " WHERE Barcode = " + barcode + "";
//
//
//        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
//
//        System.out.println(query);
//        System.out.println(allDataRetrieved);
//        // You must get one book at least
//        if (allDataRetrieved != null) {
//
//            int size = allDataRetrieved.size();
//
//            // There should be EXACTLY one book. More than that is an error
//            if (size != 1) {
//                throw new InvalidPrimaryKeyException("Multiple inventory items matching barcode : "
//                        + barcode + " found.");
//            } else {
//                // copy all the retrieved data into persistent state
//                Properties retrievedIIData = allDataRetrieved.elementAt(0);
//                persistentState = new Properties();
//
//                Enumeration allKeys = retrievedIIData.propertyNames();
//                while (allKeys.hasMoreElements() == true) {
//                    String nextKey = (String) allKeys.nextElement();
//                    String nextValue = retrievedIIData.getProperty(nextKey);
//
//                    if (nextValue != null) {
//                        persistentState.setProperty(nextKey, nextValue);
//                    }
//                }
//
//            }
//        }
//        // If no book found for this user name, throw an exception
//        else {
//            throw new InvalidPrimaryKeyException("No inventory item matching barcode : "
//                    + barcode + " found.");
//        }
//    }
//    
//    public InventoryItem(Properties props)
//    {
//		super(myTableName);
//		
//		setDependencies();
//		persistentState = new Properties();
//		Enumeration allKeys = props.propertyNames();
//		while (allKeys.hasMoreElements() == true)
//		{
//			String nextKey = (String)allKeys.nextElement();
//			String nextValue = props.getProperty(nextKey);
//
//			if (nextValue != null)
//			{
//				persistentState.setProperty(nextKey, nextValue);
//			}
//		}
//	}
//
//    //-----------------------------------------------------------------------------------
//    private void setDependencies() {
//        dependencies = new Properties();
//
//        myRegistry.setDependencies(dependencies);
//    }
//
//    //----------------------------------------------------------
//    public Object getState(String key) {
//        if (key.equals("UpdateStatusMessage") == true)
//            return updateStatusMessage;
//
//        return persistentState.getProperty(key);
//    }
//
//    //----------------------------------------------------------------
//    public static int compare(Vendor a, Vendor b) {
//        String aNum = (String) a.getState("vId");
//        String bNum = (String) b.getState("vId");
//
//        return aNum.compareTo(bNum);
//    }
//
//    /**
//     * This method is needed solely to enable the Book information to be displayable in a table
//     */
//    //--------------------------------------------------------------------------
//    public Vector<String> getEntryListView() {
//        Vector<String> v = new Vector<String>();
//
//        v.addElement(persistentState.getProperty("Barcode"));
//        v.addElement(persistentState.getProperty("InventoryItemTypeName"));
//        v.addElement(persistentState.getProperty("DateRecieved"));
//        v.addElement(persistentState.getProperty("DateLastUsed"));
//        v.addElement(persistentState.getProperty("Notes"));
//        v.addElement(persistentState.getProperty("Status"));
//
//        return v;
//    }
//
//    //-----------------------------------------------------------------------------------
//    public void update()
//    {
//        updateStateInDatabase();
//    }
//
//    //-----------------------------------------------------------------------------------
//    private void updateStateInDatabase()
//    {
//        try
//        {
//        	String barcode = persistentState.getProperty("Barcode");
//        	String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = " + barcode + ")";
//        	
//        	Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
//        	
//        	// You must get one book at least
//			if (allDataRetrieved != null)
//			{
//				Properties whereClause = new Properties();
//				whereClause.setProperty("Barcode",persistentState.getProperty("Barcode"));
//				updatePersistentState(mySchema, persistentState, whereClause);
//				updateStatusMessage = "Item: " + persistentState.getProperty("Barcode") + " updated in database!";
//			}
//			else
//			{
//					insertPersistentState(mySchema, persistentState);
//				persistentState.setProperty("Barcode", barcode);
//				updateStatusMessage = "New Item: " +  persistentState.getProperty("Barcode")
//					+ " added to database!";
//			}
//        }
//        catch (SQLException ex)
//        {
//            updateStatusMessage = "Error in installing item data in database!";
//        }
//        System.out.println(updateStatusMessage);
//    }
//
//    /** Called via the IView relationship */
//    //----------------------------------------------------------
//    public void updateState(String key, Object value)
//    {
//        stateChangeRequest(key, value);
//    }
//
//    //-----------------------------------------------------------------------------------
//    protected void initializeSchema(String tableName)
//    {
//    	if (mySchema == null)
//        {
//            mySchema = getSchemaInfo(tableName);
//        }
//    }
//
//    public void stateChangeRequest(String key, Object value)
//    {
//    	myRegistry.updateSubscribers(key, this);
//    }
//    
    public String getStatus()
    {
    	return persistentState.getProperty("Status");
    }
	
		// GUI Components
	
		
		
		// constructor for this class
		//----------------------------------------------------------
		public InventoryItem(String barcode) throws InvalidPrimaryKeyException
		{
			super(myTableName);
	
			setDependencies();
			String query = "SELECT InventoryItem.*, InventoryItemType.ValidityDays, IF(ValidityDays = '-1' , 0 , DATE_ADD(DateRecieved, INTERVAL ValidityDays DAY) <= CURRENT_DATE) as CalcExpired FROM " + myTableName + " LEFT JOIN InventoryItemType ON InventoryItemTypeName = ItemTypeName WHERE (Barcode = " + barcode + ")";
	
			Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
			
			System.out.println(allDataRetrieved);
	
			// You must get one book at least
			if (allDataRetrieved != null)
			{
				int size = allDataRetrieved.size();
	
				// There should be EXACTLY one account. More than that is an error
				if (size != 1)
				{
					throw new InvalidPrimaryKeyException("Multiple InventoryItems matching : "
						+ barcode + " found.");
				}
				else
				{
					// copy all the retrieved data into persistent state
					Properties retrievedIITData = allDataRetrieved.elementAt(0);
					persistentState = new Properties();
	
					Enumeration allKeys = retrievedIITData.propertyNames();
					while (allKeys.hasMoreElements() == true)
					{
						String nextKey = (String)allKeys.nextElement();
						String nextValue = retrievedIITData.getProperty(nextKey);
	
						if (nextValue != null)
						{
							persistentState.setProperty(nextKey, nextValue);
						}
					}
	
				}
			}
			// If no item found for this barcode, throw an exception
			else
			{
				throw new InvalidPrimaryKeyException("No II matching : "
					+ barcode + " found.");
			}
		}
	
		// Can also be used to create a NEW Book (if the system it is part of
		// allows for a new book to be set up)
		//----------------------------------------------------------
		public InventoryItem(Properties props)
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
		
		//-----------------------------------------------------------------------------------
		public static int compare(InventoryItem a, InventoryItem b)
		{
			String aNum = (String)a.getState("Barcode");
			String bNum = (String)b.getState("Barcode");
	
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
				String barcode = (String)persistentState.getProperty("Barcode");
				String query = "SELECT InventoryItem.*, InventoryItemType.ValidityDays, IF(ValidityDays = '-1' , 0 , DATE_ADD(DateRecieved, INTERVAL ValidityDays DAY) <= CURRENT_DATE) as CalcExpired FROM " + myTableName + " LEFT JOIN InventoryItemType ON InventoryItemTypeName = ItemTypeName WHERE (Barcode = " + barcode + ")";
				
				Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
				
				// You must get one book at least
				if (allDataRetrieved != null && !allDataRetrieved.isEmpty())
				{
					Properties whereClause = new Properties();
					whereClause.setProperty("Barcode",persistentState.getProperty("Barcode"));
					System.out.println(persistentState);
					updatePersistentState(mySchema, persistentState, whereClause);
					updateStatusMessage = "Item: " + persistentState.getProperty("Barcode") + " updated in database!";
				}
				else
				{
						insertPersistentState(mySchema, persistentState);
					persistentState.setProperty("Barcode", barcode);
					updateStatusMessage = "New Item: " +  persistentState.getProperty("Barcode")
						+ " added to database!";
				}
			}
			catch (SQLException ex)
			{
				updateStatusMessage = "Error in installing Item in database!";
			}
//			 System.out.println(updateStatusMessage);
		}	
	
		/**
		 * This method is needed solely to enable the IIT information to be displayable in a table
		 *
		 */
		//--------------------------------------------------------------------------
		public Vector<String> getEntryListView()
		{
			Vector<String> v = new Vector<String>();
			
			v.addElement(persistentState.getProperty("Barcode"));
			v.addElement(persistentState.getProperty("InventoryItemTypeName"));
			v.addElement(persistentState.getProperty("Vendor"));
			v.addElement(persistentState.getProperty("DateReceived"));
			v.addElement(persistentState.getProperty("DateLastUsed"));
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
