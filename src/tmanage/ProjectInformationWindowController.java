/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tmanage.classes.Order;
import tmanage.interfaces.IOrder;
import tmanage.interfaces.ITask;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class ProjectInformationWindowController implements Initializable {

    @FXML
    private Button sendButton;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField deadLineField;
    @FXML
    private TextField spentTimeField;
    @FXML
    private ChoiceBox projectsList;
    
    private Order order;
    
    public void initOrder(IOrder order){
        this.order = (Order) order;
        setOrderInformation();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        descriptionField.setEditable(false);
        salaryField.setEditable(false);
        deadLineField.setEditable(false);
        spentTimeField.setEditable(false);
        
        projectsList.setItems(FXCollections.observableArrayList(Storage.orders.getProjectNames()));
        
        projectsList.getSelectionModel().selectedItemProperty().addListener(
                 new ChangeListener<String>(){
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
                        order = (Order) Storage.orders.getOrderByName(new_val);
                        setOrderInformation();
                    }
                }
        );
    }
    public void setOrderInformation(){
        descriptionField.setText(order.getProject().getDescription());        
        salaryField.setText(String.valueOf(order.getSalary()));
        deadLineField.setText(order.getDeadLine().toString());
        long spentTime = 0;
        for (ITask task : Storage.tasks.getTasksByProject(order.getProject())){
            spentTime+=task.getSpentTime();
        }
        spentTimeField.setText(TimeConverter.getFormattedTime(spentTime));
        projectsList.getSelectionModel().select(order.getName());
    }
}
