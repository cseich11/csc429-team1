package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;

public class Vendor extends EntityBase implements IView {

    private static final String myTableName = "Vendor";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";

    public Vendor(String vendorId) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (vId = " + vendorId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one book at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple vnendors matching id : "
                        + vendorId + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);
                    // bookNumber = Integer.parseInt(retrievedBookData.getProperty("bookNumber"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no book found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No vendor matching id : "
                    + vendorId + " found.");
        }
    }

    public Vendor(Properties props)
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
    public static int compare(Vendor a, Vendor b)
    {
        String aNum = (String)a.getState("vendorId");
        String bNum = (String)b.getState("vendorId");

        return aNum.compareTo(bNum);
    }

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
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
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
            if (persistentState.getProperty("vendorId") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("vendorId",
                        persistentState.getProperty("vendorId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Vendor data for book id : " + persistentState.getProperty("vendorId") + " updated successfully in database!";
            }
            else
            {
                Integer vendorsId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("vendorId", "" + vendorsId.intValue());
                updateStatusMessage = "Vendor with ID: " +  persistentState.getProperty("vendorId")
                        + " inserted successfully!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing book data in database!";
        }
        System.out.println(updateStatusMessage);
    }

    /**
     * This method is needed solely to enable the Book information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("vId"));
        v.addElement(persistentState.getProperty("vName"));
        v.addElement(persistentState.getProperty("vPhone"));
        v.addElement(persistentState.getProperty("vStatus"));

        return v;
    }

}
