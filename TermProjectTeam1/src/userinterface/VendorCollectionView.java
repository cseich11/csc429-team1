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

    private Button doneButtonSearchBook;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public VendorCollectionView( IModel wsc)
    {

        super(wsc, "VendorCollectionView");

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
		System.out.println("11");
		try
		{
			System.out.println("1");
			VendorCollection vendorCollection = (VendorCollection)myModel.getState("VendorList");
			System.out.println("2");
	 		Vector entryList = (Vector)vendorCollection.getState("Vendor");
			System.out.println("3");
			Enumeration entries = entryList.elements();
			System.out.println("4");

			while (entries.hasMoreElements() == true)
			{
				Vendor nextVendor = (Vendor)entries.nextElement();
				System.out.println("5");
				Vector<String> view = nextVendor.getEntryListView();
				System.out.println("6");

				// add this list entry to the list
				VendorTableModel nextTableRowData = new VendorTableModel(view);
				System.out.println("7");
				tableData.add(nextTableRowData);
				System.out.println("8");
				
			}
			System.out.println("9");
			vendorTable.setItems(tableData);
			System.out.println("10");
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
        prompt.setFill(Color.GOLDENROD);
        grid.add(prompt, 0, 0);
		
		vendorTable = new TableView<VendorTableModel>();
		vendorTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
		TableColumn vendorIdColumn = new TableColumn("Vendor ID") ;
		vendorIdColumn.setMinWidth(20);
		vendorIdColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("vID"));
		
		TableColumn authorColumn = new TableColumn("Name") ;
		authorColumn.setMinWidth(70);
		authorColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("vName"));
		  
		TableColumn titleColumn = new TableColumn("Title") ;
		titleColumn.setMinWidth(250);
		titleColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("title"));
		
		TableColumn pubYearColumn = new TableColumn("Publication Year") ;
		pubYearColumn.setMinWidth(20);
		pubYearColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("pubYear"));
					
		TableColumn statusColumn = new TableColumn("Status") ;
		statusColumn.setMinWidth(20);
		statusColumn.setCellValueFactory(
	                new PropertyValueFactory<VendorTableModel, String>("status"));

		vendorTable.getColumns().addAll(vendorIdColumn, 
				authorColumn, titleColumn, pubYearColumn, statusColumn);

		vendorTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
					processVendorSelected();
				}
			}
		});
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(190, 200);
		scrollPane.setContent(vendorTable);

        doneButtonSearchBook = new Button("DONE");
        doneButtonSearchBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	InventoryManager newLibrarian = new InventoryManager();
            }
        });
        // HBox btnContainer4 = new HBox(10);
        // btnContainer4.setAlignment(Pos.BOTTOM_CENTER);
        // btnContainer4.getChildren().add(doneButtonSearchBook);
        // grid.add(btnContainer4, 0, 5);
		
		HBox btnContainer = new HBox(10);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(doneButtonSearchBook);
		
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
	
	protected void processVendorSelected()
	{
		VendorTableModel selectedItem = vendorTable.getSelectionModel().getSelectedItem();
		
		if(selectedItem != null)
		{
			String selectedVendorId = selectedItem.getVendorId();

			myModel.stateChangeRequest("selectedVendor", selectedVendorId);
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

