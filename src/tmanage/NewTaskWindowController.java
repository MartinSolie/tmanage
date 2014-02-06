/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tmanage.classes.Task;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class NewTaskWindowController implements Initializable {
    
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ChoiceBox projectsList;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField targTimeField;
    
    @FXML
    private void handleAddButtonPressed(MouseEvent event){
        Task task = new Task(DBLoader.getLastID("Tasks"), 
                Storage.orders.getOrderByName(projectsList.getValue().toString()).getProject().getId(), 
                nameField.getText(), descriptionField.getText(), Integer.parseInt(targTimeField.getText()),
                0, false);
        
        if(Storage.tasks.addTask(task)){        
            Stage stage = (Stage)addButton.getScene().getWindow();            
            stage.close();
        } else {
            //messaging about the error
        }
    }
    
    @FXML
    private void handleCancelButtonPressed (MouseEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        projectsList.setItems(FXCollections.observableArrayList(Storage.orders.getProjectNames()));
    }    
}
