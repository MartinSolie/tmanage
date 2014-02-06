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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class SettingsWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField hostField;
    @FXML
    private TextField smtpPortField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        emailField.setText(Settings.getLogin());
        passwordField.setText(Settings.getPassword());
        hostField.setText(Settings.getHost());
        smtpPortField.setText(Integer.toString(Settings.getPort()));
    }
    
    @FXML
    private void handleSaveButtonPressed (MouseEvent event){
    
    }
    
    @FXML
    private void handleCancelButtonPressed (MouseEvent event){
        
    }
}
