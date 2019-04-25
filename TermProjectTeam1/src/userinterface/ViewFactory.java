package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {
	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("InventoryManagerView"))
			return new InventoryManagerView(model);
		if(viewName.equals("AddNewIITActionView"))
			return new AddNewIITActionView(model);
		if(viewName.equals("SearchIITActionView"))
			return new SearchIITActionView(model);
		if(viewName.equals("SearchIIActionView"))
			return new SearchIIActionView(model);
		if(viewName.equals("InventoryItemTypeCollectionView"))
			return new InventoryItemTypeCollectionView(model);
		if(viewName.equals("ModifyIITActionView"))
			return new ModifyIITActionView(model);
		if(viewName.equals("DeleteIITActionView"))
			return new DeleteIITActionView(model);
		if(viewName.equals("DeleteIIActionView"))
			return new DeleteIIActionView(model);
		if(viewName.equals("AddVendorIITActionView"))
			return new AddVendorActionView(model);
		if(viewName.equals("AddVendorActionView"))
			return new AddVendorActionView(model);
		if(viewName.equals("SearchVendorActionView"))
			return new SearchVendorActionView(model);
		if(viewName.equals("VendorCollectionView"))
			return new VendorCollectionView(model);
		if(viewName.equals("ModifyVendorView"))
			return new ModifyVendorView(model);
		if(viewName.equals("ProcessInvoiceActionView"))
			return new ProcessInvoiceActionView(model);
		if(viewName.equals("SubmitInvoiceView"))
			return new SubmitInvoiceView(model);
		if(viewName.equals("IITCollectionForVIITView"))
			return new IITCollectionForVIITView(model);
		if(viewName.equals("VendorCollectionForVIITView"))
			return new VendorCollectionForVIITView(model);
		if(viewName.equals("SearchIITForVIITView"))
			return new SearchIITForVIITView(model);
		if(viewName.equals("SearchVendorsForVIITView"))
			return new SearchVendorsForVIITView(model);
		if(viewName.equals("ConfirmDeleteVIITView"))
			return new ConfirmDeleteVIITView(model);
		if(viewName.equals("PriceView"))
			return new PriceView(model);
		return null;
	}
}
