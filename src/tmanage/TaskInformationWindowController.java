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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tmanage.classes.Task;
import tmanage.interfaces.ITask;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class TaskInformationWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField desiredTimeField;
    @FXML
    private TextField spentTimeField;
    @FXML
    private CheckBox completedBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    
    private Task task;
    
    @FXML
    public void handleDeleteButtonPressed(MouseEvent event){
        if (Storage.tasks.deleteTask(task)){
            System.out.println("Succesfully deleted");
        } else {
            System.out.println("Man you are in trouble");
        }
        Stage stage = (Stage)deleteButton.getScene().getWindow();
        stage.close();        
    }
    
    @FXML
    public void handleSaveButtonPressed (MouseEvent event){
        Storage.tasks.updateTask(task, nameField.getText(),descriptionField.getText(), completedBox.isSelected());
            
        Stage stage = (Stage)saveButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void handleCancelButtonPressed (MouseEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    
    public void initTask(ITask t){
        this.task = (Task)t;
        nameField.setText(task.getName());
        descriptionField.setText(task.getDescritption());        
        spentTimeField.setText(TimeConverter.getFormattedTime(task.getSpentTime()));
        spentTimeField.setEditable(false);
        desiredTimeField.setText(TimeConverter.getFormattedTime(task.getTargetTime()));
        desiredTimeField.setEditable(false);
        completedBox.setSelected(task.isCompleted());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
