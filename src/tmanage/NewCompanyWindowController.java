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
import tmanage.classes.CompanyCustomer;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class NewCompanyWindowController implements Initializable {
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea additionalField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    
    @FXML
    private void handleAddButtonPressed(MouseEvent event){
        CompanyCustomer customer = new CompanyCustomer(DBLoader.getLastID("Companies"),nameField.getText(),emailField.getText(), additionalField.getText());
        Storage.companies.addCustomer(customer);
        
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
