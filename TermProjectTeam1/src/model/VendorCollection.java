// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the BookCollection for the Library application */
//==============================================================
public class VendorCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Vendor";

    private Vector<Vendor> vendorList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public VendorCollection(String name, String phone)
    {
        super(myTableName);
		
		vendorList = new Vector<Vendor>(); // new Vector<Book>();
		vendorList = findVendors(name, phone);
		System.out.println(vendorList);
		
    }

    //----------------------------------------------------------------------------------
    public Vector<Vendor> findVendors(String name, String phone){
    
	   String query = "SELECT * FROM " + myTableName + " WHERE vName LIKE '%" + name + "%' AND vPhone LIKE '%" + phone + "%'";
		
		//SELECT * FROM Vendor WHERE vName LIKE "%Jake%" AND vPhone LIKE "%999999999%"
       
	   Vector allDataRetrieved = getSelectQueryResult(query);

       System.out.println(query + "\n");

        if (allDataRetrieved != null)
        {
            vendorList = new Vector<Vendor>();

            for (int i = 0; i < allDataRetrieved.size(); i++)
            {
                Properties nextVendorData = (Properties)allDataRetrieved.elementAt(i);

                Vendor vendor = new Vendor(nextVendorData);

                if (vendor != null)
                {
                    addVendor(vendor);
                }

                System.out.println(nextVendorData.getProperty("vId") + "\t\t" + nextVendorData.getProperty("vName") + "\t\t"
                        + nextVendorData.getProperty("vPhone") + "\t\t" + nextVendorData.getProperty("vStatus"));
            }
        }

        return (vendorList);
    }

    private void addVendor(Vendor a)
    {
        //bookList.add(a);
        int index = findIndexToAdd(a);
        vendorList.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Vendor a)
    {
        //users.add(u);
        int low=0;
        int high = vendorList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Vendor midSession = vendorList.elementAt(middle);

            int result = Vendor.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Vendor"))
            return vendorList;
        else
        if (key.equals("VendorList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Vendor retrieve(String vId)
    {
        Vendor retValue = null;
        for (int cnt = 0; cnt < vendorList.size(); cnt++)
        {
            Vendor nextVendor = vendorList.elementAt(cnt);
            String nextBookId = (String)nextVendor.getState("vId");
            if (nextBookId.equals(vId) == true)
            {
                retValue = nextVendor;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowVendorCollectionView()
    {

        Scene localScene = myViews.get("VendorCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("VendorCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("VendorCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);
		
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public Vendor getVendor(String name)
    {
        for (int cnt = 0; cnt < vendorList.size(); cnt++)
        {
            Vendor nextVendor = vendorList.elementAt(cnt);
            if (name.equals((String)nextVendor.getState("vName")))
            {
                return nextVendor;
            }
        }
        return (null);
    }
}