// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;

/** The class containing the Deposit Amount View  for the Library application */
//==============================================================
public class SearchIITForVIITView extends View
{

	// Model

	// GUI components
	private TextField nameSearch, notesSearch;

	private Button submitButton;
	private Button cancelButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchIITForVIITView(IModel action)
	{
		super(action, "AddVIITAction");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

		populateFields();
		
		myModel.subscribe("UpdateStatusMessage", this);
	}


	// Create the label (Text) for the title
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		Text titleText = new Text("       Restaurant Inventory Inventory Item Type Search          ");
		titleText.setWrappingWidth(300);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
		return titleText;
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

		Label nameSearchLabel = new Label("Search name: ");
		grid.add(nameSearchLabel, 0, 0);

		nameSearch = new TextField();
		nameSearch.setOnAction(e -> {
			processAction(e);
		});
		grid.add(nameSearch, 1, 0);
		
		Label notesSearchLabel = new Label("Search notes: ");
		grid.add(notesSearchLabel, 0, 1);

		notesSearch = new TextField();
		notesSearch.setOnAction(e -> {
			processAction(e);
		});
		grid.add(notesSearch, 1, 1);

		submitButton = new Button("Submit");
 		submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the search
			processAction(e);
        });

		cancelButton = new Button("Back");
 		cancelButton.setOnAction(e -> {
 			clearErrorMessage();
			myModel.stateChangeRequest("Cancel", null); 
        });

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		nameSearch.setText("");
		notesSearch.setText("");
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		// DEBUG: System.out.println("DepositAmountView.processAction()");

		clearErrorMessage();

		String[] dataEntered = {nameSearch.getText(), notesSearch.getText()};
		
//		if(titleEntered == null || titleEntered.length() == 0)
//			displayErrorMessage("Please enter a title");
//		else
//		{
			processData(dataEntered);
//		}
	}

	/**
	 * Process amount entered by user.
	 * Action is to pass this info on to the action object.
	 */
	//----------------------------------------------------------
	private void processData(String[] data)
	{
		myModel.stateChangeRequest("IITData", data);
	}

	

	/**
	 * Required by interface
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if(key.equals("UpdateStatusMessage"))
		{
			String msg = (String)value;
			displayMessage(msg);
		}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
	
	/**
	 * Display message
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