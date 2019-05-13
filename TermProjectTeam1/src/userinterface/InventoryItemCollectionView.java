package userinterface;

// system imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.InventoryItem;
import model.InventoryItemCollection;
import model.InventoryItemType;
import model.InventoryItemTypeCollection;

//==============================================================================
public class InventoryItemCollectionView extends View
{
    protected TableView<InventoryItemTableModel> tableOfIIs;
    protected Button doneButton, modifyButton, deleteButton, submitButton;
    protected HBox btnContainer;

    protected MessageView statusLog;
    public Boolean sub = (Boolean)myModel.getState("showSubmitButton");


    //--------------------------------------------------------------------------
    public InventoryItemCollectionView(IModel model)
    {
        super(model, "InventoryItemCollectionView");
        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

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
		
		ObservableList<InventoryItemTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			InventoryItemCollection iiCollection = (InventoryItemCollection)myModel.getState("InventoryItemList");
	 		Vector entryList = (Vector)iiCollection.getState("InventoryItems");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true)
			{
				InventoryItem nextInventoryItem = (InventoryItem)entries.nextElement();
				Vector<String> view = nextInventoryItem.getEntryListView();

				// add this list entry to the list
				InventoryItemTableModel nextTableRowData = new InventoryItemTableModel(view);
				tableData.add(nextTableRowData);
				
			}
			
			tableOfIIs.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}

    // Create the unitMeasure container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text unitMeasureText = new Text("       Restaurant Inventory System       ");
        unitMeasureText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        unitMeasureText.setWrappingWidth(725);
        unitMeasureText.setTextAlignment(TextAlignment.CENTER);
        unitMeasureText.setFill(Color.DARKGREEN);
        container.getChildren().add(unitMeasureText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF INVENTORY ITEMS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfIIs = new TableView<InventoryItemTableModel>();
        tableOfIIs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn barcodeColumn = new TableColumn("Barcode") ;
        barcodeColumn.setMinWidth(100);
        barcodeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("Barcode"));

        TableColumn inventoryItemTypeNameColumn = new TableColumn("InventoryItemTypeName") ;
        inventoryItemTypeNameColumn.setMinWidth(100);
        inventoryItemTypeNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("InventoryItemTypeName"));

        TableColumn vendorColumn = new TableColumn("Vendor") ;
        vendorColumn.setMinWidth(100);
        vendorColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("Vendor"));

        TableColumn dateRecievedColumn = new TableColumn("DateRecieved") ;
        dateRecievedColumn.setMinWidth(100);
        dateRecievedColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("DateRecieved"));

        TableColumn dateLastUsedColumn = new TableColumn("DateLastUsed") ;
        dateLastUsedColumn.setMinWidth(100);
        dateLastUsedColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("DateLastUsed"));

        TableColumn notesColumn = new TableColumn("Notes") ;
        notesColumn.setMinWidth(100);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("Notes"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryItemTableModel, String>("Status"));

        tableOfIIs.getColumns().addAll(barcodeColumn,
                inventoryItemTypeNameColumn, vendorColumn, dateRecievedColumn, dateLastUsedColumn,
                notesColumn, statusColumn);

        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(200, 175);
        scrollPane.setContent(tableOfIIs);

        doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	
            	clearErrorMessage();
    			myModel.stateChangeRequest("Cancel", null);
    			
            }
        });

        btnContainer = new HBox(100);
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
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
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
