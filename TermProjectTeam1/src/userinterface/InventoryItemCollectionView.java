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
import javafx.scene.input.MouseEvent;
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

//==============================================================================
public class InventoryItemCollectionView extends View
{
    protected TableView<InventoryItemTableModel> tableOfIITs;
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
            InventoryItemCollection iitCollection = (InventoryItemCollection)myModel.getState("InventoryItemList");

            Vector entryList = (Vector)iitCollection.getState("InventoryItems");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                InventoryItem nextInventoryItem = (InventoryItem)entries.nextElement();
                Vector<String> view = nextInventoryItem.getEntryListView();

                // add this list entry to the list
                InventoryItemTableModel nextTableRowData = new InventoryItemTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfIITs.setItems(tableData);
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
        unitMeasureText.setWrappingWidth(300);
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

        tableOfIITs = new TableView<InventoryItemTableModel>();
        tableOfIITs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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

//		TableColumn actionsColumn = new TableColumn("Actions");
//		actionsColumn.setMinWidth(100);


        tableOfIITs.getColumns().addAll(barcodeColumn,
                inventoryItemTypeNameColumn, vendorColumn, dateRecievedColumn, dateLastUsedColumn,
                notesColumn, statusColumn);

        tableOfIITs.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=1 ){
                    addButtonsForSelected();
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfIITs);

        doneButton = new Button("Done");
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
                myModel.stateChangeRequest("CancelInventoryItemList", null);
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
    protected void addButtonsForSelected()
    {
        InventoryItemTableModel selectedItem = tableOfIITs.getSelectionModel().getSelectedItem();

        if(selectedItem != null && !sub)
        {
            if(btnContainer.getChildren().contains(modifyButton) && btnContainer.getChildren().contains(deleteButton)) {
                btnContainer.getChildren().remove(modifyButton);
                btnContainer.getChildren().remove(deleteButton);
            }
            System.out.println(selectedItem.getBarcode());
            String selectedIITName = selectedItem.getBarcode();
            modifyButton = new Button("Modify");
            modifyButton.setOnAction(e -> {
                myModel.stateChangeRequest("ModifyIIT", selectedIITName);
            });
            deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                myModel.stateChangeRequest("ConfirmDeleteIIT", selectedIITName);
            });
            btnContainer.getChildren().addAll(modifyButton, deleteButton);

            myModel.stateChangeRequest("IITSelected", selectedIITName);

        }

        if(selectedItem != null && sub)
        {
            if(btnContainer.getChildren().contains(submitButton)) {
                btnContainer.getChildren().remove(submitButton);

            }
            System.out.println(selectedItem.getBarcode());
            String selectedIITName = selectedItem.getBarcode();
            submitButton = new Button("SUBMIT");
            submitButton.setOnAction(e -> {
                myModel.stateChangeRequest("SubmitIIT", selectedIITName);
            });

            btnContainer.getChildren().addAll(submitButton);

            myModel.stateChangeRequest("IITSelected", selectedIITName);

        }
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
	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processBookSelected();
		}
	}
   */

}
