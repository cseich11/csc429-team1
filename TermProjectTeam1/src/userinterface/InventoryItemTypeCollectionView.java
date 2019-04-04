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
import model.InventoryItemType;
import model.InventoryItemTypeCollection;

//==============================================================================
public class InventoryItemTypeCollectionView extends View
{
	protected TableView<InventoryItemTypeTableModel> tableOfIITs;
	protected Button doneButton;

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public InventoryItemTypeCollectionView(IModel model)
	{
		super(model, "InventoryItemTypeCollectionView");
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
		
		ObservableList<InventoryItemTypeTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			InventoryItemTypeCollection bookCollection = (InventoryItemTypeCollection)myModel.getState("InventoryItemTypeList");

	 		Vector entryList = (Vector)bookCollection.getState("InventoryItemTypes");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true)
			{
				InventoryItemType nextInventoryItemType = (InventoryItemType)entries.nextElement();
				Vector<String> view = nextInventoryItemType.getEntryListView();

				// add this list entry to the list
				InventoryItemTypeTableModel nextTableRowData = new InventoryItemTypeTableModel(view);
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

		Text unitMeasureText = new Text(" Library System ");
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
        
        Text prompt = new Text("LIST OF BOOKS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		tableOfIITs = new TableView<InventoryItemTypeTableModel>();
		tableOfIITs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
		TableColumn nameColumn = new TableColumn("ItemTypeName") ;
		nameColumn.setMinWidth(100);
		nameColumn.setCellValueFactory(
	                new PropertyValueFactory<InventoryItemTypeTableModel, String>("itemTypeName"));
		
		TableColumn unitsColumn = new TableColumn("Units") ;
		unitsColumn.setMinWidth(100);
		unitsColumn.setCellValueFactory(
	                new PropertyValueFactory<InventoryItemTypeTableModel, String>("units"));
		  
		TableColumn unitMeasureColumn = new TableColumn("Unit Measure") ;
		unitMeasureColumn.setMinWidth(100);
		unitMeasureColumn.setCellValueFactory(
	                new PropertyValueFactory<InventoryItemTypeTableModel, String>("unitMeasure"));
		
		TableColumn validityDaysColumn = new TableColumn("ValidityDays") ;
		validityDaysColumn.setMinWidth(100);
		validityDaysColumn.setCellValueFactory(
	                new PropertyValueFactory<InventoryItemTypeTableModel, String>("validityDays"));
		
		TableColumn reorderPointColumn = new TableColumn("ReorderPoint") ;
		reorderPointColumn.setMinWidth(100);
		reorderPointColumn.setCellValueFactory(
	                new PropertyValueFactory<InventoryItemTypeTableModel, String>("reorderPoint"));
		
		TableColumn notesColumn = new TableColumn("Notes") ;
		notesColumn.setMinWidth(100);
		notesColumn.setCellValueFactory(
	                new PropertyValueFactory<TableModel, String>("reorderPoint"));
		
		TableColumn statusColumn = new TableColumn("Status") ;
		statusColumn.setMinWidth(100);
		statusColumn.setCellValueFactory(
	                new PropertyValueFactory<InventoryItemTypeTableModel, String>("status"));

		tableOfIITs.getColumns().addAll(nameColumn, 
				unitsColumn, unitMeasureColumn, validityDaysColumn, reorderPointColumn, statusColumn);

//		tableOfIITs.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event)
//			{
//				if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
//					processBookSelected();
//				}
//			}
//		});
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
       		    	myModel.stateChangeRequest("CancelBookList", null); 
       		    }
        	});

		HBox btnContainer = new HBox(100);
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
//	protected void processBookSelected()
//	{
//		BookTableModel selectedItem = tableOfIITs.getSelectionModel().getSelectedItem();
//		
//		if(selectedItem != null)
//		{
//			String selectedAcctNumber = selectedItem.getBookId();
//
//			myModel.stateChangeRequest("BookSelected", selectedAcctNumber);
//		}
//	}

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
