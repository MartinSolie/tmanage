/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tmanage.classes.PrivateCustomer;

/**
 * FXML Controller class
 *
 * @author martin
 */
    

public class NewCustomerWindowController implements Initializable {

    @FXML
    private TextField surnameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField patronymicField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea additionalField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    
    @FXML
    private void handleAddButtonPressed (MouseEvent event){
        PrivateCustomer customer = new PrivateCustomer(DBLoader.getLastID("Customers"), surnameField.getText(), 
                                        nameField.getText(), patronymicField.getText(), emailField.getText(), additionalField.getText());        
        Storage.persons.addCustomer(customer);
        
        Stage stage = (Stage)addButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleCancelButtonPressed(MouseEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
