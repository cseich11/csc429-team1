// specify the package
package userinterface;

// system imports
import java.util.Properties;
import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.Enumeration;

// project imports
import userinterface.VendorTableModel;
import model.Vendor;
import model.VendorCollection;
import model.InventoryManager;
import userinterface.MessageView;
import impresario.IModel;

/** The class containing the Teller View  for the ATM application */
//==============================================================
public class VendorCollectionView extends View
{

    // GUI stuff
	protected TableView<VendorTableModel> vendorTable;

    private Button doneButton, processInvoiceButton, addVIITButton, deleteVIITButton, modifyVendorButton;
    private Button selectVendorButton;
    private HBox btnContainer;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public VendorCollectionView( IModel model)
    {

        super(model, "VendorCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));
		container.setStyle("-fx-background-color: WHITESMOKE;"); 

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);
		
		populateFields();
    }
	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}
	
	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		
		ObservableList<VendorTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			VendorCollection vendorCollection = (VendorCollection)myModel.getState("VendorList");
	 		Vector entryList = (Vector)vendorCollection.getState("Vendor");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true)
			{
				Vendor nextVendor = (Vendor)entries.nextElement();
				Vector<String> view = nextVendor.getEntryListView();

				// add this list entry to the list
				VendorTableModel nextTableRowData = new VendorTableModel(view);
				tableData.add(nextTableRowData);				
			}
			vendorTable.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
			System.out.println("Error retrieving list of books");
		}
	}
	
    // Create the label (Text) for the title of the screen
    //-------------------------------------------------------------
    private Node createTitle()
    {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Vendor Collection ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
    }

    // Create the main form contents
    //-------------------------------------------------------------
    private VBox createFormContents()
    {
		VBox vbox = new VBox(10);
		
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(0);
        grid.setPadding(new Insets(25, 25, 0, 25));

        Text prompt = new Text("LIST OF VENDORS:");
		prompt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        prompt.setWrappingWidth(500);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0);
		
		vendorTable = new TableView<VendorTableModel>();
		vendorTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
		TableColumn vendorIdColumn = new TableColumn("Vendor ID") ;
		vendorIdColumn.setMinWidth(10);
		vendorIdColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("VendorId"));
		
		TableColumn nameColumn = new TableColumn("Name") ;
		nameColumn.setMinWidth(70);
		nameColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("name"));
		  
		TableColumn phoneColumn = new TableColumn("Phone Number") ;
		phoneColumn.setMinWidth(250);
		phoneColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("PhoneNum"));
		

		TableColumn statusColumn = new TableColumn("Status") ;
		statusColumn.setMinWidth(20);
		statusColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("status"));
		
		

		vendorTable.getColumns().addAll(vendorIdColumn, 
				 nameColumn, phoneColumn, statusColumn);

		vendorTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if (event.isPrimaryButtonDown() && event.getClickCount() >=1 ){
					addButtonsForSelected();
				}
			}
		});

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(275, 200);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(vendorTable);

        doneButton = new Button("DONE");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	 @Override
    		    public void handle(ActionEvent e) {
				/**
				 * Process the Cancel button.
				 * The ultimate result of this action is that the transaction will tell the teller to
				 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
				 * It simply tells its model (controller) that the transaction was canceled, and leaves it
				 * to the model to decide to tell the teller to do the switch back.
				 */
    		    	//----------------------------------------------------------
    		    	clearErrorMessage();
    		    	myModel.stateChangeRequest("CancelVendorList", null); 
    		    }
        });
        
		
		btnContainer = new HBox(10);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(doneButton);

		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);

        return vbox;
    }
	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}
	
	//--------------------------------------------------------------------------
	protected void addButtonsForSelected()
	{
		VendorTableModel selectedItem = vendorTable.getSelectionModel().getSelectedItem();
		
		if(selectedItem != null)
		{
			if(btnContainer.getChildren().contains(processInvoiceButton) && btnContainer.getChildren().contains(modifyVendorButton)
					&& btnContainer.getChildren().contains(addVIITButton) && btnContainer.getChildren().contains(deleteVIITButton)) 
			{
				btnContainer.getChildren().remove(processInvoiceButton);
				btnContainer.getChildren().remove(modifyVendorButton);
				btnContainer.getChildren().remove(addVIITButton);
				btnContainer.getChildren().remove(deleteVIITButton);
			}
			System.out.println(selectedItem.getVendorId());
			String selectedVendorID = selectedItem.getVendorId();
			
			processInvoiceButton = new Button("Process Invoice");
			processInvoiceButton.setOnAction(e -> {
				myModel.stateChangeRequest("ProcessInvoice", selectedVendorID);
			});
			modifyVendorButton = new Button("Modify Vendor");
			modifyVendorButton.setOnAction(e -> {
				myModel.stateChangeRequest("ModifyVendor", selectedVendorID);
			});
			btnContainer.getChildren().addAll(processInvoiceButton, modifyVendorButton);

			myModel.stateChangeRequest("IITSelected", selectedVendorID);
			
		}
	}

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }
    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}

