// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Deposit Amount View  for the ATM application */
//==============================================================
public class PriceView extends View
{

	// Model

	// GUI components
	protected TextField priceInput;
	private Button submitButton;
	private Button doneButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public PriceView(IModel AddVIITAction)
	{
		super(AddVIITAction, "PriceView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                          \n                            "));

		getChildren().add(container);

		populateFields();
		
		myModel.subscribe("UpdateStatusMessage", this);
	}


	// Create the label (Text) for the title
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

        Text titleText = new Text("Enter a price the Vendor will supply this Item at");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setStrokeWidth(0.5);
		titleText.setStroke(Color.GOLDENROD);  
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

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
        grid.setPadding(new Insets(25, 40, 1, 25));




        Text price = new Text("Price:");
		price.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        price.setWrappingWidth(350);
        price.setTextAlignment(TextAlignment.CENTER);
        price.setFill(Color.GOLDENROD);
        grid.add(price, 1, 0);

		priceInput = new TextField();
		priceInput.setEditable(true);
		priceInput.setOnAction(e -> {
			processAction(e);
		});
		grid.add(priceInput, 1, 1);
		
		
		submitButton = new Button("Submit");
 		submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the insert
			processAction(e);
        });

		doneButton = new Button("Done");
 		doneButton.setOnAction(e -> {
 			clearErrorMessage();
 			myModel.stateChangeRequest("Cancel", null);
        });

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(doneButton);

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
		priceInput.setText("");
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		// DEBUG: System.out.println("DepositAmountView.processAction()");

		clearErrorMessage();

		String priceEntered = priceInput.getText();

		if (priceEntered == null || priceEntered.length() == 0)
			displayErrorMessage("Please enter an valid Price (e.g. 5.00)");
		else if(!priceEntered.matches("^0\\.[0-9]{2}$") && !priceEntered.matches("^[1-9][0-9]*\\.[0-9]{2}$"))  
			displayErrorMessage("Please enter a valid Price (e.g. 5.00)");
		else
		{
			processData(priceEntered);
		}
	}

	/**
	 * Process amount entered by user.
	 * Action is to pass this info on to the action object.
	 */
	//----------------------------------------------------------
	private void processData(String a)
	{
		myModel.stateChangeRequest("VIITData", a);
		populateFields();
		displayMessage((String)myModel.getState("UpdateStatusMessage"));
	}

	

	/**
	 * Required by interface
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if(key.equals("UpdateStatusMessage"))
		{
			String msg = (String)myModel.getState("UpdateStatusMessage");
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
