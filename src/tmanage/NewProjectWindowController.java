/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tmanage.classes.Customer;
import tmanage.classes.Order;
import tmanage.classes.Project;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class NewProjectWindowController implements Initializable {
        
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;    
    @FXML
    private ChoiceBox customersList;    
    @FXML
    private ToggleButton privateToggleButton;
    @FXML
    private ToggleButton companyToggleButton;
    @FXML
    final ToggleGroup custGroup = new ToggleGroup();
    @FXML
    private TextField nameField;
    @FXML
    private TextArea dField;    
    @FXML
    private TextField deadLine;
    @FXML
    private TextField salaryField;
    
       
    @FXML
    private void handleAddButtonPressed(MouseEvent event){
        Project project = new Project(DBLoader.getLastID("Projects"), nameField.getText(), dField.getText());
        double salary = Double.parseDouble(salaryField.getText());        
        Date dLine = Date.valueOf(deadLine.getText());
                
        Customer customer;
        if (custGroup.getSelectedToggle()==privateToggleButton){
            customer = (Customer)Storage.persons.getCustomerByName((String)customersList.getValue());            
            Storage.orders.addPersonOrder(new Order(DBLoader.getLastID("CustomerOrders"),salary,dLine,customer, project));
        } else {
            customer =(Customer)Storage.companies.getCustomerByName((String)customersList.getValue());
            Storage.orders.addCompanyOrder(new Order(DBLoader.getLastID("CompanyOrders"), salary, dLine, customer, project));
        }
        
        Stage stage = (Stage)addButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleCancelButtonPressed(MouseEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
       customersList.setItems(FXCollections.observableArrayList(Storage.companies.getNames()));
       
       custGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
        @Override
        public void changed(ObservableValue<? extends Toggle> ov,
        Toggle toggle, Toggle new_toggle) {
            if (new_toggle == privateToggleButton){
                customersList.setItems(FXCollections.observableArrayList(Storage.persons.getNames()));
            } else {
                customersList.setItems(FXCollections.observableArrayList(Storage.companies.getNames()));
            }
            
         }
        });
       
    }    
}
