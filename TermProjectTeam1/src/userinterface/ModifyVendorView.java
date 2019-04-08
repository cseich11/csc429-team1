package userinterface;

//system imports
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

//project imports
import impresario.IModel;

public class ModifyVendorView extends View
{
    protected TextField name;
    protected TextField number;
    private Button doneButton;
    private Button submitButton;
    private ComboBox status;

    // For showing error message
    private VBox container;
    private MessageView statusLog;
	
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
    public ModifyVendorView( IModel Vendor)
    {

        super(Vendor, "ModifyVendorView");

        // create a container for showing the contents
        container = new VBox(10);

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
        myModel.subscribe("LoginError", this);
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
    private VBox createFormContents()
    {
		VBox vbox = new VBox(10);
    	
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
        grid.add(name, 2, 0);

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
        grid.add(vStatus, 2, 6);
		
		status = new ComboBox();
        status.getItems().addAll("Active", "Inactive");
        status.setVisibleRowCount(2);
		
		HBox statusContainer = new HBox(10);
		statusContainer.setAlignment(Pos.CENTER);	
		statusContainer.getChildren().add(status);
        grid.add(statusContainer, 2, 7);

        submitButton = new Button("Submit");
 		submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the insert
			processAction();
        });

		doneButton = new Button("Done");
 		doneButton.setOnAction(e -> {
 			clearErrorMessage();
			myModel.stateChangeRequest("CancelInsert", null);   
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

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
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
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    private void processAction() {
        //TODO: Call the modify method from Vendor class using the input from this view
  
    }
}
