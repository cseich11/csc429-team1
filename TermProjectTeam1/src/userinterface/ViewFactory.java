package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {
	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("InventoryManagerView"))
			return new InventoryManagerView(model);
		else if(viewName.equals("AddVendorIITActionView"))
			return new AddVendorActionView(model);
		else if(viewName.equals("AddVendorActionView"))
			return new AddVendorActionView(model);
		else if(viewName.equals("SearchVendorActionView"))
			return new SearchVendorActionView(model);
		else if(viewName.equals("VendorCollectionView"))
			return new VendorCollectionView(model);
		else if(viewName.equals("ModifyVendorView"))
			return new ModifyVendorView(model);
		
		return null;
	}
}
