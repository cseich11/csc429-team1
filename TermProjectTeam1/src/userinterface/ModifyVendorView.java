package userinterface;

//system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
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
import model.InventoryManager;
import model.SearchVendorAction;
import model.Vendor;

import java.util.Properties;

//project imports
import impresario.IModel;

public class ModifyVendorView extends View
{
    protected TextField name;
    protected TextField number;
    private Button doneButton;
    private Button submitButton;
    private ComboBox<String> status;

    // For showing error message

    private MessageView statusLog;
    SearchVendorAction searchV;
	
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
    public ModifyVendorView( IModel model)
    {

        super(model, "ModifyVendorView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
		container.setStyle("-fx-background-color: WHITESMOKE;"); 

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("UpdateStatusMessage", this);
    }

    // Create the label (Text) for the title of the screen
    //-------------------------------------------------------------
    private Node createTitle()
    {
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

        Text titleText = new Text("Modify Vendor Info");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setStrokeWidth(0.5);
		titleText.setStroke(Color.GOLDENROD);  
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

        return container;
    }


    // Create the main form contents
    //-------------------------------------------------------------
    private GridPane createFormContents()
    {
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 40, 1, 25));

        Text vName = new Text("NAME:");
        vName.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        vName.setWrappingWidth(350);
        vName.setTextAlignment(TextAlignment.CENTER);
        vName.setFill(Color.GOLDENROD);
        grid.add(vName, 2, 0);

        name = new TextField();
        name.setEditable(true);
        grid.add(name, 2, 1);

        Text vPhone = new Text("PHONE NUMBER:");
        vPhone.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        vPhone.setWrappingWidth(350);
        vPhone.setTextAlignment(TextAlignment.CENTER);
        vPhone.setFill(Color.GOLDENROD);
        grid.add(vPhone, 2, 2);

        number = new TextField();
        number.setEditable(true);
        grid.add(number, 2, 3);

        Text vStatus = new Text("STATUS");
        vStatus.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        vStatus.setWrappingWidth(350);
        vStatus.setTextAlignment(TextAlignment.CENTER);
        vStatus.setFill(Color.GOLDENROD);
        grid.add(vStatus, 2, 4);
		
		status = new ComboBox();
        status.getItems().addAll("Active", "Inactive");
        status.getSelectionModel().selectFirst();
        status.setVisibleRowCount(2);
		
		HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.CENTER);	
        btnContainer.getChildren().add(status);
        grid.add(btnContainer, 2, 5);

        submitButton = new Button("SUBMIT");
        submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the insert
			processAction();
        });

        doneButton = new Button("DONE");
        doneButton.setOnAction(e -> {
 			clearErrorMessage();
 			InventoryManager newManager = new InventoryManager();
        });
        
        HBox btnContainer4 = new HBox(150);
        btnContainer4.setAlignment(Pos.BOTTOM_CENTER);
		btnContainer4.getChildren().add(submitButton);
        btnContainer4.getChildren().add(doneButton);
        grid.add(btnContainer4, 2, 8);

        return grid;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("UpdateStatusMessage") == true)
        {
            // display the passed text
        	String val = (String)value;
			if (val.startsWith("ERR") == true)
				displayErrorMessage(val);
			else
				displayMessage(val);
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

    private void processAction() 
    {
    	clearErrorMessage();
    	
		String venNameEntered = name.getText();
		String phoneNumEntered = number.getText();
		String statusEntered = (String)status.getValue();
		boolean phoneC = true;
		try {
			Integer.parseInt(phoneNumEntered);
		} catch(NumberFormatException e) {
			phoneC = false;
		}
		
		if(venNameEntered.length() == 0)
			displayErrorMessage("Please Enter a Vendor Name");
		else if(!phoneC || phoneNumEntered.length() != 10)
			displayErrorMessage("Please Enter a Valid 10 digit Phone Number");
		else
			processData(venNameEntered, phoneNumEntered, statusEntered);
    }
    
    private void processData(String n, String num, String s) 
    {
    	Properties props = new Properties();
		props.setProperty("vendorName", n);
		props.setProperty("phoneNumber", num);
		props.setProperty("status", s);
		
		System.out.println(n + " - " + num + " - " + s); //DEBUG
		
		myModel.stateChangeRequest("ModifyVendorData", props);
		//populateFields();
    }
}
